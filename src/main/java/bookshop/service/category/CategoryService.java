package bookshop.service.category;

import bookshop.dto.category.CategoryDto;
import bookshop.dto.category.CreateCategoryRequestDto;
import bookshop.dto.category.UpdateCategoryRequestDto;
import java.util.List;
import org.springframework.data.domain.Pageable;

public interface CategoryService {
    CategoryDto save(CreateCategoryRequestDto categoryRequestDto);

    List<CategoryDto> findAll(Pageable pageable);

    CategoryDto findById(Long id);

    void updateById(Long id, UpdateCategoryRequestDto categoryRequestDto);

    void deleteById(Long id);
}
