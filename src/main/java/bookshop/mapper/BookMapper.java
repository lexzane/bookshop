package bookshop.mapper;

import bookshop.dto.BookDto;
import bookshop.dto.CreateBookRequestDto;
import bookshop.dto.UpdateBookRequestDto;
import bookshop.model.Book;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface BookMapper {
    BookDto toDto(Book book);

    Book toModel(CreateBookRequestDto requestDto);

    void updateModelFromDto(@MappingTarget Book book, UpdateBookRequestDto requestDto);
}
