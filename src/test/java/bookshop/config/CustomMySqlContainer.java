package bookshop.config;

import org.testcontainers.containers.MySQLContainer;

public class CustomMySqlContainer extends MySQLContainer<CustomMySqlContainer> {
    private static final String DB_IMAGE = "mysql:8.4";
    private static final String TEST_DB_URL = "TEST_DB_URL";
    private static final String TEST_DB_USERNAME = "TEST_DB_USERNAME";
    private static final String TEST_DB_PASSWORD = "TEST_DB_PASSWORD";

    private static CustomMySqlContainer mysqlContainer;

    private CustomMySqlContainer() {
        super(DB_IMAGE);
    }

    public static synchronized CustomMySqlContainer getInstance() {
        if (mysqlContainer == null) {
            mysqlContainer = new CustomMySqlContainer();
        }
        return mysqlContainer;
    }

    @Override
    public void start() {
        super.start();
        System.setProperty(TEST_DB_URL, mysqlContainer.getJdbcUrl());
        System.setProperty(TEST_DB_USERNAME, mysqlContainer.getUsername());
        System.setProperty(TEST_DB_PASSWORD, mysqlContainer.getPassword());
    }

    @Override
    public void stop() {
    }
}
