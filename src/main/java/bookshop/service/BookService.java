package bookshop.service;

import bookshop.dto.BookDto;
import bookshop.dto.BookSearchParameters;
import bookshop.dto.CreateBookRequestDto;
import bookshop.dto.UpdateBookRequestDto;
import java.util.List;

public interface BookService {
    BookDto save(CreateBookRequestDto bookRequestDto);

    List<BookDto> findAll();

    List<BookDto> search(BookSearchParameters searchParameters);

    BookDto findById(Long id);

    void updateById(Long id, UpdateBookRequestDto bookRequestDto);

    void deleteById(Long id);
}
