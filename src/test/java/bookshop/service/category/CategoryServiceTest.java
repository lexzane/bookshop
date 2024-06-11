package bookshop.service.category;

import static bookshop.db_util.TestDataGenerator.FIRST_CATEGORY_DESCRIPTION;
import static bookshop.db_util.TestDataGenerator.FIRST_CATEGORY_ID;
import static bookshop.db_util.TestDataGenerator.FIRST_CATEGORY_NAME;
import static bookshop.db_util.TestDataGenerator.buildFirstCategoryDto;
import static bookshop.db_util.TestDataGenerator.buildFirstUpdateCategoryDto;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.AdditionalAnswers.returnsFirstArg;
import static org.mockito.Mockito.doAnswer;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.verifyNoInteractions;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;

import bookshop.dto.category.CategoryDto;
import bookshop.dto.category.CreateCategoryRequestDto;
import bookshop.dto.category.UpdateCategoryRequestDto;
import bookshop.exception.EntityNotFoundException;
import bookshop.mapper.CategoryMapper;
import bookshop.model.Category;
import bookshop.repository.category.CategoryRepository;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;

@ExtendWith(MockitoExtension.class)
class CategoryServiceTest {
    @InjectMocks
    private CategoryServiceImpl categoryService;
    @Mock
    private CategoryRepository categoryRepository;
    @Mock
    private CategoryMapper categoryMapper;

    @Test
    @DisplayName("Create a new category")
    void save_ValidData_ReturnsCategoryDto() {
        final CreateCategoryRequestDto requestDto = new CreateCategoryRequestDto(
                FIRST_CATEGORY_NAME,
                FIRST_CATEGORY_DESCRIPTION
        );
        final Category category = buildFirstCategory();
        final CategoryDto expected = buildFirstCategoryDto();

        when(categoryMapper.toModel(requestDto)).thenReturn(category);
        doAnswer(returnsFirstArg()).when(categoryRepository).save(category);
        when(categoryMapper.toDto(category)).thenReturn(expected);

        final CategoryDto actual = categoryService.save(requestDto);

        assertThat(actual).isEqualTo(expected);
        verifyNoMoreInteractions(categoryMapper, categoryRepository);
    }

    @Test
    @DisplayName("Get all available categories using sorting and pagination")
    void findAll_ValidData_ReturnsCategoryDtoList() {
        final PageRequest pageRequest = PageRequest.of(0, 5);
        final Category category = buildFirstCategory();
        final CategoryDto expected = buildFirstCategoryDto();

        when(categoryRepository.findAll(pageRequest)).thenReturn(new PageImpl<>(List.of(category)));
        when(categoryMapper.toDto(category)).thenReturn(expected);

        final List<CategoryDto> actual = categoryService.findAll(pageRequest);

        assertThat(actual).containsExactly(expected);
        verifyNoMoreInteractions(categoryMapper, categoryRepository);
    }

    @Test
    @DisplayName("Get an available category by id")
    void findById_ValidData_ReturnsCategoryDtoList() {
        final Category category = buildFirstCategory();
        final CategoryDto expected = buildFirstCategoryDto();

        when(categoryRepository.findById(FIRST_CATEGORY_ID)).thenReturn(Optional.of(category));
        when(categoryMapper.toDto(category)).thenReturn(expected);

        final CategoryDto actual = categoryService.findById(FIRST_CATEGORY_ID);

        assertThat(actual).isEqualTo(expected);
        verifyNoMoreInteractions(categoryMapper, categoryRepository);
    }

    @Test
    @DisplayName("Throw exception on find category by invalid id")
    void findById_InvalidData_ThrowsEntityNotFoundException() {
        when(categoryRepository.findById(FIRST_CATEGORY_ID)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> categoryService.findById(FIRST_CATEGORY_ID))
                .isExactlyInstanceOf(EntityNotFoundException.class)
                .hasMessage("Couldn't get category from DB by id=" + FIRST_CATEGORY_ID);

        verifyNoMoreInteractions(categoryRepository);
        verifyNoInteractions(categoryMapper);
    }

    @Test
    @DisplayName("Update an available category by id")
    void updateById_ValidData_NoContent() {
        final Category category = buildFirstCategory();
        final UpdateCategoryRequestDto requestDto = buildFirstUpdateCategoryDto();

        when(categoryRepository.findById(FIRST_CATEGORY_ID)).thenReturn(Optional.of(category));
        doNothing().when(categoryMapper).updateModelFromDto(category, requestDto);
        doAnswer(returnsFirstArg()).when(categoryRepository).save(category);

        categoryService.updateById(FIRST_CATEGORY_ID, requestDto);

        verifyNoMoreInteractions(categoryMapper, categoryRepository);
    }

    @Test
    @DisplayName("Throw exception on update category by invalid id")
    void updateById_InvalidData_ThrowsEntityNotFoundException() {
        when(categoryRepository.findById(FIRST_CATEGORY_ID)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> categoryService
                .updateById(FIRST_CATEGORY_ID, buildFirstUpdateCategoryDto()))
                .isExactlyInstanceOf(EntityNotFoundException.class)
                .hasMessage("Couldn't get category from DB by id=" + FIRST_CATEGORY_ID);

        verifyNoMoreInteractions(categoryRepository);
        verifyNoInteractions(categoryMapper);
    }

    @Test
    @DisplayName("Delete an available category by id")
    void deleteById_ValidData_NoContent() {
        doNothing().when(categoryRepository).deleteById(FIRST_CATEGORY_ID);

        categoryService.deleteById(FIRST_CATEGORY_ID);

        verifyNoMoreInteractions(categoryRepository);
        verifyNoInteractions(categoryMapper);
    }

    private static Category buildFirstCategory() {
        return new Category()
                .setName(FIRST_CATEGORY_NAME)
                .setDescription(FIRST_CATEGORY_DESCRIPTION);
    }
}
