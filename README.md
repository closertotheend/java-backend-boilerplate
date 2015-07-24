# java-backend-boilerplate

Simple java REST server composed from various libraries.

If you are looking for the frontend which works with this project (on other dedicated server, such as Apache) then
consider to check  https://github.com/ilja903/simple-frontend-boilerplate

Idea per 2015: Use only easy interchangable minimalistic libraries. (http://tom.lokhorst.eu/2010/09/why-libraries-are-better-than-frameworks)

Idea 2 per 2015: No magic. Stay close to metal.

#### Basic bundle includes:

* Spark 2.2 (Simple REST server)

* Gson (Json serializer)

###### Connectivity

* commons-dbutils

* postgresql driver

###### DB Migrations

* Flyway

###### Testing

* JUnit

* Rest-assured (both for testing)

#### To run Flyway migrations use:

mvn clean compile flyway:migrate

#### Whole app source:
```java
public class Application {
    static ResponseTransformer toJson = new Gson()::toJson;
    static QueryRunner queryRunner = new QueryRunner();

    public static void main(String... args) {
        get("/", (request, response) -> "Greetings!", toJson);

        get("/users", (request, response) -> {
            try (Connection connection = AppDataSource.getConnection()) {
                return queryRunner.query(connection, "SELECT * FROM users", new MapListHandler());
            }
        }, toJson);

        post("/user", (request, response) -> {
            // Start transaction
            try (Connection connection = AppDataSource.getTransactConnection()) {
                JsonObject userJson = new JsonParser().parse(request.body()).getAsJsonObject();

                String name = userJson.get("name").getAsString();
                LocalDate dateOfBirth = LocalDate.parse(userJson.get("date_of_birth").getAsString());

                List<Map<String, Object>> responseObject = queryRunner.insert(connection,
                        "INSERT INTO users (name, date_of_birth) VALUES (?, '" + dateOfBirth + "');",
                        new MapListHandler(), name);

                // Commit transaction
                connection.commit();

                return responseObject;
            }
        }, toJson);


        after((request, response) -> {
            // For security reasons do not forget to change "*" to url
            response.header("Access-Control-Allow-Origin", "*");
            response.type("application/json");
        });
    }
}

```

#### Also:
Consider to add Apache commons, Guava (Not included).

If you want DI then probably best candidate is Guice.
