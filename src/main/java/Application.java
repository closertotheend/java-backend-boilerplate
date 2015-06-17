import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import config.AppDataSource;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.MapListHandler;

import javax.sql.DataSource;
import java.time.LocalDate;

import static spark.Spark.*;


public class Application {

    public static void main(String... args) {
        try {
            start(AppDataSource.getDataSource());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void start(DataSource dataSource)  {
        QueryRunner queryRunner = new QueryRunner(dataSource);

        get("/",
                (request, response) -> "Greetings!",
                new Gson()::toJson);

        get("/users",
                (request, response) -> queryRunner.query("SELECT * FROM users", new MapListHandler()),
                new Gson()::toJson);

        post("/user",
                (request, response) -> {
                    JsonObject jsonRequest = new JsonParser().parse(request.body()).getAsJsonObject();

                    String name = jsonRequest.get("name").getAsString();
                    LocalDate dateOfBirth = LocalDate.parse(jsonRequest.get("date_of_birth").getAsString());

                    return queryRunner.insert(
                            "INSERT INTO users (name, date_of_birth) VALUES (?, '" + dateOfBirth + "');",
                            new MapListHandler(), name);

                },
                new Gson()::toJson);


        after((request, response) -> {
            response.header("Access-Control-Allow-Origin", "http://localhost");
            response.type("application/json");
        });
    }


}


