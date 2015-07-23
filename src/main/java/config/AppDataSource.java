package config;

import org.postgresql.ds.PGPoolingDataSource;

import java.sql.Connection;
import java.sql.SQLException;

public class AppDataSource {

    private static PGPoolingDataSource dataSource;
    static {
        // Not recommended, you may need to implement connection pooling or use apache database pooling lib
        dataSource = new PGPoolingDataSource();
        dataSource.setServerName("localhost");
        dataSource.setUser("postgres");
        dataSource.setPassword("123456");
        dataSource.setDatabaseName("spark");
    }

    public static Connection getConnection() throws SQLException {
        return dataSource.getConnection();
    }

    public static Connection getTransactConnection() throws SQLException {
            Connection connection = getConnection();
            connection.setAutoCommit(false);
            return connection;
    }

}
