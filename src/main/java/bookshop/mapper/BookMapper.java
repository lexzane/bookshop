package bookshop.mapper;

import bookshop.dto.book.BookDto;
import bookshop.dto.book.CreateBookRequestDto;
import bookshop.dto.book.UpdateBookRequestDto;
import bookshop.model.Book;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface BookMapper {
    BookDto toDto(Book book);

    Book toModel(CreateBookRequestDto requestDto);

    void updateModelFromDto(@MappingTarget Book book, UpdateBookRequestDto requestDto);
}
