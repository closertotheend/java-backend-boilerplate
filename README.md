# java-backend-boilerplate

Simple java REST server composed from various libraries.

If you are looking for the frontend which works with this project (on other dedicated server, such as Apache) then
consider to check  https://github.com/ilja903/simple-frontend-boilerplate

Idea per 2015: Use only easy interchangable minimalistic libraries. 

Related: http://tom.lokhorst.eu/2010/09/why-libraries-are-better-than-frameworks

#### Basic bundle includes

* Spark 2.2 (Simple REST server)

* Gson (Json serializer)

###### Connectivity

* commons-dbutils

* commons-dbcp

* postgresql driver

###### DB Migrations

* Flyway

###### Testing

* JUnit

* Rest-assured (both for testing)

#### Also:
Consider to add Apache commons, Guava (Not included).

If you want DI then probably best candidate is Guice.
