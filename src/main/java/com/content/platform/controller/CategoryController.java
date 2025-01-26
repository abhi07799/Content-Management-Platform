package com.content.platform.controller;

import com.content.platform.dto.request.CategoryRequestDto;
import com.content.platform.dto.response.CategoryResponseDto;
import com.content.platform.service.CategoryService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "Category", description = "Category management APIs")
@RestController
@RequestMapping("/v1/category")
public class CategoryController
{
    private final CategoryService categoryService;

    @Autowired
    public CategoryController(CategoryService categoryService)
    {
        this.categoryService = categoryService;
    }

    @Operation(summary = "Create a new category", description = "This endpoint accepts category request dto and returns a category response dto.")
    @ApiResponses(
            {@ApiResponse(responseCode = "200", description = "Category created successfully",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = CategoryResponseDto.class))
            ),
                    @ApiResponse(responseCode = "400", description = "Invalid input data",
                            content = @Content(mediaType = "application/json")
                    ),
                    @ApiResponse(responseCode = "500", description = "Internal Server Error",
                            content = @Content(mediaType = "application/json")
                    )
            })
    @PostMapping("/addCategory")
    public ResponseEntity<CategoryResponseDto> addCategory(@Valid @RequestBody CategoryRequestDto categoryRequestDto)
    {
        return new ResponseEntity<>(categoryService.addCategory(categoryRequestDto), HttpStatus.CREATED);
    }


    @Operation(summary = "Fetch all categories", description = "This endpoint returns a list of category response dto.")
    @ApiResponses(
            {@ApiResponse(responseCode = "200", description = "All Categories fetched successfully",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = CategoryResponseDto.class))
            ),
                    @ApiResponse(responseCode = "404", description = "Categories Not Found",
                            content = @Content(mediaType = "application/json")
                    ),
                    @ApiResponse(responseCode = "500", description = "Internal Server Error",
                            content = @Content(mediaType = "application/json")
                    )
            })
    @GetMapping("/getAllCategories")
    public ResponseEntity<List<CategoryResponseDto>> getAllCategories()
    {
        return new ResponseEntity<>(categoryService.getAllCategories(), HttpStatus.OK);
    }

    @Operation(summary = "Fetch a category by category id", description = "This endpoint accepts category id and returns a category response dto.")
    @ApiResponses(
            {@ApiResponse(responseCode = "200", description = "Category fetched successfully",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = CategoryResponseDto.class))
            ),
                    @ApiResponse(responseCode = "404", description = "Category Not Found",
                            content = @Content(mediaType = "application/json")
                    ),
                    @ApiResponse(responseCode = "500", description = "Internal Server Error",
                            content = @Content(mediaType = "application/json")
                    )
            })
    @GetMapping("/getCategoryById/{categoryId}")
    public ResponseEntity<CategoryResponseDto> getCategoryByCategoryId(@PathVariable Long categoryId)
    {
        return new ResponseEntity<>(categoryService.getCategoryById(categoryId), HttpStatus.OK);
    }

    @Operation(summary = "Fetch a category by category id", description = "This endpoint accepts category id and returns a category response dto.")
    @ApiResponses(
            {@ApiResponse(responseCode = "200", description = "Category fetched successfully",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = CategoryResponseDto.class))
            ),
                    @ApiResponse(responseCode = "404", description = "Category Not Found",
                            content = @Content(mediaType = "application/json")
                    ),
                    @ApiResponse(responseCode = "500", description = "Internal Server Error",
                            content = @Content(mediaType = "application/json")
                    )
            })
    @GetMapping("/getCategoryByName")
    public ResponseEntity<CategoryResponseDto> getCategoryByName(@RequestParam("categoryName") String categoryName)
    {
        return new ResponseEntity<>(categoryService.getCategoryByName(categoryName), HttpStatus.OK);
    }



}
