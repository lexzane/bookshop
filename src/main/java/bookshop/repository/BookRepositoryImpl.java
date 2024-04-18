package bookshop.repository;

import bookshop.model.Book;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.EntityTransaction;
import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class BookRepositoryImpl implements BookRepository {
    private final EntityManagerFactory factory;

    @Override
    public Book save(final Book book) {
        EntityTransaction transaction = null;
        try (final var entityManager = factory.createEntityManager()) {
            transaction = entityManager.getTransaction();
            transaction.begin();
            entityManager.persist(book);
            transaction.commit();
            return book;
        } catch (Exception exception) {
            if (transaction != null && transaction.isActive()) {
                transaction.rollback();
            }
            throw new RuntimeException("Couldn't insert book into DB: " + book);
        }
    }

    @Override
    public List<Book> findAll() {
        try (final var entityManager = factory.createEntityManager()) {
            return entityManager.createQuery("FROM Book b", Book.class).getResultList();
        } catch (Exception exception) {
            throw new RuntimeException("Couldn't get all books from DB", exception);
        }
    }

    @Override
    public Optional<Book> findById(final Long id) {
        try (final var entityManager = factory.createEntityManager()) {
            return Optional.ofNullable(entityManager.find(Book.class, id));
        } catch (Exception exception) {
            throw new RuntimeException("Couldn't get book from DB by id: " + id, exception);
        }
    }
}
