# SWIFT Codes API System

Spring Boot application for managing SWIFT/BIC codes with a PostgreSQL database. Fully containerized using Docker and ready for deployment.

## Features

- REST API for managing SWIFT codes
- Support for headquarter–branch relationships
- Automatic data import from CSV on startup
- Centralized exception handling
- Dockerized setup with PostgreSQL

## Technologies Used

- Java 21
- Spring Boot
- Spring Web
- Spring Data JPA
- PostgreSQL
- Docker & Docker Compose
- Gradle (via wrapper)
- Apache Commons CSV

## Requirements

- Docker
- Docker Compose

## Quick Start

### 1. Clone the repository

```bash
git clone https://github.com/grzesryba/swift-codes-api.git
cd swift-codes-api
```

### 2. Start the application

```bash
cd docker
docker-compose up --build -d
```

Application will be available at: `http://localhost:8080`

## API Documentation

Base URL: `http://localhost:8080/v1/swift-codes`

### 1. Get SWIFT Code Details

**GET** `/v1/swift-codes/{swiftCode}`  
Example: `http://localhost:8080/v1/swift-codes/AIZKLV22XXX`

Response for headquarters:

```json
{
    "address": "MIHAILA TALA STREET 1  RIGA, RIGA, LV-1045",
    "bankName": "ABLV BANK, AS IN LIQUIDATION",
    "countryISO2": "LV",
    "countryName": "LATVIA",
    "isHeadquarter": true,
    "swiftCode": "AIZKLV22XXX",
    "branches": [
        {
            "address": "ELIZABETES STREET 23  RIGA, RIGA, LV-1010",
            "bankName": "ABLV BANK, AS IN LIQUIDATION",
            "countryISO2": "LV",
            "countryName": "LATVIA",
            "isHeadquarter": false,
            "swiftCode": "AIZKLV22CLN"
        }
    ]
}
```

### 2. Get SWIFT Codes by Country

**GET** `/v1/swift-codes/country/{countryISO2}`  
Example: `http://localhost:8080/v1/swift-codes/country/MC`

Response(part):

```json
{
    "countryISO2": "MC",
    "countryName": "MONACO",
    "swiftCodes": [
        {
            "address": "12 BOULEVARD DES MOULINS  MONACO, MONACO, 98000",
            "bankName": "BANK JULIUS BAER (MONACO) S.A.M.",
            "countryISO2": "MC",
            "countryName": "MONACO",
            "isHeadquarter": true,
            "swiftCode": "BAERMCMCXXX"
        },
        {
            "address": "31 AVENUE DE LA COSTA  MONACO, MONACO, 98000",
            "bankName": "BARCLAYS BANK S.A",
            "countryISO2": "MC",
            "countryName": "MONACO",
            "isHeadquarter": true,
            "swiftCode": "BARCMCC1XXX"
        },
    ]
  }
```

### 3. Add New SWIFT Code

**POST** `/v1/swift-codes`

Request example:

```json
{
    "address": "ul. Testowa 123, Warszawa",
    "bankName": "Bank Testowy",
    "countryISO2": "PL",
    "countryName": "POLAND",
    "isHeadquarter": false,
    "swiftCode": "ABCDEFG1234"
}
```

### 4. Delete SWIFT Code

**DELETE** `/v1/swift-codes/{swiftCode}`  
Example: `http://localhost:8080/v1/swift-codes/ABCDEFG1234`

Response:

``` bash
SWIFT code deleted successfully: ABCDEFG1234
```

## Author

Grzegorz Rybiński
