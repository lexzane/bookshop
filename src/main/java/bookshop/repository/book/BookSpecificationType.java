package bookshop.repository.book;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum BookSpecificationType {
    AUTHOR("author"),
    TITLE("title"),
    PRICE("price");

    private final String key;
}
