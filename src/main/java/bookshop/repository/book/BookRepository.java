package bookshop.repository.book;

import bookshop.model.Book;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Repository;

@Repository
public interface BookRepository extends JpaRepository<Book, Long>, JpaSpecificationExecutor<Book> {
    @Override
    @NonNull
    @EntityGraph(attributePaths = "categories")
    Page<Book> findAll(@NonNull Pageable pageable);

    @Override
    @NonNull
    @EntityGraph(attributePaths = "categories")
    List<Book> findAll(@NonNull Specification<Book> spec);

    @EntityGraph(attributePaths = "categories")
    Optional<Book> findFetchCategoriesById(Long id);

    @Query("FROM Book book "
            + "JOIN book.categories category "
            + "WHERE category.id = :categoryId")
    List<Book> findAllByCategoryId(Long categoryId);
}

