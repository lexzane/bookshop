package bookshop.dto.book;

import jakarta.validation.constraints.Size;

public record BookSearchParameters(
        @Size(min = 1) String[] titles,
        @Size(min = 1) String[] authors,
        @Size(min = 1) String[] prices
) {
}
