package bookshop.dto;

public record BookSearchParameters(
        String[] titles,
        String[] authors,
        String[] prices
) {
}
