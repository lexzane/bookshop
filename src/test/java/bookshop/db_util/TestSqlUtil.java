package bookshop.db_util;

import java.sql.Connection;
import java.sql.SQLException;
import javax.sql.DataSource;
import org.springframework.core.io.ClassPathResource;
import org.springframework.jdbc.datasource.init.ScriptUtils;

public final class TestSqlUtil {
    public static void executeDelete(final DataSource dataSource) throws SQLException {
        try (final Connection connection = dataSource.getConnection()) {
            connection.setAutoCommit(true);
            executeSqlScript(connection, "database/books_categories/delete-books-categories.sql");
            executeSqlScript(connection, "database/categories/delete-categories.sql");
            executeSqlScript(connection, "database/books/delete-books.sql");
        }
    }

    public static void executeInsert(final DataSource dataSource) throws SQLException {
        try (final Connection connection = dataSource.getConnection()) {
            connection.setAutoCommit(true);
            executeDelete(dataSource);
            executeSqlScript(connection, "database/books/insert-books.sql");
            executeSqlScript(connection, "database/categories/insert-categories.sql");
            executeSqlScript(connection, "database/books_categories/insert-books-categories.sql");
        }
    }

    private static void executeSqlScript(final Connection connection, final String path) {
        ScriptUtils.executeSqlScript(connection, new ClassPathResource(path));
    }
}
