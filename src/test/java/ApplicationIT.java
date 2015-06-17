import com.google.gson.Gson;
import config.AppDataSource;
import org.apache.commons.dbutils.DbUtils;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import spark.Spark;

import javax.sql.DataSource;
import java.util.HashMap;
import java.util.Map;

import static com.jayway.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.containsString;

public class ApplicationIT {

    static DataSource dataSourceWithFalseCommit = AppDataSource.createTestDataSource();

    @BeforeClass
    public static void beforeTests() throws Exception {
        Spark.port(7777);
        Application.start(dataSourceWithFalseCommit);
        //Wait until application is loaded
        Thread.sleep(500);
    }

    @AfterClass
    public static void afterTests() throws Exception {
        Spark.stop();
    }

    @After
    public void afterTest() throws Exception {
        DbUtils.rollbackAndClose(dataSourceWithFalseCommit.getConnection());
    }


    @Test
    public void shouldSaveUser() throws Exception {
        Map<String, String> map = new HashMap<>();
        map.put("name", "Michael");
        map.put("date_of_birth", "1976-06-19");
        String json = new Gson().toJson(map);

        given().body(json).
                post("http://localhost:7777/user").
                then().body(containsString("Michael")).and().statusCode(200);
    }


}