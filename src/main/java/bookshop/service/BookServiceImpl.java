package bookshop.service;

import bookshop.dto.BookDto;
import bookshop.dto.CreateBookRequestDto;
import bookshop.exception.EntityNotFoundException;
import bookshop.mapper.BookMapper;
import bookshop.model.Book;
import bookshop.repository.BookRepository;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class BookServiceImpl implements BookService {
    private final BookRepository bookRepository;
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
    public BookDto findById(final Long id) {
        return bookRepository.findById(id)
                .map(bookMapper::toDto)
                .orElseThrow(() ->
                        new EntityNotFoundException("Couldn't get book from DB by id: " + id));
    }
}
