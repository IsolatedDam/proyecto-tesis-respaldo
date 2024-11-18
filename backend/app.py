from flask import Flask, request, jsonify
import mariadb
from bcrypt import hashpw, gensalt, checkpw
import re

app = Flask(__name__)

# Conexión a la base de datos
def get_db_connection():
    try:
        conn = mariadb.connect(
            user="admin",
            password="Admin1234",
            host="turismo1.cfug8846eg5c.sa-east-1.rds.amazonaws.com",
            port=3306,
            database="turismo"
        )
        return conn
    except mariadb.Error as e:
        print(f"Error al conectar a MariaDB: {e}")
        return None

# Validar datos de persona
def validate_persona_data(data):
    if not data.get("nombre"):
        return "El nombre es obligatorio."
    if not data.get("email"):
        return "El correo electrónico es obligatorio."
    if not data.get("contrasena"):
        return "La contraseña es obligatoria."
    if len(data.get("contrasena", "")) < 6:
        return "La contraseña debe tener al menos 6 caracteres."
    if not re.match(r"[^@]+@[^@]+\.[^@]+", data["email"]):
        return "El correo electrónico no tiene un formato válido."
    return None

# Encriptar contraseña
def hash_password(password):
    return hashpw(password.encode("utf-8"), gensalt()).decode("utf-8")

# Verificar contraseña
def verify_password(password, hashed_password):
    return checkpw(password.encode("utf-8"), hashed_password.encode("utf-8"))

# Rutas API
@app.route('/create_persona', methods=['POST'])
def create_persona():
    data = request.get_json()
    validation_error = validate_persona_data(data)
    if validation_error:
        return jsonify({"status": "error", "message": validation_error}), 400

    nombre = data["nombre"]
    email = data["email"]
    contrasena = hash_password(data["contrasena"])
    rol = data.get("rol", "user")

    conn = get_db_connection()
    if not conn:
        return jsonify({"status": "error", "message": "Error al conectar a la base de datos"}), 500

    try:
        cursor = conn.cursor()
        cursor.execute(
            "INSERT INTO Persona (nombre, email, contrasena, rol) VALUES (?, ?, ?, ?)",
            (nombre, email, contrasena, rol)
        )
        conn.commit()
        return jsonify({"status": "success", "message": "Usuario creado exitosamente"}), 201
    except mariadb.IntegrityError:
        return jsonify({"status": "error", "message": "El correo electrónico ya está registrado"}), 400
    except Exception as e:
        print(f"Error al crear persona: {e}")
        return jsonify({"status": "error", "message": "Error interno"}), 500
    finally:
        cursor.close()
        conn.close()

@app.route('/personas', methods=['GET'])
def get_personas():
    conn = get_db_connection()
    if not conn:
        return jsonify({"status": "error", "message": "Error al conectar a la base de datos"}), 500

    try:
        cursor = conn.cursor()
        cursor.execute("SELECT idUsuario, nombre, email, rol FROM Persona")
        personas = cursor.fetchall()
        result = [{"idUsuario": p[0], "nombre": p[1], "email": p[2], "rol": p[3]} for p in personas]
        return jsonify({"status": "success", "data": result}), 200
    except Exception as e:
        print(f"Error al obtener personas: {e}")
        return jsonify({"status": "error", "message": "Error interno"}), 500
    finally:
        cursor.close()
        conn.close()

@app.route('/login', methods=['POST'])
def login():
    try:
        data = request.get_json()
        if not data or not data.get("nombre") or not data.get("contrasena"):
            return jsonify({"status": "error", "message": "Nombre y contraseña son obligatorios"}), 400

        nombre = data["nombre"]
        contrasena = data["contrasena"]

        conn = get_db_connection()
        if not conn:
            return jsonify({"status": "error", "message": "Error al conectar a la base de datos"}), 500

        cursor = conn.cursor()
        cursor.execute("SELECT idUsuario, nombre, contrasena, rol FROM Persona WHERE nombre = ?", (nombre,))
        user = cursor.fetchone()

        if not user:
            return jsonify({"status": "error", "message": "Usuario no encontrado"}), 404

        user_id, user_name, hashed_password, user_role = user

        if not verify_password(contrasena, hashed_password):
            return jsonify({"status": "error", "message": "Contraseña incorrecta"}), 401

        return jsonify({
            "status": "success",
            "message": "Usuario autenticado correctamente",
            "user": {
                "idUsuario": user_id,
                "nombre": user_name,
                "rol": user_role
            }
        }), 200
    except Exception as e:
        print(f"Error en /login: {e}")
        return jsonify({"status": "error", "message": "Error interno"}), 500
    finally:
        if 'cursor' in locals():
            cursor.close()
        if 'conn' in locals():
            conn.close()

if __name__ == '__main__':
    app.run(debug=True, host="0.0.0.0", port=5000)
