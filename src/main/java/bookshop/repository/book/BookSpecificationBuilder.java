package bookshop.repository.book;

import static bookshop.repository.book.BookSpecificationType.AUTHOR;
import static bookshop.repository.book.BookSpecificationType.PRICE;
import static bookshop.repository.book.BookSpecificationType.TITLE;

import bookshop.dto.book.BookSearchParameters;
import bookshop.model.Book;
import bookshop.repository.SpecificationBuilder;
import bookshop.repository.SpecificationProviderManager;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class BookSpecificationBuilder implements SpecificationBuilder<Book> {
    private final SpecificationProviderManager<Book> bookSpecificationProviderManager;

    @Override
    public Specification<Book> build(final BookSearchParameters searchParameters) {
        Specification<Book> specification = Specification.where(null);
        if (searchParameters.titles() != null && searchParameters.titles().length > 0) {
            specification = specification
                    .and(bookSpecificationProviderManager.getSpecificationProvider(TITLE.getKey())
                    .getSpecification(searchParameters.titles()));
        }
        if (searchParameters.authors() != null && searchParameters.authors().length > 0) {
            specification = specification
                    .and(bookSpecificationProviderManager.getSpecificationProvider(AUTHOR.getKey())
                    .getSpecification(searchParameters.authors()));
        }
        if (searchParameters.prices() != null && searchParameters.prices().length > 0) {
            specification = specification
                    .and(bookSpecificationProviderManager.getSpecificationProvider(PRICE.getKey())
                            .getSpecification(searchParameters.prices()));
        }
        return specification;
    }
}
