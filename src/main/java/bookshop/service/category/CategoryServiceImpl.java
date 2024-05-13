package bookshop.service.category;

import bookshop.dto.category.CategoryDto;
import bookshop.dto.category.CreateCategoryRequestDto;
import bookshop.dto.category.UpdateCategoryRequestDto;
import bookshop.exception.EntityNotFoundException;
import bookshop.mapper.CategoryMapper;
import bookshop.model.Category;
import bookshop.repository.category.CategoryRepository;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CategoryServiceImpl implements CategoryService {
    private final CategoryRepository categoryRepository;
    private final CategoryMapper categoryMapper;

    @Override
    public CategoryDto save(final CreateCategoryRequestDto categoryRequestDto) {
        final Category category = categoryMapper.toModel(categoryRequestDto);
        return categoryMapper.toDto(categoryRepository.save(category));
    }

    @Override
    public List<CategoryDto> findAll(final Pageable pageable) {
        return categoryRepository.findAll(pageable).stream()
                .map(categoryMapper::toDto)
                .toList();
    }

    @Override
    public CategoryDto findById(final Long id) {
        return categoryRepository.findById(id)
                .map(categoryMapper::toDto)
                .orElseThrow(() ->
                        new EntityNotFoundException("Couldn't get category from DB by id=" + id));
    }

    @Override
    public void updateById(final Long id, final UpdateCategoryRequestDto categoryRequestDto) {
        final Category category = categoryRepository.findById(id).orElseThrow(() ->
                new EntityNotFoundException("Couldn't get category from DB by id=" + id));
        categoryMapper.updateModelFromDto(category, categoryRequestDto);
        categoryRepository.save(category);
    }

    @Override
    public void deleteById(final Long id) {
        categoryRepository.deleteById(id);
    }
}
