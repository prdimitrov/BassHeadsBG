# BassHeadsBG

## 📝 Description
BassHeadsBG is a Spring Boot-based web application that serves as a project for the BassHeads. It utilizes a combination of modern frameworks, libraries, and tools to provide a robust backend with efficient data handling and security.

## 📦 Libraries Used
- **Hibernate**: For object-relational mapping (ORM) and persistence.
- **ModelMapper**: For mapping Java objects (DTOs to entities and vice versa).
- **Lombok**: To reduce boilerplate code by generating getters, setters, and constructors automatically.

## 🐬 Database Used
- **MySQL**: For storing and managing relational data.

## 🌱 Framework Used
- **Spring Boot**: A framework for creating applications with minimal configuration.
- **Spring Data JPA**: For simplifying data persistence and repository patterns using JPA.
- **Spring Security**: To handle authentication and authorization, ensuring secure access control.
- **Thymeleaf**: As a server-side Java templating engine for rendering dynamic HTML content.

## 🔧 Features
- 🛡️ **Spring Security**: Secure your application with role-based access control and form authentication.
- 📊 **Model Mapping**: Use ModelMapper to easily map between DTOs and entities.
- 🗄️ **MySQL Integration**: Store and retrieve data using MySQL, managed via JPA/Hibernate

## 😢 *Outdated features*
- 💬 **Kafka**: A microservice used to send image URLs between BassHeadsBG and ImageHoster.
- 🔥 **Google Firebase**: Previously integrated into the project for sending image URLs using **Kafka** in the [ImageHoster](https://github.com/prdimitrov/ImageHoster) project. The images were uploaded to **Google Firebase**, and the new URLs were stored in the project **database**. However, this integration is no longer in use due to the paid nature of Google Storage services.

## 🚀 Getting Started

### Prerequisites
- ☕ **Java 17**
- 🏗️ **Maven**: Make sure Maven is installed on your machine.
- 🐬 **MySQL**: Ensure MySQL is set up and a database is created.

### Installation
1. Clone the repository
   ```
   git clone https://github.com/prdimitrov/bassheadsbg.git

2. Configure

   ```
   Before running the application, make sure you set the required 
   environment variables for the database, the first admin account, and any external APIs.
   
3. Run the project
   ```
   :)

## 🌍 Environment Variables
The following environment variables must be defined in your system:

| Variable Name         | Description                             |
|-----------------------|-----------------------------------------|
| `db_username`         | MySQL database username                 |
| `db_password`         | MySQL database password                 |
| `forex_api_key`       | API key for Forex rates (**Open Exchange Rates API**) |
| `accu_key`            | API key for **AccuWeather** data            |
| `admin_username`      | Admin user's username                   |
| `admin_password`      | Admin user's password                   |
| `admin_email`         | Admin user's email                      |
| `admin_firstName`     | Admin user's first name                 |
| `admin_lastName`      | Admin user's last name                  |
| `admin_birthDate`     | Admin user's birth date (format: **YYYY-MM-DD**) |

### Additional explanation
- The **accu_key** is used to get the most of the cities in Bulgaria,
so user can choose his city when registering, or editing his profile.
- The forex_api_key is used to retrieve the currency rates from ***www.openexchangerates.org***, which is used to show the prices for different objects (speakers, cables, etc) in different currencies.
- All the variables, starting with **admin_** are used for initializing the first admin's **username**, **password**, **email**, **first name**, **last name** and **birthdate**.

# Sample photos 
``` 
WILL BE ADDED SOON!
