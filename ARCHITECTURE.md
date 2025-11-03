# HealingTouch - Arquitectura de la Aplicación

## Descripción General
Esta aplicación JavaFX sigue un patrón arquitectónico de capas moderno y bien estructurado, siguiendo las mejores prácticas para aplicaciones de interfaz gráfica empresariales.

## Estructura de Directorios

```
src/
├── main/
│   ├── java/com/healingtouch/
│   │   ├── app/              # Punto de entrada de la aplicación
│   │   ├── config/           # Configuración (base de datos, etc.)
│   │   ├── controller/       # Controladores JavaFX (capa de presentación)
│   │   ├── model/            # Modelos de dominio (entidades)
│   │   ├── repository/       # Capa de acceso a datos (DAO)
│   │   ├── service/          # Lógica de negocio
│   │   └── util/             # Utilidades y helpers
│   └── resources/com/healingtouch/
│       ├── css/              # Hojas de estilo
│       ├── images/           # Recursos de imágenes
│       └── view/             # Archivos FXML (vistas)
└── test/
    ├── java/                 # Pruebas unitarias
    └── resources/            # Recursos para pruebas
```

## Patrones Arquitectónicos Implementados

### 1. **MVC (Model-View-Controller)**
   - **Model** (`model/`): Entidades de dominio (User, Patient)
   - **View** (`resources/view/`): Archivos FXML que definen la UI
   - **Controller** (`controller/`): Controladores JavaFX que manejan eventos UI

### 2. **Repository Pattern (DAO)**
   - **Ubicación**: `repository/`
   - **Propósito**: Abstrae el acceso a datos
   - **Ejemplos**: 
     - `UserRepository`: Operaciones CRUD para usuarios
     - `PatientRepository`: Operaciones CRUD para pacientes
   - **Beneficios**: 
     - Separación de lógica de persistencia
     - Fácil cambio de fuente de datos
     - Código más testeable

### 3. **Service Layer Pattern**
   - **Ubicación**: `service/`
   - **Propósito**: Contiene la lógica de negocio
   - **Ejemplos**:
     - `AuthenticationService`: Maneja autenticación de usuarios
     - `PatientService`: Maneja registro y gestión de pacientes
   - **Beneficios**:
     - Controladores delgados (thin controllers)
     - Lógica de negocio reutilizable
     - Facilita pruebas unitarias

### 4. **Singleton Pattern**
   - **Ubicación**: `config/DatabaseConfig`
   - **Propósito**: Gestión centralizada de configuración de base de datos
   - **Beneficios**:
     - Una única instancia de configuración
     - Pool de conexiones compartido
     - Fácil mantenimiento

### 5. **Object Pool Pattern**
   - **Ubicación**: `util/JDBCConnectionPool`
   - **Propósito**: Reutilización de conexiones de base de datos
   - **Beneficios**:
     - Mejor rendimiento
     - Gestión eficiente de recursos

## Flujo de Datos

```
Usuario → Vista (FXML) → Controlador → Servicio → Repositorio → Base de Datos
                            ↓            ↓           ↓
                        UI Logic    Business    Data Access
                                     Logic
```

### Ejemplo de Flujo: Login de Usuario

1. **Vista** (`Login.fxml`): Usuario ingresa credenciales
2. **Controlador** (`LoginController`): Captura evento de click
3. **Servicio** (`AuthenticationService`): Valida datos y aplica lógica de negocio
4. **Repositorio** (`UserRepository`): Ejecuta consulta SQL
5. **Config** (`DatabaseConfig`): Provee conexión desde el pool
6. **Respuesta**: Flujo inverso hasta mostrar resultado en la vista

## Responsabilidades por Capa

### App Layer (`app/`)
- Punto de entrada de la aplicación
- Configuración inicial de JavaFX
- Gestión de la ventana principal

### Controller Layer (`controller/`)
- Manejo de eventos de UI
- Validación de entrada básica
- Navegación entre vistas
- NO debe contener lógica de negocio
- NO debe acceder directamente a la base de datos

### Service Layer (`service/`)
- Lógica de negocio
- Validación de reglas de negocio
- Orquestación de operaciones complejas
- Transacciones

### Repository Layer (`repository/`)
- Operaciones CRUD
- Consultas SQL
- Mapeo de ResultSet a objetos
- NO debe contener lógica de negocio

### Model Layer (`model/`)
- Entidades de dominio (POJOs)
- Getters y setters
- Sin lógica de negocio

### Config Layer (`config/`)
- Configuración de aplicación
- Gestión de conexiones
- Parámetros de configuración

### Util Layer (`util/`)
- Utilidades generales
- Helpers
- Clases reutilizables

## Ventajas de esta Arquitectura

1. **Separación de Responsabilidades**: Cada capa tiene un propósito claro
2. **Mantenibilidad**: Cambios en una capa no afectan otras
3. **Testabilidad**: Fácil crear pruebas unitarias por capa
4. **Escalabilidad**: Fácil agregar nuevas funcionalidades
5. **Reutilización**: Servicios y repositorios reutilizables
6. **Legibilidad**: Código organizado y fácil de entender
7. **Colaboración**: Múltiples desarrolladores pueden trabajar en paralelo

## Mejores Prácticas Aplicadas

- ✅ Estructura de directorios Maven estándar
- ✅ Separación de código fuente y recursos
- ✅ Controladores delgados (thin controllers)
- ✅ Servicios con lógica de negocio
- ✅ Repositorios para acceso a datos
- ✅ Configuración centralizada
- ✅ Pool de conexiones para eficiencia
- ✅ Nomenclatura clara y consistente
- ✅ Paquetes organizados por funcionalidad

## Convenciones de Nomenclatura

- **Clases de Modelo**: Sustantivos singulares (User, Patient)
- **Controladores**: `[Nombre]Controller` (LoginController)
- **Servicios**: `[Nombre]Service` (AuthenticationService)
- **Repositorios**: `[Nombre]Repository` (UserRepository)
- **Vistas FXML**: `[Nombre].fxml` (Login.fxml)
- **Paquetes**: minúsculas (controller, service, repository)

## Próximos Pasos Recomendados

1. Implementar pruebas unitarias en `src/test/java`
2. Agregar manejo de excepciones personalizado
3. Implementar logging (Log4j o SLF4J)
4. Usar PreparedStatements para prevenir SQL Injection
5. Agregar capa de DTO (Data Transfer Objects) si es necesario
6. Implementar validación con Bean Validation (JSR-303)
7. Considerar usar un ORM (JPA/Hibernate) en lugar de JDBC directo

## Tecnologías Utilizadas

- **JavaFX 8**: Framework de interfaz gráfica
- **Maven**: Gestión de dependencias y construcción
- **MySQL**: Base de datos
- **JDBC**: Acceso a base de datos
- **JFoenix**: Componentes Material Design para JavaFX
- **TrayNotification**: Notificaciones del sistema

---

**Autor**: Mauricio Belduque  
**Versión**: 2.0  
**Fecha**: 2025
