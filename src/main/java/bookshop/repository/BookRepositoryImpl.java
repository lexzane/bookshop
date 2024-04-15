package bookshop.repository;

import bookshop.model.Book;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class BookRepositoryImpl implements BookRepository {
    private final SessionFactory factory;

    @Override
    public Book save(final Book book) {
        Transaction transaction = null;
        try (final var session = factory.openSession()) {
            transaction = session.beginTransaction();
            session.persist(book);
            transaction.commit();
            return book;
        } catch (Exception exception) {
            if (transaction != null) {
                transaction.rollback();
            }
            throw new RuntimeException("Couldn't insert book into DB: " + book);
        }
    }

    @Override
    public List<Book> findAll() {
        try (final var session = factory.openSession()) {
            return session.createQuery("FROM Book b", Book.class).getResultList();
        } catch (Exception exception) {
            throw new RuntimeException("Couldn't get all books from DB", exception);
        }
    }
}
