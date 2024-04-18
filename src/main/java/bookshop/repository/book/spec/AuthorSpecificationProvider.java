package bookshop.repository.book.spec;

import static bookshop.repository.book.BookSpecificationType.AUTHOR;

import bookshop.model.Book;
import bookshop.repository.SpecificationProvider;
import java.util.Arrays;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;

@Component
class AuthorSpecificationProvider implements SpecificationProvider<Book> {
    @Override
    public String getKey() {
        return AUTHOR.getKey();
    }

    @Override
    public Specification<Book> getSpecification(final String... params) {
        return (root, query, criteriaBuilder) ->
                root.get(getKey()).in(Arrays.stream(params).toArray());
    }
}
