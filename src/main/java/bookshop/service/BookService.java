package bookshop.service;

import bookshop.model.Book;
import java.util.List;

public interface BookService {
    Book save(final Book book);

    List<Book> findAll();
}
