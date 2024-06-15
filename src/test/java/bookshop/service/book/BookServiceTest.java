package bookshop.service.book;

import static bookshop.db_util.TestDataGenerator.FIRST_BOOK_AUTHOR;
import static bookshop.db_util.TestDataGenerator.FIRST_BOOK_COVER_IMAGE;
import static bookshop.db_util.TestDataGenerator.FIRST_BOOK_DESCRIPTION;
import static bookshop.db_util.TestDataGenerator.FIRST_BOOK_ID;
import static bookshop.db_util.TestDataGenerator.FIRST_BOOK_ISBN;
import static bookshop.db_util.TestDataGenerator.FIRST_BOOK_PRICE;
import static bookshop.db_util.TestDataGenerator.FIRST_BOOK_TITLE;
import static bookshop.db_util.TestDataGenerator.FIRST_CATEGORY_ID;
import static bookshop.db_util.TestDataGenerator.SECOND_CATEGORY_ID;
import static bookshop.db_util.TestDataGenerator.buildFirstBook;
import static bookshop.db_util.TestDataGenerator.buildFirstBookDto;
import static bookshop.db_util.TestDataGenerator.buildFirstUpdateBookDto;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.AdditionalAnswers.returnsFirstArg;
import static org.mockito.Mockito.doAnswer;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.verifyNoInteractions;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;

import bookshop.dto.book.BookDto;
import bookshop.dto.book.BookDtoWithoutCategoryIds;
import bookshop.dto.book.BookSearchParameters;
import bookshop.dto.book.CreateBookRequestDto;
import bookshop.dto.book.UpdateBookRequestDto;
import bookshop.exception.EntityNotFoundException;
import bookshop.mapper.BookMapper;
import bookshop.model.Book;
import bookshop.repository.book.BookRepository;
import bookshop.repository.book.BookSpecificationBuilder;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;

@ExtendWith(MockitoExtension.class)
class BookServiceTest {
    @InjectMocks
    private BookServiceImpl bookService;
    @Mock
    private BookRepository bookRepository;
    @Mock
    private BookSpecificationBuilder bookSpecificationBuilder;
    @Mock
    private BookMapper bookMapper;

    @Test
    @DisplayName("Create a new book")
    void save_ValidData_ReturnsBookDto() {
        final CreateBookRequestDto requestDto = new CreateBookRequestDto(
                FIRST_BOOK_TITLE,
                FIRST_BOOK_AUTHOR,
                FIRST_BOOK_ISBN,
                FIRST_BOOK_PRICE,
                FIRST_BOOK_DESCRIPTION,
                FIRST_BOOK_COVER_IMAGE,
                Set.of(FIRST_CATEGORY_ID, SECOND_CATEGORY_ID)
        );
        final Book book = buildFirstBook();
        final BookDto expected = buildFirstBookDto();

        when(bookMapper.toModel(requestDto)).thenReturn(book);
        doAnswer(returnsFirstArg()).when(bookRepository).save(book);
        when(bookMapper.toDto(book)).thenReturn(expected);

        final BookDto actual = bookService.save(requestDto);

        assertThat(actual).isEqualTo(expected);
        verifyNoMoreInteractions(bookMapper, bookRepository);
        verifyNoInteractions(bookSpecificationBuilder);
    }

    @Test
    @DisplayName("Get all available books using sorting and pagination")
    void findAll_ValidData_ReturnsBookDtoList() {
        final PageRequest pageable = PageRequest.of(0, 5);
        final Book book = buildFirstBook();
        final BookDto expected = buildFirstBookDto();

        when(bookRepository.findAll(pageable)).thenReturn(new PageImpl<>(List.of(book)));
        when(bookMapper.toDto(book)).thenReturn(expected);

        final List<BookDto> actual = bookService.findAll(pageable);

        assertThat(actual).containsExactly(expected);
        verifyNoMoreInteractions(bookMapper, bookRepository);
        verifyNoInteractions(bookSpecificationBuilder);
    }

    @Test
    @DisplayName("Search for available books by params")
    void search_ValidData_ReturnsBookDtoList() {
        final BookSearchParameters searchParameters = new BookSearchParameters(
                new String[]{FIRST_BOOK_TITLE},
                new String[]{FIRST_BOOK_AUTHOR},
                new String[]{FIRST_BOOK_PRICE.toString()}
        );
        final Specification<Book> specification = Specification.allOf();
        final Book book = buildFirstBook();
        final BookDto expected = buildFirstBookDto();

        when(bookSpecificationBuilder.build(searchParameters)).thenReturn(specification);
        when(bookRepository.findAll(specification)).thenReturn(List.of(book));
        when(bookMapper.toDto(book)).thenReturn(expected);

        final List<BookDto> actual = bookService.search(searchParameters);

        assertThat(actual).containsExactly(expected);
        verifyNoMoreInteractions(bookSpecificationBuilder, bookMapper, bookRepository);
    }

    @Test
    @DisplayName("Get an available book by id")
    void findById_ValidData_ReturnsBookDto() {
        final Book book = buildFirstBook();
        final BookDto expected = buildFirstBookDto();

        when(bookRepository.findFetchCategoriesById(FIRST_BOOK_ID)).thenReturn(Optional.of(book));
        when(bookMapper.toDto(book)).thenReturn(expected);

        final BookDto actual = bookService.findById(FIRST_BOOK_ID);

        assertThat(actual).isEqualTo(expected);
        verifyNoMoreInteractions(bookMapper, bookRepository);
        verifyNoInteractions(bookSpecificationBuilder);
    }

    @Test
    @DisplayName("Throw exception on find book by invalid id")
    void findById_InvalidData_ThrowsEntityNotFoundException() {
        when(bookRepository.findFetchCategoriesById(FIRST_BOOK_ID)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> bookService.findById(FIRST_BOOK_ID))
                .isExactlyInstanceOf(EntityNotFoundException.class)
                .hasMessage("Couldn't get book from DB by id=" + FIRST_BOOK_ID);

        verifyNoMoreInteractions(bookRepository);
        verifyNoInteractions(bookMapper, bookSpecificationBuilder);
    }

    @Test
    @DisplayName("Update an available book by id")
    void updateById_ValidData_NoContent() {
        final Book book = buildFirstBook();
        final UpdateBookRequestDto requestDto = buildFirstUpdateBookDto();

        when(bookRepository.findById(FIRST_BOOK_ID)).thenReturn(Optional.of(book));
        doNothing().when(bookMapper).updateModelFromDto(book, requestDto);
        doAnswer(returnsFirstArg()).when(bookRepository).save(book);

        bookService.updateById(FIRST_BOOK_ID, requestDto);

        verifyNoMoreInteractions(bookMapper, bookRepository);
        verifyNoInteractions(bookSpecificationBuilder);
    }

    @Test
    @DisplayName("Throw exception on update book by invalid id")
    void updateById_InvalidData_ThrowsEntityNotFoundException() {
        when(bookRepository.findById(FIRST_BOOK_ID)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> bookService
                .updateById(FIRST_BOOK_ID, buildFirstUpdateBookDto()))
                .isExactlyInstanceOf(EntityNotFoundException.class)
                .hasMessage("Couldn't get book from DB by id=" + FIRST_BOOK_ID);

        verifyNoMoreInteractions(bookRepository);
        verifyNoInteractions(bookMapper, bookSpecificationBuilder);
    }

    @Test
    @DisplayName("Delete an available book by id")
    void deleteById_ValidData_NoContent() {
        doNothing().when(bookRepository).deleteById(FIRST_BOOK_ID);

        bookService.deleteById(FIRST_BOOK_ID);

        verifyNoMoreInteractions(bookRepository);
        verifyNoInteractions(bookMapper, bookSpecificationBuilder);
    }

    @Test
    @DisplayName("Get available books by category id")
    void findAllByCategoryId_ValidData_ReturnsBookDtoWithoutCategoryIdsList() {
        final Book book = buildFirstBook();
        final BookDtoWithoutCategoryIds expected = new BookDtoWithoutCategoryIds(
                FIRST_BOOK_ID,
                FIRST_BOOK_TITLE,
                FIRST_BOOK_AUTHOR,
                FIRST_BOOK_ISBN,
                FIRST_BOOK_PRICE,
                FIRST_BOOK_DESCRIPTION,
                FIRST_BOOK_COVER_IMAGE
        );

        when(bookRepository.findAllByCategoryId(FIRST_CATEGORY_ID)).thenReturn(List.of(book));
        when(bookMapper.toDtoWithoutCategories(book)).thenReturn(expected);

        final List<BookDtoWithoutCategoryIds> actual = bookService.findAllByCategoryId(FIRST_CATEGORY_ID);

        assertThat(actual).containsExactly(expected);
        verifyNoMoreInteractions(bookMapper, bookRepository);
        verifyNoInteractions(bookSpecificationBuilder);
    }
}
