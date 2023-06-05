# **Trip Statistics Service from Department of Transportation**

## **What this is**

A simple tool that allows the visualization of boarding and disembarking of passengers in the city by the users of the transport department.

## **Requirements**

 - Maven
 - Java 17
 - Docker

## **Start-up**

Run the following commands in the command line:

    - docker-compose up --build --force-recreate -d

## **Instructions**

(...) TODO

## Application design

The application is divided into 4 modules, they are:
- api: is the module that represents input and output DTOs and can be easily extended in another project if needed.
- application: Application bootstrap module with all application entrypoints and main settings.
- core: Main module that contains all the business rule and main behaviors.
- model: Module with the responsibility of telling the database model and repository behaviors.

## Database design 

Existing tables in the project:

- taxis: The green and yellow taxi datasets contain the trip data. There is information such as pick-up and drop-off times and the pick-up and drop-off location IDs.
- zones: The zones dataset contains the id and names of the different zones of the city. These ids are the same ones that are referenced from the taxi trips pickup and drop-off locations.

### Liquibase Instructions

In order to create the migration file open **src/model** and execute:

```
mvn liquibase:diff -Plocal
```

To migrate changes use the following:
```
mvn liquibase:update -Plocal 
```

## Open API

Access Url: http://localhost:8080/api/swagger-ui/index.html#/