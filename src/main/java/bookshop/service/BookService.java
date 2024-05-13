package bookshop.service;

import bookshop.dto.BookDto;
import bookshop.dto.BookSearchParameters;
import bookshop.dto.CreateBookRequestDto;
import bookshop.dto.UpdateBookRequestDto;
import java.util.List;
import org.springframework.data.domain.Pageable;

public interface BookService {
    BookDto save(CreateBookRequestDto bookRequestDto);

    List<BookDto> findAll(Pageable pageable);

    List<BookDto> search(BookSearchParameters searchParameters);

    BookDto findById(Long id);

    void updateById(Long id, UpdateBookRequestDto bookRequestDto);

    void deleteById(Long id);
}
