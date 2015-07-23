package db.migration;

import org.apache.commons.dbutils.QueryRunner;
import org.flywaydb.core.api.migration.jdbc.JdbcMigration;

import java.sql.Connection;

public class V2__Add_fixture_users implements JdbcMigration {
    public void migrate(Connection connection) throws Exception {
        new QueryRunner().update(connection,
                "INSERT INTO users (name, date_of_birth) VALUES ('Jonhson', '1988-01-29');" +
                        "INSERT INTO users (name, date_of_birth) VALUES ('Lars', '1970-11-24');" +
                        "INSERT INTO users (name, date_of_birth) VALUES ('Mark', '1992-11-15');"
        );
    }
}