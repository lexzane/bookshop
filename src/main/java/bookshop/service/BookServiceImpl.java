package bookshop.service;

import bookshop.dto.BookDto;
import bookshop.dto.BookSearchParameters;
import bookshop.dto.CreateBookRequestDto;
import bookshop.dto.UpdateBookRequestDto;
import bookshop.exception.EntityNotFoundException;
import bookshop.mapper.BookMapper;
import bookshop.model.Book;
import bookshop.repository.book.BookRepository;
import bookshop.repository.book.BookSpecificationBuilder;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
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
    public List<BookDto> findAll() {
        return bookRepository.findAll().stream()
                .map(bookMapper::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public List<BookDto> search(final BookSearchParameters searchParameters) {
        final Specification<Book> specification = bookSpecificationBuilder.build(searchParameters);
        return bookRepository.findAll(specification).stream()
                .map(bookMapper::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public BookDto findById(final Long id) {
        return bookRepository.findById(id)
                .map(bookMapper::toDto)
                .orElseThrow(() ->
                        new EntityNotFoundException("Couldn't get book from DB by id: " + id));
    }

    @Override
    public void updateById(final Long id, final UpdateBookRequestDto bookRequestDto) {
        final Book book = bookRepository.findById(id).orElseThrow(() ->
                new EntityNotFoundException("Couldn't get book from DB by id: " + id));
        bookMapper.updateModelFromDto(book, bookRequestDto);
        bookRepository.save(book);
    }

    @Override
    public void deleteById(final Long id) {
        bookRepository.deleteById(id);
    }
}
