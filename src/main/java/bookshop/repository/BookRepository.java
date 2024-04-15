package bookshop.repository;

import bookshop.model.Book;
import java.util.List;

public interface BookRepository {
    Book save(final Book book);

    List<Book> findAll();
}
