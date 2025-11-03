# HealingTouch

[![License: GPL v3](https://img.shields.io/badge/License-GPLv3-blue.svg)](https://www.gnu.org/licenses/gpl-3.0)
[![Java](https://img.shields.io/badge/Java-17-orange.svg)](https://adoptium.net/)
[![JavaFX](https://img.shields.io/badge/JavaFX-17.0.2-green.svg)](https://openjfx.io/)
[![Maven](https://img.shields.io/badge/Maven-3.6+-red.svg)](https://maven.apache.org/)
[![Contributors](https://img.shields.io/github/contributors/mbelduque/Healingtouch.svg)](https://github.com/mbelduque/Healingtouch/graphs/contributors)
[![Stars](https://img.shields.io/github/stars/mbelduque/Healingtouch.svg?style=social)](https://github.com/mbelduque/Healingtouch/stargazers)
[![LinkedIn](https://img.shields.io/badge/-LinkedIn-black.svg?logo=linkedin&colorB=555)](https://www.linkedin.com/in/mauricio-belduque-guzman-925541137/)

<!-- PROJECT LOGO -->
<br />
<p align="center">
  <h1 align="center">
    <img src="https://github.com/mbelduque/Healingtouch/blob/master/src/com/healingtouch/resources/images/healingtouch.png" alt="HealingTouch Logo">
  </h1>
  <h3 align="center">Medical Appointment Management System</h3>
  <p align="center">
    A modern JavaFX desktop application for managing medical appointments, patient records, and healthcare workflows
    <br />
    <a href="#about-the-project"><strong>Explore the docs »</strong></a>
    <br />
    <br />
    <a href="https://github.com/mbelduque/Healingtouch/issues">Report Bug</a>
    ·
    <a href="https://github.com/mbelduque/Healingtouch/issues">Request Feature</a>
  </p>
</p>

---

## Table of Contents

* [About the Project](#about-the-project)
  * [Key Features](#key-features)
  * [Built With](#built-with)
  * [Architecture](#architecture)
* [Getting Started](#getting-started)
  * [Prerequisites](#prerequisites)
  * [Installation](#installation)
  * [Database Setup](#database-setup)
* [Usage](#usage)
* [Project Structure](#project-structure)
* [Documentation](#documentation)
* [Contributing](#contributing)
* [License](#license)
* [Contact](#contact)
* [Acknowledgements](#acknowledgements)

---

## About The Project

HealingTouch is a comprehensive medical appointment management system built with JavaFX. It provides healthcare facilities with a modern, intuitive interface for managing patient records, scheduling appointments, and streamlining healthcare workflows.

The application follows enterprise-level architecture patterns including MVC (Model-View-Controller), Service Layer, and Repository patterns, ensuring maintainability, scalability, and testability.

### Key Features

✨ **Patient Management**
- Complete patient registration and profile management
- Secure storage of personal and medical information
- Search and filter capabilities

🗓️ **Appointment Scheduling**
- Intuitive appointment booking interface
- Multi-specialty support (General Medicine, Dentistry, Gynecology, Urology)
- Real-time availability management

🔐 **Authentication & Security**
- Secure user authentication system
- Role-based access control
- Password encryption

💻 **Modern UI/UX**
- Material Design components (JFoenix)
- Responsive and intuitive interface
- System tray notifications

### Built With

* **[JavaFX 17.0.2](https://openjfx.io/)** - Modern UI framework
* **[JFoenix 8.0.8](http://www.jfoenix.com/)** - Material Design components
* **[Maven](https://maven.apache.org/)** - Dependency management and build tool
* **[MySQL 5.7+](https://www.mysql.com/)** - Database management system
* **[Apache Commons DBCP](https://commons.apache.org/proper/commons-dbcp/)** - Connection pooling
* **[FontAwesomeFX](https://bitbucket.org/Jerady/fontawesomefx/)** - Icon library

### Architecture

HealingTouch implements a clean, layered architecture:

```
┌─────────────────────┐
│   Presentation      │  Controllers + FXML Views
├─────────────────────┤
│   Service Layer     │  Business Logic
├─────────────────────┤
│   Repository        │  Data Access (DAO)
├─────────────────────┤
│   Database          │  MySQL
└─────────────────────┘
```

For detailed architecture information, see [ARCHITECTURE.md](ARCHITECTURE.md).

---

## Getting Started

Follow these instructions to get HealingTouch running on your local machine.

### Prerequisites

**Required Software:**

* **Java Development Kit (JDK) 17 or higher**
  ```sh
  java -version  # Verify installation
  ```
  Download from: [Adoptium](https://adoptium.net/)

* **Apache Maven 3.6 or higher**
  ```sh
  mvn -version   # Verify installation
  ```
  Download from: [Maven](https://maven.apache.org/download.cgi)

* **MySQL Server 5.7 or higher**
  ```sh
  mysql --version  # Verify installation
  ```
  Download from: [MySQL](https://dev.mysql.com/downloads/mysql/)

### Installation

1. **Clone the repository**
   ```sh
   git clone https://github.com/mbelduque/Healingtouch.git
   cd Healingtouch
   ```

2. **Build the project**
   ```sh
   mvn clean compile
   ```

3. **Package the application**
   ```sh
   mvn package
   ```

### Database Setup

1. **Create the database**
   ```sql
   CREATE DATABASE healingtouchdb;
   USE healingtouchdb;
   ```

2. **Create required tables**
   ```sql
   -- Users table
   CREATE TABLE user (
       id INT AUTO_INCREMENT PRIMARY KEY,
       email VARCHAR(255) NOT NULL UNIQUE,
       password VARCHAR(255) NOT NULL
   );

   -- Patients table
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

3. **Configure database connection**
   
   Edit the database credentials in `src/main/java/com/healingtouch/config/DatabaseConfig.java` if needed:
   ```java
   private static final String URL = "jdbc:mysql://localhost:3306/healingtouchdb?useSSL=false";
   private static final String USER = "root";
   private static final String PASSWORD = ""; // Update with your password
   ```

### Running the Application

**Option 1: Using Maven (Recommended)**
```sh
mvn javafx:run
```

**Option 2: Using an IDE**
- Open the project in your IDE (Eclipse, IntelliJ IDEA, VS Code)
- Run `com.healingtouch.app.HealingTouchApp` as a Java Application

For detailed build instructions, see [BUILD_INSTRUCTIONS.md](BUILD_INSTRUCTIONS.md).

---

## Usage

### Login
- Launch the application
- Use your registered credentials to log in
- New installations will require creating a user in the database

### Patient Management
- Navigate to the patient registration section
- Enter patient details (name, ID, contact information)
- Save and manage patient records

### Appointment Scheduling
- Select a patient from the database
- Choose appointment date and time
- Assign a medical specialty
- Confirm the appointment

---

## Project Structure

```
Healingtouch/
├── src/
│   ├── main/
│   │   ├── java/com/healingtouch/
│   │   │   ├── app/              # Application entry point
│   │   │   ├── config/           # Configuration (database, etc.)
│   │   │   ├── controller/       # UI Controllers
│   │   │   ├── model/            # Domain models (entities)
│   │   │   ├── repository/       # Data access layer
│   │   │   ├── service/          # Business logic
│   │   │   └── util/             # Utilities
│   │   └── resources/
│   │       └── com/healingtouch/
│   │           ├── css/          # Stylesheets
│   │           ├── images/       # Images and icons
│   │           └── view/         # FXML view files
│   └── test/                     # Test files
├── lib/                          # External libraries
├── pom.xml                       # Maven configuration
├── README.md                     # This file
├── ARCHITECTURE.md               # Architecture documentation
├── BUILD_INSTRUCTIONS.md         # Detailed build guide
├── MIGRATION_GUIDE.md            # Migration information
└── LICENSE                       # GPL v3 License
```

---

## Documentation

- **[ARCHITECTURE.md](ARCHITECTURE.md)** - Detailed architecture and design patterns
- **[BUILD_INSTRUCTIONS.md](BUILD_INSTRUCTIONS.md)** - Complete build and deployment guide
- **[MIGRATION_GUIDE.md](MIGRATION_GUIDE.md)** - Migration and update instructions
- **[FUTURE_IMPROVEMENTS.md](FUTURE_IMPROVEMENTS.md)** - Planned features and enhancements

---

## Contributing

Contributions are what make the open source community such an amazing place to learn, inspire, and create. Any contributions you make are **greatly appreciated**.

1. Fork the Project
2. Create your Feature Branch (`git checkout -b feature/AmazingFeature`)
3. Commit your Changes (`git commit -m 'Add some AmazingFeature'`)
4. Push to the Branch (`git push origin feature/AmazingFeature`)
5. Open a Pull Request

### Development Guidelines
- Follow the existing code structure and patterns
- Add appropriate comments and documentation
- Test your changes thoroughly
- Update documentation as needed

---

## License

Distributed under the GNU General Public License v3.0. See [LICENSE](LICENSE) for more information.

---

## Contact

**Mauricio Belduque Guzmán**
- LinkedIn: [Mauricio Belduque](https://www.linkedin.com/in/mauricio-belduque-guzman-925541137/)
- GitHub: [@mbelduque](https://github.com/mbelduque)

**Project Link:** [https://github.com/mbelduque/Healingtouch](https://github.com/mbelduque/Healingtouch)

---

## Acknowledgements

* [JavaFX Documentation](https://openjfx.io/)
* [JFoenix - Material Design Components](http://www.jfoenix.com/)
* [Font Awesome Icons](https://fontawesome.com/)
* [Apache Commons](https://commons.apache.org/)
* [Maven Central Repository](https://mvnrepository.com/)
* [MySQL Documentation](https://dev.mysql.com/doc/)
* [GitHub - Version Control](https://github.com/)

---

<p align="center">
  Made with ❤️ for healthcare professionals
</p>

