# HealingTouch - Instrucciones de Compilación y Ejecución

## Requisitos Previos

### Software Necesario
- **Java Development Kit (JDK) 17 o superior**
  - Descargar de: https://adoptium.net/
  - Verificar instalación: `java -version`
  
- **Apache Maven 3.6 o superior**
  - Descargar de: https://maven.apache.org/download.cgi
  - Verificar instalación: `mvn -version`
  
- **MySQL Server 5.7 o superior**
  - Para la base de datos healingtouchdb
  - Configuración por defecto: localhost:3306, usuario: root, sin contraseña

### Configuración IDE (Opcional)

#### Eclipse
1. Instalar plugin e(fx)clipse para soporte JavaFX
2. Importar como proyecto Maven existente
3. Asegurar que use JDK 17

#### IntelliJ IDEA
1. Importar como proyecto Maven
2. Configurar SDK del proyecto a Java 17
3. Habilitar auto-import de Maven

#### VS Code
1. Instalar extensiones:
   - Extension Pack for Java
   - JavaFX Support
2. Abrir la carpeta del proyecto

## Estructura del Proyecto

```
Healingtouch/
├── src/
│   ├── main/
│   │   ├── java/           # Código fuente Java
│   │   └── resources/      # Recursos (FXML, CSS, imágenes)
│   └── test/
│       ├── java/           # Tests unitarios
│       └── resources/      # Recursos para tests
├── lib/
│   └── TrayTester.jar      # Biblioteca de notificaciones
├── pom.xml                 # Configuración Maven
├── ARCHITECTURE.md         # Documentación de arquitectura
├── MIGRATION_GUIDE.md      # Guía de migración
└── BUILD_INSTRUCTIONS.md   # Este archivo
```

## Configuración de Base de Datos

### 1. Crear Base de Datos

```sql
CREATE DATABASE healingtouchdb;
USE healingtouchdb;

-- Tabla de usuarios
CREATE TABLE user (
    id INT AUTO_INCREMENT PRIMARY KEY,
    email VARCHAR(255) NOT NULL UNIQUE,
    password VARCHAR(255) NOT NULL
);

-- Tabla de pacientes
CREATE TABLE patient (
    id INT AUTO_INCREMENT PRIMARY KEY,
    names VARCHAR(255) NOT NULL,
    surnames VARCHAR(255) NOT NULL,
    document_id VARCHAR(50) NOT NULL UNIQUE,
    telephone VARCHAR(20),
    birthdate DATE,
    address VARCHAR(500)
);
```

### 2. Configurar Conexión

Editar `src/main/java/com/healingtouch/config/DatabaseConfig.java`:

```java
private static final String URL = "jdbc:mysql://localhost:3306/healingtouchdb?useSSL=false";
private static final String USER = "root";
private static final String PASSWORD = ""; // Cambiar si es necesario
```

## Compilación del Proyecto

### Opción 1: Línea de Comandos

```bash
# Limpiar compilaciones anteriores
mvn clean

# Compilar el proyecto
mvn compile

# Compilar y empaquetar (crear JAR)
mvn package

# Compilar sin ejecutar tests
mvn package -DskipTests
```

### Opción 2: IDE

**Eclipse:**
1. Clic derecho en el proyecto
2. Run As → Maven build...
3. Goals: `clean compile` o `clean package`

**IntelliJ IDEA:**
1. Abrir panel Maven (lado derecho)
2. Expandir Lifecycle
3. Ejecutar: clean, compile, o package

**VS Code:**
1. Abrir Command Palette (Ctrl+Shift+P)
2. Buscar "Maven: Execute commands"
3. Seleccionar el goal deseado

## Ejecución de la Aplicación

### Opción 1: Maven (Recomendado)

```bash
# Ejecutar con Maven JavaFX plugin
mvn javafx:run
```

### Opción 2: Desde el JAR

```bash
# Empaquetar primero
mvn clean package

# Ejecutar el JAR (requiere JavaFX en el classpath)
java --module-path $PATH_TO_FX --add-modules javafx.controls,javafx.fxml \
     -cp target/Healingtouch-0.0.1-SNAPSHOT.jar:lib/TrayTester.jar \
     com.healingtouch.app.HealingTouchApp
```

### Opción 3: IDE

**Eclipse:**
1. Clic derecho en `HealingTouchApp.java`
2. Run As → Java Application

**IntelliJ IDEA:**
1. Abrir `HealingTouchApp.java`
2. Clic en el icono de play verde junto a la clase
3. Seleccionar "Run 'HealingTouchApp.main()'"

**VS Code:**
1. Abrir `HealingTouchApp.java`
2. Clic en "Run" sobre el método main

## Resolución de Problemas Comunes

### Error: "Error: JavaFX runtime components are missing"

**Causa:** JavaFX no está en el module path

**Solución:** Usar `mvn javafx:run` en lugar de `java -jar`

### Error: "Failed to connect to database"

**Causa:** MySQL no está corriendo o credenciales incorrectas

**Solución:**
1. Verificar que MySQL esté corriendo: `mysql -u root -p`
2. Verificar credenciales en `DatabaseConfig.java`
3. Verificar que la base de datos existe

### Error: "package tray.notification does not exist"

**Causa:** TrayTester.jar no está en lib/ o no está configurado correctamente

**Solución:**
1. Verificar que existe: `lib/TrayTester.jar`
2. Verificar configuración en `pom.xml`

### Error de Compilación: "source value 17 is obsolete"

**Causa:** JDK instalado es menor a Java 17

**Solución:**
1. Instalar JDK 17 o superior
2. Configurar JAVA_HOME correctamente
3. Reiniciar IDE

### Warning: "Using platform encoding"

**No crítico.** Para eliminar, agregar a pom.xml:
```xml
<properties>
    <project.build.sourceEncoding>UTF-8</project.build.sourceEncoding>
</properties>
```

## Testing

### Ejecutar Tests (cuando se implementen)

```bash
# Ejecutar todos los tests
mvn test

# Ejecutar test específico
mvn test -Dtest=AuthenticationServiceTest

# Ejecutar tests con reporte de cobertura
mvn clean test jacoco:report
```

## Crear Distribución

### JAR Ejecutable

```bash
# Crear JAR con todas las dependencias
mvn clean package assembly:single

# El JAR estará en: target/Healingtouch-0.0.1-SNAPSHOT-jar-with-dependencies.jar
```

### Ejecutable Nativo (con jpackage - Java 14+)

```bash
# Requiere jpackage
jpackage --input target \
         --name HealingTouch \
         --main-jar Healingtouch-0.0.1-SNAPSHOT.jar \
         --main-class com.healingtouch.app.HealingTouchApp \
         --type exe  # o dmg para Mac, deb/rpm para Linux
```

## Verificación de Build

### Checklist de Verificación

- [ ] `mvn clean` ejecuta sin errores
- [ ] `mvn compile` ejecuta sin errores
- [ ] `mvn package` crea el JAR exitosamente
- [ ] `mvn javafx:run` inicia la aplicación
- [ ] La interfaz gráfica se muestra correctamente
- [ ] Se puede conectar a la base de datos
- [ ] Login funciona correctamente
- [ ] Registro de pacientes funciona

### Comando de Verificación Completa

```bash
# Ejecutar todos los pasos
mvn clean compile package javafx:run
```

## Información de Versiones

- **Java:** 17
- **JavaFX:** 17.0.2
- **Maven:** 3.6+
- **MySQL Connector:** 5.1.39
- **JFoenix:** 8.0.8

## Soporte y Contacto

Para problemas o preguntas:
1. Revisar `ARCHITECTURE.md` para entender la estructura
2. Revisar `MIGRATION_GUIDE.md` para cambios recientes
3. Consultar issues en GitHub
4. Contactar al equipo de desarrollo

## Referencias Útiles

- [Documentación Maven](https://maven.apache.org/guides/)
- [Documentación JavaFX](https://openjfx.io/)
- [JFoenix Documentation](http://www.jfoenix.com/)
- [MySQL Connector/J](https://dev.mysql.com/doc/connector-j/en/)

---

**Última Actualización:** Noviembre 2025  
**Mantenido por:** Equipo de Desarrollo HealingTouch
