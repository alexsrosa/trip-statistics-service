## **Trip Statistics Service from Department of Transportation**

### What this is

A simple tool that allows the visualization of boarding and disembarking of passengers in the city by the users of the
transport department.

---

### **Start-up project**

Docker compose helps us to upload the whole stack in a simple and fast way with just one command.
To do this, run the command below:

```sh
docker-compose up --build --force-recreate -d
```

**-d** does not block your terminal, but if you want to follow the logs directly, you can remove it.

Another way to follow the logs:

```sh
docker-compose logs -f trip-statistics-service
docker-compose logs -f posgres
```

#### Troubleshooting

- If you run docker-compose and get an invalid command error, make sure you have both Docker and Docker compose
  installed on your machine. It is also updated.
- If you don't remember, you can check if your docker has one running and stop if necessary.
    - See active containers: ``docker ps``
    - Stop all containers: ``docker stop $(docker ps -a -q)``
- If any port errors or conflicts arise, it is suggested to clean up your local docker with the command below:
    - This will remove all unused images and containers, among other docker resources: ``docker system prune``

---

### System architectural approach

This application follows a layered architecture that resembles the clean architecture.
A more detailed explanation of the layers follows:

- **Model**: Layer that has mapping of database objects and their relationships.
- **Repository**: In this layer, [Spring Data](https://spring.io/projects/spring-data)
  is used to represent operations performed on the database, such as saving and searching the saved data.
- **Service**: This layer represents the business part linked to database objects
  and contains fundamental rules of use.
- **UseCase**: This layer represents the features of the system and can have links with one or more
  services ([Facade Pattern](https://refactoring.guru/design-patterns/facade)).
- **Controller**: This layer holds the system entry points for data exposure.

#### Details about application modules

The application is divided into 4 modules, they are:

- **api**: is the module that represents input and output DTOs and can be easily extended in another project if needed.
- **application**: Application bootstrap module with all application entrypoints and main settings.
- **core**: Main module that contains all the business rule and main behaviors.
- **model**: Module with the responsibility of telling the database model and repository behaviors.

#### Technologies used in this project

- [Java 17](https://jdk.java.net/17)
- [Maven Build Tool](https://maven.apache.org/)
- [Stack Spring](https://spring.io/)
- [Docker, Docker-Compose](https://www.docker.com/)
- [Database Postgres](https://www.postgresql.org/)

#### Database design

Existing tables in the project:

- **taxis**: The green and yellow taxi datasets contain the trip data. There is information such as pick-up and drop-off
  times and the pick-up and drop-off location IDs.
- **zones**: The zones dataset contains the id and names of the different zones of the city. These ids are the same ones
  that are referenced from the taxi trips pickup and drop-off locations.

Query to see the total records in the database:

  ````sql
  select (select count(*) from taxis) as taxis,
         (select count(*) from zones) as zones;
  ````

##### Liquibase Instructions

Liquibase is a database version control tool that enables automation and tracking of schema changes.
It is useful for maintaining consistency, facilitating collaboration among developers, and ensuring controlled database
deployment across different environments.

In order to create the migration file open **src/model** and execute:

```sh
mvn liquibase:diff
```

To migrate changes use the following:

```sh
mvn liquibase:update
```

> However, it's worth mentioning that when starting the application in an IDE or with Docker,
**no command needs to be executed**, unless we are making new database changes during development.

#### Open API view

This project has Open Api settings, which provide details about the api available in this application.
Enter the url below to access on localhost:
http://localhost:8080/api/swagger-ui/index.html#/

---

### Business resources included in this project

Yellow and green taxi trip records include fields capturing pick-up and drop-off dates/times, pick-up and drop-off
locations That will help us to get great insights and below will be presenting the query interfaces (API).

#### _API - Exposed resources_

### `GET /api/v1/trips/top_zones`

- This endpoint will return a list of the first 5 zones order by number of total pickups or the number of
  total drop-offs.
- This endpoint accept 1 parameter:
    - **order** _required*_: values can be **dropoffs** or **pickups**
- Curl example:
  ````
  curl --location --request GET \
  'http://localhost:8080/api/v1/trips/top_zones?order=pickups'
  ````
- Response example:

````json
{
  "top_zones": [
    {
      "zone": "JFK Airport",
      "pu_total": 160045,
      "do_total": 33709
    },
    {
      "zone": "Upper East Side South",
      "pu_total": 148074,
      "do_total": 133396
    },
    {
      "zone": "Upper East Side North",
      "pu_total": 138835,
      "do_total": 149112
    },
    {
      "zone": "Midtown Center",
      "pu_total": 135418,
      "do_total": 116585
    },
    {
      "zone": "Penn Station/Madison Sq West",
      "pu_total": 109229,
      "do_total": 67500
    }
  ]
}
````

### `GET /api/v1/trips/zone-trips`

- This endpoint will return the sum of the pickups and drop-offs in just one zone and one date.
- This this endpoint accept 2 parameter:
    - **zone** _(Required*)_: value must be the zone **id** or **zone** of any of the available zones
    - **date** _(Required*)_: value must be a date

- Curl example:

````
curl --location --request GET \
'http://localhost:8080/api/v1/trips/zone-trips?date=2023-01-24&zone=231'
````

- Response example:

````json
{
  "zone": "TriBeCa/Civic Center",
  "date": "2023-01-24",
  "pu": 1611,
  "do": 1471
}
````

### `GET /api/v1/trips/list-yellow`

- This endpoint will return data from the yellow trip file with pagination + filtering and multiple sort.
- This endpoint accept 5 parameter to filter:
    - **id** _(Optional)_: unique identifier of taxi
    - **pu_date** _(Optional)_: Indication pick-ups date.
    - **do_date** _(Optional)_: Indication drop-offs date.
    - **pu_location** _(Optional)_: Indication pick-ups locations exists on table zones.
    - **do_location** _(Optional)_: Indication drop-offs locations exists on table zones.
- This endpoint accept 2 parameter for pagination/sort:
    - **page** indicate number of page
    - **size** indicate size for witch page
    - **sort** indicate field to sort and if ``desc`` or ``asc``
        - Example: ``sort=id,desc``
        - Valid sort field: id, pu_date, do_date, pu_location, do_location

- Curl example:

````
curl --location --request GET \
'http://localhost:8080/api/v1/trips/list-yellow?pu_date=2023-01-19T11:16:11&do_date=2023-01-19T11:42:21&pu_location=143&do_location=170'
````

- Response example:

````json
{
  "content": [
    {
      "id": "ec43ba38-d111-476c-bc39-ca896774371d",
      "pu_date": "2023-01-19T11:16:11",
      "do_date": "2023-01-19T11:42:21",
      "pu_location": 143,
      "do_location": 170
    }
  ],
  "pageable": {
    "sort": {
      "empty": true,
      "sorted": false,
      "unsorted": true
    },
    "offset": 0,
    "pageNumber": 0,
    "pageSize": 20,
    "paged": true,
    "unpaged": false
  },
  "totalPages": 1,
  "totalElements": 1,
  "last": true,
  "size": 20,
  "number": 0,
  "sort": {
    "empty": true,
    "sorted": false,
    "unsorted": true
  },
  "numberOfElements": 1,
  "first": true,
  "empty": false
}
````

### **Important considerations**

- When launching the app with docker, it is not blocked by loading CSVs. That is, the application is available to
  receive requests and the CSV loading process runs in the background
- The application has Spring Cache in database queries, which are evicted whenever there is a new load or the cvs load
  ends.

### Future technical improvements

- Create a maven image with dependencies cache to make docker startup faster;
- Change the application to read the new CSV in an external directory or in the cloud so as not to need a new deployment
  with each update.
- Improve the performance of the top 5 zones results.