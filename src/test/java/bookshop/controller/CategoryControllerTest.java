package bookshop.controller;

import static bookshop.db_util.TestDataGenerator.FIRST_BOOK_AUTHOR;
import static bookshop.db_util.TestDataGenerator.FIRST_BOOK_COVER_IMAGE;
import static bookshop.db_util.TestDataGenerator.FIRST_BOOK_DESCRIPTION;
import static bookshop.db_util.TestDataGenerator.FIRST_BOOK_ID;
import static bookshop.db_util.TestDataGenerator.FIRST_BOOK_ISBN;
import static bookshop.db_util.TestDataGenerator.FIRST_BOOK_PRICE;
import static bookshop.db_util.TestDataGenerator.FIRST_BOOK_TITLE;
import static bookshop.db_util.TestDataGenerator.FIRST_CATEGORY_ID;
import static bookshop.db_util.TestDataGenerator.SECOND_BOOK_AUTHOR;
import static bookshop.db_util.TestDataGenerator.SECOND_BOOK_COVER_IMAGE;
import static bookshop.db_util.TestDataGenerator.SECOND_BOOK_DESCRIPTION;
import static bookshop.db_util.TestDataGenerator.SECOND_BOOK_ID;
import static bookshop.db_util.TestDataGenerator.SECOND_BOOK_ISBN;
import static bookshop.db_util.TestDataGenerator.SECOND_BOOK_PRICE;
import static bookshop.db_util.TestDataGenerator.SECOND_BOOK_TITLE;
import static bookshop.db_util.TestDataGenerator.SECOND_CATEGORY_DESCRIPTION;
import static bookshop.db_util.TestDataGenerator.SECOND_CATEGORY_ID;
import static bookshop.db_util.TestDataGenerator.SECOND_CATEGORY_NAME;
import static bookshop.db_util.TestDataGenerator.buildFirstCategoryDto;
import static bookshop.db_util.TestDataGenerator.buildFirstUpdateCategoryDto;
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

import bookshop.dto.book.BookDtoWithoutCategoryIds;
import bookshop.dto.category.CategoryDto;
import bookshop.dto.category.CreateCategoryRequestDto;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.sql.SQLException;
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
class CategoryControllerTest {
    private static final String THIRD_CATEGORY_NAME = "Science";
    private static final String THIRD_CATEGORY_DESCRIPTION = "Science books";

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
    @DisplayName("Get all available categories using sorting and pagination")
    void getAll_CategoriesAvailable_ReturnsExpectedCategories() throws Exception {
        final MvcResult mvcResult = mockMvc
                .perform(get("/api/categories?page=0&size=2")
                        .contentType(APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();

        final CategoryDto[] actual = objectMapper.readValue(
                mvcResult.getResponse().getContentAsString(),
                CategoryDto[].class
        );

        assertThat(actual).containsExactly(buildFirstCategoryDto(), buildSecondCategoryDto());
    }

    @Test
    @DisplayName("Get an available category by id")
    void getCategoryById_CategoryAvailable_ReturnsExpectedCategory() throws Exception {
        final MvcResult mvcResult = mockMvc
                .perform(get("/api/categories/" + FIRST_CATEGORY_ID)
                        .contentType(APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();

        final CategoryDto actual = objectMapper.readValue(
                mvcResult.getResponse().getContentAsString(),
                CategoryDto.class
        );

        assertThat(actual).isEqualTo(buildFirstCategoryDto());
    }

    @Test
    @DisplayName("Create a new category")
    void createCategory_CreatesNewCategory_ReturnsNewCategory() throws Exception {
        final CreateCategoryRequestDto requestDto = new CreateCategoryRequestDto(
                THIRD_CATEGORY_NAME,
                THIRD_CATEGORY_DESCRIPTION
        );

        final MvcResult mvcResult = mockMvc
                .perform(post("/api/categories")
                        .contentType(APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(requestDto))
                        .accept(APPLICATION_JSON_VALUE))
                .andExpect(status().isCreated())
                .andReturn();

        final CategoryDto actual = objectMapper.readValue(
                mvcResult.getResponse().getContentAsString(),
                CategoryDto.class
        );

        assertThat(actual).isEqualTo(CategoryDto.builder()
                .id(3L)
                .name(THIRD_CATEGORY_NAME)
                .description(THIRD_CATEGORY_DESCRIPTION)
                .build()
        );
    }

    @Test
    @DisplayName("Update an available category by id")
    void updateCategoryById_UpdatesCategory_NoContent() throws Exception {
        mockMvc.perform(put("/api/categories/" + FIRST_CATEGORY_ID)
                        .contentType(APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(buildFirstUpdateCategoryDto()))
                        .accept(APPLICATION_JSON_VALUE))
                .andExpect(status().isNoContent());
    }

    @Test
    @DisplayName("Delete an available category by id")
    void deleteCategoryById_DeletesCategory_NoContent() throws Exception {
        mockMvc.perform(delete("/api/categories/" + FIRST_CATEGORY_ID)
                        .contentType(APPLICATION_JSON))
                .andExpect(status().isNoContent());
    }

    @Test
    @DisplayName("Get available books by category id")
    void getBooksByCategoryId_BooksAvailable_ReturnsExpectedBooks() throws Exception {
        final BookDtoWithoutCategoryIds firstExpected = new BookDtoWithoutCategoryIds(
                FIRST_BOOK_ID,
                FIRST_BOOK_TITLE,
                FIRST_BOOK_AUTHOR,
                FIRST_BOOK_ISBN,
                FIRST_BOOK_PRICE,
                FIRST_BOOK_DESCRIPTION,
                FIRST_BOOK_COVER_IMAGE
        );
        final BookDtoWithoutCategoryIds secondExpected = new BookDtoWithoutCategoryIds(
                SECOND_BOOK_ID,
                SECOND_BOOK_TITLE,
                SECOND_BOOK_AUTHOR,
                SECOND_BOOK_ISBN,
                SECOND_BOOK_PRICE,
                SECOND_BOOK_DESCRIPTION,
                SECOND_BOOK_COVER_IMAGE
        );

        final MvcResult mvcResult = mockMvc
                .perform(get("/api/categories/" + FIRST_CATEGORY_ID + "/books")
                        .contentType(APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();

        final BookDtoWithoutCategoryIds[] actual = objectMapper.readValue(
                mvcResult.getResponse().getContentAsString(),
                BookDtoWithoutCategoryIds[].class
        );

        assertThat(actual).containsExactly(firstExpected, secondExpected);
    }

    public static CategoryDto buildSecondCategoryDto() {
        return CategoryDto.builder()
                .id(SECOND_CATEGORY_ID)
                .name(SECOND_CATEGORY_NAME)
                .description(SECOND_CATEGORY_DESCRIPTION)
                .build();
    }
}
