package config;

import org.apache.commons.dbcp.BasicDataSource;

import javax.sql.DataSource;

public class AppDataSource {

    static BasicDataSource dataSource = createMostBasicDataSource();

    private static BasicDataSource createMostBasicDataSource() {
        BasicDataSource dataSource = new BasicDataSource();
        dataSource.setDriverClassName("org.postgresql.Driver");
        dataSource.setUsername("postgres");
        dataSource.setPassword("123456");
        dataSource.setUrl("jdbc:postgresql://localhost/spark");
        return dataSource;
    }

    public static BasicDataSource getDataSource() {
        return dataSource;
    }

    /*
    * create datasource with only one connection and without default autocommit
    * */
    public static DataSource createTestDataSource() {
        BasicDataSource dataSource = createMostBasicDataSource();
        dataSource.setDefaultAutoCommit(false);
        dataSource.setMaxActive(1);
        dataSource.setMinIdle(1);
        return dataSource;
    }

}
