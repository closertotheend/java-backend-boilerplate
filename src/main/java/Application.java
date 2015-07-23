import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import config.AppDataSource;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.MapListHandler;
import spark.ResponseTransformer;

import java.sql.Connection;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;

import static spark.Spark.*;


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


