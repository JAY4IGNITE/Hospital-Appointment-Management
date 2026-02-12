# Hospital Appointment Management System

A Java-based application for managing hospital appointments, patients, and doctors.

## Overview
This system allows for the management of hospital operations including:
- **Patient Registration**: Register new patients with their details.
- **Doctor Management**: Manage doctor profiles and specializations.
- **Appointment Booking**: Schedule appointments between patients and doctors.
- **Data Persistence**: Uses MySQL database for storing all records.

## Features
- User Authentication (Login/Register)
- Role-based Access (Admin, Doctor, Patient)
- View and Manage Appointments
- Database integration with MySQL

## Prerequisites
- **Java Development Kit (JDK)**: Version 8 or higher.
- **MySQL Server**: Ensure MySQL is installed and running.
- **MySQL Connector**: Included in `lib/mysql-connector-j-8.2.0.jar`.

## Setup Instructions

### 1. Database Configuration
1. Open your MySQL client (e.g., MySQL Workbench or Command Line).
2. Run the `database_schema.sql` script provided in the root directory to create the database and tables.
   ```sql
   source database_schema.sql;
   ```
   Or open the file and execute the SQL commands manually.

### 2. Application Configuration
Ensure the database credentials in the application match your local MySQL setup. Check the `DBConnection` or `DAO` classes (likely in `src/com/hams/dao` or `util`) if you need to change the username/password (default usually `root` / `password` or empty).

## Execution Process

### Compiling
Compile the Java source files, including the MySQL connector in the classpath.

```bash
javac -cp "lib/mysql-connector-j-8.2.0.jar;src" -d bin src/com/hams/main/Main.java
# (Note: You may need to compile all files if dependencies are complex)
# Find all java files
dir /s /B *.java > sources.txt
javac -cp "lib/mysql-connector-j-8.2.0.jar" -d bin @sources.txt
```

### Running
Run the application using the `java` command, pointing to the compiled classes and the library.

```bash
java -cp "bin;lib/mysql-connector-j-8.2.0.jar" com.hams.main.Main
```

## Project Structure
- `src/`: Source code
  - `com.hams.controller`: Logic controllers
  - `com.hams.dao`: Database Access Objects
  - `com.hams.model`: Data models
  - `com.hams.view`: GUI views
  - `com.hams.main`: Entry point
- `lib/`: External libraries (MySQL Connector)
- `database_schema.sql`: SQL script for database setup
