package bookshop.mapper;

import bookshop.dto.category.CategoryDto;
import bookshop.dto.category.CreateCategoryRequestDto;
import bookshop.dto.category.UpdateCategoryRequestDto;
import bookshop.model.Category;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface CategoryMapper {
    CategoryDto toDto(Category category);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "deleted", ignore = true)
    Category toModel(CreateCategoryRequestDto requestDto);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "deleted", ignore = true)
    void updateModelFromDto(@MappingTarget Category category, UpdateCategoryRequestDto requestDto);
}
