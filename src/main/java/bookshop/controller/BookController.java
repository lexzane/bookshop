package bookshop.controller;

import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.HttpStatus.NO_CONTENT;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

import bookshop.dto.book.BookDto;
import bookshop.dto.book.BookSearchParameters;
import bookshop.dto.book.CreateBookRequestDto;
import bookshop.dto.book.UpdateBookRequestDto;
import bookshop.service.book.BookService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Positive;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@Validated
@RestController
@RequestMapping("/api/books")
@RequiredArgsConstructor
@Tag(name = "Book management", description = "Endpoints for managing books")
public class BookController {
    private final BookService bookService;

    @PreAuthorize("hasAuthority('USER')")
    @GetMapping
    @Operation(summary = "Get all books",
            description = "Get a list of all available books using sorting and pagination")
    public List<BookDto> getAll(final Pageable pageable) {
        return bookService.findAll(pageable);
    }

    @PreAuthorize("hasAuthority('USER')")
    @GetMapping(value = "/{id}")
    @Operation(summary = "Get a book", description = "Get an available book by id")
    public BookDto getBookById(@PathVariable @Valid @Positive final Long id) {
        return bookService.findById(id);
    }

    @PreAuthorize("hasAuthority('USER')")
    @GetMapping(value = "/search")
    @Operation(summary = "Search for books", description = "Search for available books by params")
    public List<BookDto> searchBooks(@Valid final BookSearchParameters searchParameters) {
        return bookService.search(searchParameters);
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @ResponseStatus(CREATED)
    @PostMapping(consumes = APPLICATION_JSON_VALUE)
    @Operation(summary = "Create a book", description = "Create a new book")
    public BookDto createBook(@RequestBody @Valid final CreateBookRequestDto bookRequestDto) {
        return bookService.save(bookRequestDto);
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @ResponseStatus(NO_CONTENT)
    @PutMapping(value = "/{id}", consumes = APPLICATION_JSON_VALUE)
    @Operation(summary = "Update a book", description = "Update an available book by id")
    public void updateBookById(@PathVariable @Valid @Positive final Long id,
                               @RequestBody @Valid final UpdateBookRequestDto bookRequestDto) {
        bookService.updateById(id, bookRequestDto);
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @ResponseStatus(NO_CONTENT)
    @DeleteMapping(value = "/{id}")
    @Operation(summary = "Delete a book", description = "Delete an available book by id")
    public void deleteBookById(@PathVariable @Valid @Positive final Long id) {
        bookService.deleteById(id);
    }
}
