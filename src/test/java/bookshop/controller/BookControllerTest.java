package bookshop.controller;

import static bookshop.db_util.TestDataGenerator.FIRST_BOOK_AUTHOR;
import static bookshop.db_util.TestDataGenerator.FIRST_BOOK_ID;
import static bookshop.db_util.TestDataGenerator.FIRST_BOOK_PRICE;
import static bookshop.db_util.TestDataGenerator.FIRST_BOOK_TITLE;
import static bookshop.db_util.TestDataGenerator.SECOND_BOOK_AUTHOR;
import static bookshop.db_util.TestDataGenerator.SECOND_BOOK_COVER_IMAGE;
import static bookshop.db_util.TestDataGenerator.SECOND_BOOK_DESCRIPTION;
import static bookshop.db_util.TestDataGenerator.SECOND_BOOK_ID;
import static bookshop.db_util.TestDataGenerator.SECOND_BOOK_ISBN;
import static bookshop.db_util.TestDataGenerator.SECOND_BOOK_PRICE;
import static bookshop.db_util.TestDataGenerator.SECOND_BOOK_TITLE;
import static bookshop.db_util.TestDataGenerator.SECOND_CATEGORY_ID;
import static bookshop.db_util.TestDataGenerator.buildFirstBookDto;
import static bookshop.db_util.TestDataGenerator.buildFirstUpdateBookDto;
import static bookshop.db_util.TestSqlUtil.executeDelete;
import static bookshop.db_util.TestSqlUtil.executeInsert;
import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import bookshop.dto.book.BookDto;
import bookshop.dto.book.CreateBookRequestDto;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.Set;
import javax.sql.DataSource;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

@AutoConfigureMockMvc
@ExtendWith(SpringExtension.class)
@WithMockUser(username = "John Doe", authorities = {"ADMIN", "USER"})
@SpringBootTest(webEnvironment = RANDOM_PORT)
class BookControllerTest {
    private static final String FOURTH_BOOK_TITLE = "A New Hope";
    private static final String FOURTH_BOOK_AUTHOR = "Alan Dean Foster";
    private static final String FOURTH_BOOK_ISBN = "978-0345341464";
    private static final BigDecimal FOURTH_BOOK_PRICE = BigDecimal.valueOf(29.99);
    private static final String FOURTH_BOOK_DESCRIPTION = "Episode IV - A New Hope";
    private static final String FOURTH_BOOK_COVER_IMAGE = "https://example.com/cover4.jpg";

    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;

    @BeforeEach
    void setUp(@Autowired final DataSource dataSource) throws SQLException {
        executeInsert(dataSource);
    }

    @AfterEach
    void tearDown(@Autowired final DataSource dataSource) throws SQLException {
        executeDelete(dataSource);
    }

    @Test
    @DisplayName("Get all available books using sorting and pagination")
    void getAll_BooksAvailable_ReturnsExpectedBooks() throws Exception {
        final MvcResult mvcResult = mockMvc
                .perform(get("/api/books?page=0&size=3")
                        .contentType(APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();

        final BookDto[] actual = objectMapper.readValue(
                mvcResult.getResponse().getContentAsString(),
                BookDto[].class
        );

        assertThat(actual).containsExactly(buildFirstBookDto(), buildSecondBookDto(), buildThirdBookDto());
    }

    @Test
    @DisplayName("Get an available book by id")
    void getBookById_BookAvailable_ReturnsExpectedBook() throws Exception {
        final MvcResult mvcResult = mockMvc
                .perform(get("/api/books/" + FIRST_BOOK_ID)
                        .contentType(APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();

        final BookDto actual = objectMapper.readValue(
                mvcResult.getResponse().getContentAsString(),
                BookDto.class
        );

        assertThat(actual).isEqualTo(buildFirstBookDto());
    }

    @Test
    @DisplayName("Search for available books by params")
    void searchBooks_BooksAvailable_ReturnsExpectedBooks() throws Exception {
        final MvcResult mvcResult = mockMvc
                .perform(get("/api/books/search"
                        + "?prices=" + FIRST_BOOK_PRICE
                        + "&authors=" + FIRST_BOOK_AUTHOR
                        + "&titles=" + FIRST_BOOK_TITLE)
                        .contentType(APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();

        final BookDto[] actual = objectMapper.readValue(
                mvcResult.getResponse().getContentAsString(),
                BookDto[].class
        );

        assertThat(actual).containsExactly(buildFirstBookDto());
    }

    @Test
    @DisplayName("Create a new book")
    void createBook_CreatesNewBook_ReturnsNewBook() throws Exception {
        final CreateBookRequestDto requestDto = new CreateBookRequestDto(
                FOURTH_BOOK_TITLE,
                FOURTH_BOOK_AUTHOR,
                FOURTH_BOOK_ISBN,
                FOURTH_BOOK_PRICE,
                FOURTH_BOOK_DESCRIPTION,
                FOURTH_BOOK_COVER_IMAGE,
                Set.of(SECOND_CATEGORY_ID)
        );

        final MvcResult mvcResult = mockMvc
                .perform(post("/api/books")
                        .contentType(APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(requestDto))
                        .accept(APPLICATION_JSON_VALUE))
                .andExpect(status().isCreated())
                .andReturn();

        final BookDto actual = objectMapper.readValue(
                mvcResult.getResponse().getContentAsString(),
                BookDto.class
        );

        assertThat(actual).isEqualTo(BookDto.builder()
                .id(4L)
                .title(FOURTH_BOOK_TITLE)
                .author(FOURTH_BOOK_AUTHOR)
                .isbn(FOURTH_BOOK_ISBN)
                .price(FOURTH_BOOK_PRICE)
                .description(FOURTH_BOOK_DESCRIPTION)
                .coverImage(FOURTH_BOOK_COVER_IMAGE)
                .build()
        );
    }

    @Test
    @DisplayName("Update an available book by id")
    void updateBookById_UpdatesBook_NoContent() throws Exception {
        mockMvc.perform(put("/api/books/" + FIRST_BOOK_ID)
                        .contentType(APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(buildFirstUpdateBookDto()))
                        .accept(APPLICATION_JSON_VALUE))
                .andExpect(status().isNoContent());
    }

    @Test
    @DisplayName("Delete an available book by id")
    void deleteBookById_DeletesBook_NoContent() throws Exception {
        mockMvc.perform(delete("/api/books/" + FIRST_BOOK_ID)
                        .contentType(APPLICATION_JSON))
                .andExpect(status().isNoContent());
    }

    public static BookDto buildSecondBookDto() {
        return BookDto.builder()
                .id(SECOND_BOOK_ID)
                .title(SECOND_BOOK_TITLE)
                .author(SECOND_BOOK_AUTHOR)
                .isbn(SECOND_BOOK_ISBN)
                .price(SECOND_BOOK_PRICE)
                .description(SECOND_BOOK_DESCRIPTION)
                .coverImage(SECOND_BOOK_COVER_IMAGE)
                .build();
    }

    public static BookDto buildThirdBookDto() {
        return BookDto.builder()
                .id(3L)
                .title("Revenge of the Sith")
                .author("Matthew Stover")
                .isbn("978-0345428837")
                .price(BigDecimal.valueOf(29.99))
                .description("Episode III - Revenge of the Sith")
                .coverImage("https://example.com/cover3.jpg")
                .build();
    }
}
