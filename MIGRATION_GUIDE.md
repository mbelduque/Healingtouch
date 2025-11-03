# Guía de Migración - HealingTouch

## Resumen de Cambios

Este documento explica los cambios realizados en la reestructuración del proyecto HealingTouch hacia una arquitectura JavaFX moderna y profesional.

## Cambios en la Estructura de Directorios

### Antes (Estructura Antigua)
```
src/
└── com/healingtouch/
    ├── main/
    │   └── Main.java
    ├── controller/
    │   ├── LoginController.java (con lógica de BD y negocio)
    │   ├── DoctorController.java
    │   ├── PatientController.java
    │   └── ManagerController.java
    ├── view/
    │   ├── Login.fxml
    │   ├── Doctor.fxml
    │   ├── Patient.fxml
    │   └── Manager.fxml
    ├── model/
    │   ├── User.java
    │   └── Patient.java
    ├── helpers/
    │   ├── JDBCConnectionPool.java
    │   └── ObjectPool.java
    └── resources/
        ├── css/
        └── images/
```

### Después (Estructura Nueva - Maven Estándar)
```
src/
├── main/
│   ├── java/com/healingtouch/
│   │   ├── app/
│   │   │   └── HealingTouchApp.java (nuevo nombre)
│   │   ├── config/
│   │   │   └── DatabaseConfig.java (nuevo - Singleton)
│   │   ├── controller/
│   │   │   ├── LoginController.java (refactorizado - sin lógica BD)
│   │   │   ├── DoctorController.java
│   │   │   ├── PatientController.java
│   │   │   └── ManagerController.java
│   │   ├── model/
│   │   │   ├── User.java
│   │   │   └── Patient.java
│   │   ├── repository/
│   │   │   ├── UserRepository.java (nuevo)
│   │   │   └── PatientRepository.java (nuevo)
│   │   ├── service/
│   │   │   ├── AuthenticationService.java (nuevo)
│   │   │   └── PatientService.java (nuevo)
│   │   └── util/
│   │       ├── JDBCConnectionPool.java
│   │       └── ObjectPool.java
│   └── resources/com/healingtouch/
│       ├── view/
│       │   ├── Login.fxml
│       │   ├── Doctor.fxml
│       │   ├── Patient.fxml
│       │   └── Manager.fxml
│       ├── css/
│       │   └── application.css
│       └── images/
│           └── *.png
└── test/
    ├── java/
    └── resources/
```

## Cambios Técnicos Importantes

### 1. Actualización de Java y JavaFX

**Antes:**
- Java 8 (con JavaFX incluido)
- JavaFX Maven Plugin versión 8.8.3

**Después:**
- Java 17
- JavaFX 17 como dependencia separada
- OpenJFX Maven Plugin versión 0.0.8

### 2. Clase Principal

**Antes:** `com.healingtouch.main.Main`
```java
package com.healingtouch.main;

public class Main extends Application {
    public static Stage stage = null;
    // ...
}
```

**Después:** `com.healingtouch.app.HealingTouchApp`
```java
package com.healingtouch.app;

public class HealingTouchApp extends Application {
    public static Stage stage = null;
    // ... mejor documentado
}
```

### 3. Nueva Capa: Config (Configuración)

**Archivo:** `DatabaseConfig.java`
- Patrón Singleton para gestión centralizada de BD
- Pool de conexiones compartido
- Fácil modificación de parámetros de conexión

```java
DatabaseConfig config = DatabaseConfig.getInstance();
Connection conn = config.getConnection();
// usar conexión
config.releaseConnection(conn);
```

### 4. Nueva Capa: Repository (Acceso a Datos)

Los repositorios encapsulan todas las operaciones de base de datos.

**UserRepository.java:**
- `findByEmailAndPassword(email, password)` - Login
- `existsByEmail(email)` - Verificar si email existe
- `save(user)` - Guardar usuario

**PatientRepository.java:**
- `existsByDocumentId(documentId)` - Verificar si paciente existe
- `save(...)` - Guardar paciente

### 5. Nueva Capa: Service (Lógica de Negocio)

Los servicios contienen la lógica de negocio y validan reglas.

**AuthenticationService.java:**
- `authenticate(email, password)` - Autenticar usuario
- `emailExists(email)` - Validar email

**PatientService.java:**
- `registerPatient(...)` - Registrar paciente completo
- Retorna códigos: "SUCCESS", "DOCUMENT_EXISTS", "EMAIL_EXISTS"

### 6. Controladores Refactorizados

**Antes (LoginController con lógica de BD):**
```java
@FXML
void login(MouseEvent event) {
    // Conexión directa a BD
    JDBCConnectionPool pool = new JDBCConnectionPool(...);
    Connection connection = pool.checkOut();
    Statement statement = connection.createStatement();
    ResultSet status = statement.executeQuery("SELECT * FROM user...");
    // Lógica mezclada
}
```

**Después (LoginController delgado):**
```java
public class LoginController {
    private AuthenticationService authService;
    
    public LoginController() {
        this.authService = new AuthenticationService();
    }
    
    @FXML
    void login(MouseEvent event) {
        String email = txtFieldEmailLogin.getText();
        String password = pswdFieldPasswordLogin.getText();
        
        // Delega al servicio
        User user = authService.authenticate(email, password);
        
        if (user != null) {
            // Éxito
        } else {
            // Error
        }
    }
}
```

## Rutas de Recursos Actualizadas

**Archivos FXML:**
```xml
<!-- Antes -->
stylesheets="@../resources/css/application.css"
<Image url="@../resources/images/logo.png" />

<!-- Después -->
stylesheets="@../css/application.css"
<Image url="@../images/logo.png" />
```

**Código Java:**
```java
// Antes
FXMLLoader.load(getClass().getResource("/com/healingtouch/view/Manager.fxml"))

// Después (mismo - no cambió)
FXMLLoader.load(getClass().getResource("/com/healingtouch/view/Manager.fxml"))
```

## Configuración Maven (pom.xml)

### Cambios en Build
```xml
<!-- Antes -->
<sourceDirectory>src</sourceDirectory>

<!-- Después -->
<sourceDirectory>src/main/java</sourceDirectory>
<resources>
    <resource>
        <directory>src/main/resources</directory>
    </resource>
</resources>
```

### Nuevas Dependencias JavaFX
```xml
<dependency>
    <groupId>org.openjfx</groupId>
    <artifactId>javafx-controls</artifactId>
    <version>17.0.2</version>
</dependency>
<dependency>
    <groupId>org.openjfx</groupId>
    <artifactId>javafx-fxml</artifactId>
    <version>17.0.2</version>
</dependency>
```

## Ventajas de la Nueva Arquitectura

### 1. Separación de Responsabilidades
- **Controladores**: Solo manejo de UI
- **Servicios**: Lógica de negocio
- **Repositorios**: Acceso a datos
- **Modelos**: Entidades de dominio

### 2. Testabilidad
```java
// Fácil crear tests unitarios
@Test
public void testAuthentication() {
    AuthenticationService service = new AuthenticationService();
    User user = service.authenticate("test@example.com", "password");
    assertNotNull(user);
}
```

### 3. Mantenibilidad
- Cambiar BD: Solo modificar repositorios
- Cambiar lógica: Solo modificar servicios
- Cambiar UI: Solo modificar controladores y FXML

### 4. Reutilización
Los servicios y repositorios pueden usarse desde cualquier controlador:
```java
// En cualquier controlador
AuthenticationService authService = new AuthenticationService();
User user = authService.authenticate(email, password);
```

## Comandos Maven Actualizados

```bash
# Compilar
mvn clean compile

# Empaquetar
mvn clean package

# Ejecutar (con JavaFX)
mvn javafx:run

# Saltar tests
mvn clean package -DskipTests
```

## Próximos Pasos Recomendados

1. **Agregar Tests Unitarios**
   ```
   src/test/java/com/healingtouch/
   ├── service/
   │   ├── AuthenticationServiceTest.java
   │   └── PatientServiceTest.java
   └── repository/
       ├── UserRepositoryTest.java
       └── PatientRepositoryTest.java
   ```

2. **Mejorar Seguridad**
   - Usar `PreparedStatement` en lugar de `Statement`
   - Hashear contraseñas (BCrypt)
   - Validación de entrada

3. **Configuración Externa**
   - Archivo `application.properties` para configuración
   - Variables de entorno para credenciales

4. **Logging**
   - Agregar SLF4J + Logback
   - Logs en lugar de printStackTrace()

5. **Manejo de Excepciones**
   - Excepciones personalizadas
   - Manejo centralizado de errores

## Compatibilidad

### Código Existente
- ✅ Todas las vistas FXML funcionan igual
- ✅ Todos los controladores de UI mantienen funcionalidad
- ✅ Modelos no han cambiado
- ✅ Pool de conexiones sigue funcionando

### Cambios que Requieren Atención
- ⚠️ La clase principal cambió de nombre
- ⚠️ Rutas de recursos en FXML actualizadas
- ⚠️ Java 17 en lugar de Java 8

## Solución de Problemas

### Error: "module not found"
**Solución:** Asegurar que JavaFX esté en las dependencias del pom.xml

### Error: "cannot find symbol FXML"
**Solución:** Verificar que javafx-fxml esté en las dependencias

### Error: "package tray.notification does not exist"
**Solución:** Verificar que TrayTester.jar esté en lib/ y configurado en pom.xml

## Documentación Adicional

- Ver `ARCHITECTURE.md` para descripción detallada de la arquitectura
- Ver JavaDoc en las clases para documentación de métodos
- Ver diagramas UML (próximamente)

---

**Fecha de Migración:** Noviembre 2025  
**Versión:** 2.0  
**Autor:** Equipo de Desarrollo
