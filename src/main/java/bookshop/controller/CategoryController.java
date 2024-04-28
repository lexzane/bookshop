package bookshop.controller;

import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.HttpStatus.NO_CONTENT;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

import bookshop.dto.book.BookDtoWithoutCategoryIds;
import bookshop.dto.category.CategoryDto;
import bookshop.dto.category.CreateCategoryRequestDto;
import bookshop.dto.category.UpdateCategoryRequestDto;
import bookshop.service.book.BookService;
import bookshop.service.category.CategoryService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Positive;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@Validated
@RestController
@RequestMapping("/api/categories")
@RequiredArgsConstructor
@Tag(name = "Category management", description = "Endpoints for managing categories")
public class CategoryController {
    private final CategoryService categoryService;
    private final BookService bookService;

    @PreAuthorize("hasAuthority('USER')")
    @GetMapping
    @Operation(summary = "Get all categories",
            description = "Get a list of all available categories using sorting and pagination")
    public List<CategoryDto> getAll(final Pageable pageable) {
        return categoryService.findAll(pageable);
    }

    @PreAuthorize("hasAuthority('USER')")
    @GetMapping("/{id}")
    @Operation(summary = "Get a category", description = "Get an available category by id")
    public CategoryDto getCategoryById(@PathVariable @Valid @Positive final Long id) {
        return categoryService.findById(id);
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @ResponseStatus(CREATED)
    @PostMapping(consumes = APPLICATION_JSON_VALUE)
    @Operation(summary = "Create a category", description = "Create a new category")
    public CategoryDto createCategory(
            @RequestBody @Valid final CreateCategoryRequestDto categoryRequestDto
    ) {
        return categoryService.save(categoryRequestDto);
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @ResponseStatus(NO_CONTENT)
    @PutMapping(value = "/{id}", consumes = APPLICATION_JSON_VALUE)
    @Operation(summary = "Update a category", description = "Update an available category by id")
    public void updateCategoryById(
            @PathVariable @Valid @Positive final Long id,
            @RequestBody @Valid final UpdateCategoryRequestDto categoryRequestDto
    ) {
        categoryService.updateById(id, categoryRequestDto);
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @ResponseStatus(NO_CONTENT)
    @DeleteMapping("/{id}")
    @Operation(summary = "Delete a category", description = "Delete an available category by id")
    public void deleteCategoryById(@PathVariable @Valid @Positive final Long id) {
        categoryService.deleteById(id);
    }

    @PreAuthorize("hasAuthority('USER')")
    @GetMapping("/{id}/books")
    @Operation(summary = "Get books by category id",
            description = "Get available books by category id")
    public List<BookDtoWithoutCategoryIds> getBooksByCategoryId(
            @PathVariable @Valid @Positive final Long id
    ) {
        return bookService.findAllByCategoryId(id);
    }
}
