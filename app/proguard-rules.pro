# Add project specific ProGuard rules here.
# You can control the set of applied configuration files using the
# proguardFiles setting in build.gradle.
#
# For more details, see
#   http://developer.android.com/guide/developing/tools/proguard.html

# Si estás usando Room para bases de datos locales, mantén las clases y anotaciones de Room
-keep class androidx.room.** { *; }
-keep interface androidx.room.** { *; }
-dontwarn androidx.room.paging.**

# Si usas DataStore para almacenar preferencias, mantén las clases de DataStore
-keep class androidx.datastore.preferences.** { *; }
-keep interface androidx.datastore.preferences.** { *; }

# Si utilizas Retrofit, mantén las clases de Retrofit
-keep class retrofit2.** { *; }
-keep interface retrofit2.** { *; }

# Si tu proyecto usa WebView con JS, descomenta y especifica el nombre de la clase JavaScript
#-keepclassmembers class fqcn.of.javascript.interface.for.webview {
#   public *;
#}

# Mantén los números de línea en los rastros de pila para debugging
-keepattributes SourceFile,LineNumberTable

# Si mantienes los números de línea, oculta el nombre del archivo fuente original
-renamesourcefileattribute SourceFile

# Evitar eliminar los métodos de clases con anotaciones de Room o Retrofit
-keepattributes *Annotation*

# Evitar advertencias sobre clases de bibliotecas adicionales como Picasso o Maps
-dontwarn com.squareup.picasso.**
-dontwarn com.google.android.gms.maps.**