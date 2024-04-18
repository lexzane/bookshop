package bookshop.mapper;

import bookshop.dto.BookDto;
import bookshop.dto.CreateBookRequestDto;
import bookshop.model.Book;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface BookMapper {
    BookDto toDto(Book book);

    Book toModel(CreateBookRequestDto requestDto);
}
