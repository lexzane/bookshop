package bookshop.repository.book;

import static bookshop.db_util.TestDataGenerator.FIRST_CATEGORY_ID;
import static bookshop.db_util.TestDataGenerator.SECOND_BOOK_AUTHOR;
import static bookshop.db_util.TestDataGenerator.SECOND_BOOK_COVER_IMAGE;
import static bookshop.db_util.TestDataGenerator.SECOND_BOOK_DESCRIPTION;
import static bookshop.db_util.TestDataGenerator.SECOND_BOOK_ID;
import static bookshop.db_util.TestDataGenerator.SECOND_BOOK_ISBN;
import static bookshop.db_util.TestDataGenerator.SECOND_BOOK_PRICE;
import static bookshop.db_util.TestDataGenerator.SECOND_BOOK_TITLE;
import static bookshop.db_util.TestDataGenerator.buildFirstBook;
import static bookshop.db_util.TestSqlUtil.executeDelete;
import static bookshop.db_util.TestSqlUtil.executeInsert;
import static org.apache.commons.lang3.math.NumberUtils.LONG_ONE;
import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace.NONE;

import bookshop.model.Book;
import bookshop.model.Category;
import java.sql.SQLException;
import java.util.List;
import java.util.Set;
import javax.sql.DataSource;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

@DataJpaTest
@AutoConfigureTestDatabase(replace = NONE)
class BookRepositoryTest {
    @Autowired
    private BookRepository bookRepository;

    @BeforeAll
    static void beforeAll(@Autowired final DataSource dataSource) throws SQLException {
        executeInsert(dataSource);
    }

    @AfterAll
    static void afterAll(@Autowired final DataSource dataSource) throws SQLException {
        executeDelete(dataSource);
    }

    @Test
    @DisplayName("Get available books by category id")
    void findAllByCategoryId_BooksAvailable_ReturnsBooks() {
        final List<Book> actual = bookRepository.findAllByCategoryId(LONG_ONE);

        assertThat(actual).containsExactly(buildFirstBook(), buildSecondBook());
    }

    private static Book buildSecondBook() {
        return new Book()
                .setId(SECOND_BOOK_ID)
                .setTitle(SECOND_BOOK_TITLE)
                .setAuthor(SECOND_BOOK_AUTHOR)
                .setIsbn(SECOND_BOOK_ISBN)
                .setPrice(SECOND_BOOK_PRICE)
                .setDescription(SECOND_BOOK_DESCRIPTION)
                .setCoverImage(SECOND_BOOK_COVER_IMAGE)
                .setCategories(Set.of(new Category(FIRST_CATEGORY_ID)));
    }
}
