package bookshop.repository.book.spec;

import static bookshop.repository.book.BookSpecificationType.PRICE;

import bookshop.model.Book;
import bookshop.repository.SpecificationProvider;
import java.util.Arrays;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;

@Component
class PriceSpecificationProvider implements SpecificationProvider<Book> {
    @Override
    public String getKey() {
        return PRICE.getKey();
    }

    @Override
    public Specification<Book> getSpecification(final String... params) {
        return (root, query, criteriaBuilder) ->
                root.get(getKey()).in(Arrays.stream(params).toArray());
    }
}
