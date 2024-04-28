package bookshop.service.book;

import bookshop.dto.book.BookDto;
import bookshop.dto.book.BookDtoWithoutCategoryIds;
import bookshop.dto.book.BookSearchParameters;
import bookshop.dto.book.CreateBookRequestDto;
import bookshop.dto.book.UpdateBookRequestDto;
import java.util.List;
import org.springframework.data.domain.Pageable;

public interface BookService {
    BookDto save(CreateBookRequestDto bookRequestDto);

    List<BookDto> findAll(Pageable pageable);

    List<BookDto> search(BookSearchParameters searchParameters);

    BookDto findById(Long id);

    void updateById(Long id, UpdateBookRequestDto bookRequestDto);

    void deleteById(Long id);

    List<BookDtoWithoutCategoryIds> findAllByCategoryId(Long categoryId);
}
