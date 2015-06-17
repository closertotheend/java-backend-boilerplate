package db.migration;

import config.AppDataSource;
import org.apache.commons.dbutils.QueryRunner;
import org.flywaydb.core.api.migration.jdbc.JdbcMigration;

import java.sql.Connection;

public class V1__Add_user_table implements JdbcMigration {
    public void migrate(Connection connection) throws Exception {
        new QueryRunner(AppDataSource.getDataSource()).update(
                "CREATE TABLE users" +
                        "(" +
                        "  id SERIAL NOT NULL," +
                        "  name VARCHAR NOT NULL," +
                        "  date_of_birth DATE NOT NULL" +
                        ")"
        );
    }
}