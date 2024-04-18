package bookshop.controller;

import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.HttpStatus.NO_CONTENT;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

import bookshop.dto.BookDto;
import bookshop.dto.BookSearchParameters;
import bookshop.dto.CreateBookRequestDto;
import bookshop.dto.UpdateBookRequestDto;
import bookshop.service.BookService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/books")
@RequiredArgsConstructor
public class BookController {
    private final BookService bookService;

    @GetMapping(produces = APPLICATION_JSON_VALUE)
    public List<BookDto> getAll() {
        return bookService.findAll();
    }

    @GetMapping(value = "/{id}", produces = APPLICATION_JSON_VALUE)
    public BookDto getBookById(@PathVariable final Long id) {
        return bookService.findById(id);
    }

    @GetMapping(value = "/search", produces = APPLICATION_JSON_VALUE)
    public List<BookDto> searchBooks(final BookSearchParameters searchParameters) {
        return bookService.search(searchParameters);
    }

    @ResponseStatus(CREATED)
    @PostMapping(consumes = APPLICATION_JSON_VALUE, produces = APPLICATION_JSON_VALUE)
    public BookDto createBook(@RequestBody final CreateBookRequestDto bookRequestDto) {
        return bookService.save(bookRequestDto);
    }

    @ResponseStatus(NO_CONTENT)
    @PutMapping(value = "/{id}", consumes = APPLICATION_JSON_VALUE)
    public void updateBookById(@PathVariable final Long id,
                               @RequestBody final UpdateBookRequestDto bookRequestDto) {
        bookService.updateById(id, bookRequestDto);
    }

    @ResponseStatus(NO_CONTENT)
    @DeleteMapping(value = "/{id}")
    public void deleteBookById(@PathVariable final Long id) {
        bookService.deleteById(id);
    }
}
