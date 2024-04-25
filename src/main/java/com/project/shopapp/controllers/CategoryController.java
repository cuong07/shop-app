package com.project.shopapp.controllers;

import com.project.shopapp.components.LocalizationUtils;
import com.project.shopapp.dtos.CategoryDTO;
import com.project.shopapp.models.category.Category;
import com.project.shopapp.responses.CategoryResponse;
import com.project.shopapp.responses.UpdateCategoryResponse;
import com.project.shopapp.services.category.CategoryService;
import com.project.shopapp.utils.MessageKeys;
import com.project.shopapp.components.StoreFile;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("${api.prefix}/categories")
//@Validated
@AllArgsConstructor
public class CategoryController {
    private final CategoryService categoryService;
    private final LocalizationUtils localizationUtils;
    private final StoreFile storeFile;

//    public CategoryController(CategoryService categoryService, LocalizationUtils localizationUtils, StoreFile storeFile) {
//        this.categoryService = categoryService;
//        this.localizationUtils = localizationUtils;
//    }


    @PostMapping(value = "", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<?> createCategory(
            @RequestParam("file") MultipartFile file,
            @Valid @ModelAttribute CategoryDTO categoryDTO,
            BindingResult result
    ) {
        try {
            CategoryResponse categoryResponse = new CategoryResponse();
            if (result.hasErrors()) {
                List<String> errorMessages = result.getFieldErrors()
                        .stream()
                        .map(FieldError::getDefaultMessage)
                        .toList();
                categoryResponse.setMessage(localizationUtils.getLocalizedMessage(MessageKeys.CATEGORY_CREATE_FAILED));
                categoryResponse.setErrors(errorMessages);
                return ResponseEntity.badRequest().body(categoryResponse);
            }

            if (file.getSize() > 10 * 1024 * 1024) { // size > 10 mb
                return ResponseEntity.status(HttpStatus.PAYLOAD_TOO_LARGE).body("File is too large! Maximum size is 10MB");
            }
            String contentType = file.getContentType();
            if (contentType == null || !contentType.startsWith("image/")) {
                return ResponseEntity.status(HttpStatus.UNSUPPORTED_MEDIA_TYPE)
                        .body("File must be an image");
            }

            if (!file.isEmpty()) {
                String fileName = storeFile.saveImage(file, "uploads/category");
                categoryDTO.setThumbnail(fileName);
            }

            Category category = categoryService.createCategory(categoryDTO);
            categoryResponse.setMessage(localizationUtils.getLocalizedMessage(MessageKeys.CATEGORY_CREATE_SUCCESSFULLY));
            categoryResponse.setCategory(category);
            return ResponseEntity.ok(categoryResponse);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("An error occurred while processing the request");

        }
    }


    @GetMapping("")
    public ResponseEntity<?> getAllCategories(
            @RequestParam(name = "page", defaultValue = "0") int page,
            @RequestParam(name = "limit", defaultValue = "0") int limit
    ) {
        try {
            PageRequest pageRequest = PageRequest.of(
                    page,
                    limit,
                    Sort.by("id").ascending());
            Page<Category> categories = categoryService.getAllCategories(pageRequest);
            return ResponseEntity.ok(categories);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.ok(e);
        }
    }


    @PutMapping(value = "/{id}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<?> updateCategory(
            @PathVariable Long id,
            @Valid @RequestBody CategoryDTO categoryDTO,
            @ModelAttribute("file") MultipartFile file
    ) {
        try {
            if (file.getSize() > 10 * 1024 * 1024) { // size > 10 mb
                return ResponseEntity.status(HttpStatus.PAYLOAD_TOO_LARGE).body("File is too large! Maximum size is 10MB");
            }
            String contentType = file.getContentType();
            if (contentType == null || !contentType.startsWith("image/")) {
                return ResponseEntity.status(HttpStatus.UNSUPPORTED_MEDIA_TYPE)
                        .body("File must be an image");
            }

            String fileName = storeFile.saveImage(file, "uploads/category");
            if (!file.isEmpty()) {
                categoryDTO.setThumbnail(fileName);
            }
            categoryService.updateCategory(id, categoryDTO);
            return ResponseEntity.ok(UpdateCategoryResponse.builder()
                    .message(localizationUtils
                            .getLocalizedMessage(MessageKeys.CATEGORY_UPDATE_SUCCESSFULLY))
                    .build());
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }


    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<UpdateCategoryResponse> deleteCategory(
            @PathVariable Long id
    ) {
        categoryService.deleteCategory(id);
        return ResponseEntity.ok(UpdateCategoryResponse.builder()
                .message(localizationUtils
                        .getLocalizedMessage(MessageKeys.CATEGORY_DELETE_SUCCESSFULLY))
                .build());
    }
}
