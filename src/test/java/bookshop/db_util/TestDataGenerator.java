package bookshop.db_util;

import static org.apache.commons.lang3.math.NumberUtils.LONG_ONE;

import bookshop.dto.book.BookDto;
import bookshop.dto.book.UpdateBookRequestDto;
import bookshop.dto.category.CategoryDto;
import bookshop.dto.category.UpdateCategoryRequestDto;
import bookshop.model.Book;
import bookshop.model.Category;
import java.math.BigDecimal;
import java.util.Set;

public final class TestDataGenerator {
    public static final Long FIRST_BOOK_ID = LONG_ONE;
    public static final String FIRST_BOOK_TITLE = "The Phantom Menace";
    public static final String FIRST_BOOK_AUTHOR = "Terry Brooks";
    public static final String FIRST_BOOK_ISBN = "978-0590010894";
    public static final BigDecimal FIRST_BOOK_PRICE = BigDecimal.valueOf(9.99);
    public static final String FIRST_BOOK_DESCRIPTION = "Episode I - The Phantom Menace";
    public static final String FIRST_BOOK_COVER_IMAGE = "https://example.com/cover1.jpg";

    public static final Long SECOND_BOOK_ID = 2L;
    public static final String SECOND_BOOK_TITLE = "Attack of the Clones";
    public static final String SECOND_BOOK_AUTHOR = "R.A. Salvatore";
    public static final String SECOND_BOOK_ISBN = "978-0099410577";
    public static final BigDecimal SECOND_BOOK_PRICE = BigDecimal.valueOf(19.99);
    public static final String SECOND_BOOK_DESCRIPTION = "Episode II - Attack of the Clones";
    public static final String SECOND_BOOK_COVER_IMAGE = "https://example.com/cover2.jpg";

    public static final Long FIRST_CATEGORY_ID = LONG_ONE;
    public static final String FIRST_CATEGORY_NAME = "Sci-Fi";
    public static final String FIRST_CATEGORY_DESCRIPTION = "Sci-Fi books";

    public static final Long SECOND_CATEGORY_ID = 2L;
    public static final String SECOND_CATEGORY_NAME = "Fantasy";
    public static final String SECOND_CATEGORY_DESCRIPTION = "Fantasy books";

    public static Book buildFirstBook() {
        return new Book()
                .setId(FIRST_BOOK_ID)
                .setTitle(FIRST_BOOK_TITLE)
                .setAuthor(FIRST_BOOK_AUTHOR)
                .setIsbn(FIRST_BOOK_ISBN)
                .setPrice(FIRST_BOOK_PRICE)
                .setDescription(FIRST_BOOK_DESCRIPTION)
                .setCoverImage(FIRST_BOOK_COVER_IMAGE)
                .setCategories(Set.of(
                        new Category(FIRST_CATEGORY_ID),
                        new Category(SECOND_CATEGORY_ID)
                ));
    }

    public static BookDto buildFirstBookDto() {
        return BookDto.builder()
                .id(FIRST_BOOK_ID)
                .title(FIRST_BOOK_TITLE)
                .author(FIRST_BOOK_AUTHOR)
                .isbn(FIRST_BOOK_ISBN)
                .price(FIRST_BOOK_PRICE)
                .description(FIRST_BOOK_DESCRIPTION)
                .coverImage(FIRST_BOOK_COVER_IMAGE)
                .build();
    }

    public static UpdateBookRequestDto buildFirstUpdateBookDto() {
        return new UpdateBookRequestDto(
                FIRST_BOOK_TITLE,
                FIRST_BOOK_AUTHOR,
                FIRST_BOOK_ISBN,
                BigDecimal.valueOf(21.99),
                FIRST_BOOK_DESCRIPTION,
                FIRST_BOOK_COVER_IMAGE,
                Set.of(FIRST_CATEGORY_ID, SECOND_CATEGORY_ID)
        );
    }

    public static CategoryDto buildFirstCategoryDto() {
        return CategoryDto.builder()
                .id(FIRST_CATEGORY_ID)
                .name(FIRST_CATEGORY_NAME)
                .description(FIRST_CATEGORY_DESCRIPTION)
                .build();
    }

    public static UpdateCategoryRequestDto buildFirstUpdateCategoryDto() {
        return new UpdateCategoryRequestDto(
                FIRST_CATEGORY_NAME,
                "New description"
        );
    }
}
