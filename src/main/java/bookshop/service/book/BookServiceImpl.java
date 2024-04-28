package bookshop.service.book;

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
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class BookServiceImpl implements BookService {
    private final BookRepository bookRepository;
    private final BookSpecificationBuilder bookSpecificationBuilder;
    private final BookMapper bookMapper;

    @Override
    public BookDto save(final CreateBookRequestDto bookRequestDto) {
        final Book book = bookMapper.toModel(bookRequestDto);
        return bookMapper.toDto(bookRepository.save(book));
    }

    @Override
    public List<BookDto> findAll(final Pageable pageable) {
        return bookRepository.findAll(pageable).stream()
                .map(bookMapper::toDto)
                .toList();
    }

    @Override
    public List<BookDto> search(final BookSearchParameters searchParameters) {
        final Specification<Book> specification = bookSpecificationBuilder.build(searchParameters);
        return bookRepository.findAll(specification).stream()
                .map(bookMapper::toDto)
                .toList();
    }

    @Override
    public BookDto findById(final Long id) {
        return bookRepository.findFetchCategoriesById(id)
                .map(bookMapper::toDto)
                .orElseThrow(() ->
                        new EntityNotFoundException("Couldn't get book from DB by id=" + id));
    }

    @Override
    public void updateById(final Long id, final UpdateBookRequestDto bookRequestDto) {
        final Book book = bookRepository.findById(id).orElseThrow(() ->
                new EntityNotFoundException("Couldn't get book from DB by id=" + id));
        bookMapper.updateModelFromDto(book, bookRequestDto);
        bookRepository.save(book);
    }

    @Override
    public void deleteById(final Long id) {
        bookRepository.deleteById(id);
    }

    @Override
    public List<BookDtoWithoutCategoryIds> findAllByCategoryId(final Long categoryId) {
        return bookRepository.findAllByCategoryId(categoryId).stream()
                .map(bookMapper::toDtoWithoutCategories)
                .toList();
    }
}
