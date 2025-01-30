package com.content.platform.controller;

import com.content.platform.dto.ErrorDto;
import com.content.platform.dto.request.CategoryRequestDto;
import com.content.platform.dto.response.CategoryResponseDto;
import com.content.platform.dto.response.UserResponseDto;
import com.content.platform.service.AdminService;
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

@Tag(name = "Admin", description = "Administration Usage APIs")
@RestController
@RequestMapping("/v1/admin")
public class AdminController
{

    private final AdminService adminService;

    @Autowired
    public AdminController(AdminService adminService)
    {
        this.adminService = adminService;
    }

    @Operation(summary = "Fetch all users", description = "This endpoint returns a list of user response dto.")
    @ApiResponses(
            {@ApiResponse(responseCode = "200", description = "All Users fetched successfully",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = UserResponseDto.class))
            ),
                    @ApiResponse(responseCode = "404", description = "User Not Found",
                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorDto.class))
                    ),
                    @ApiResponse(responseCode = "500", description = "Internal Server Error",
                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorDto.class))
                    )
            })
    @GetMapping("/getAllUsers")
    public ResponseEntity<List<UserResponseDto>> getAllUsers()
    {
        return new ResponseEntity<>(adminService.getAllUsers(), HttpStatus.OK);
    }

    @Operation(summary = "Fetch a user by user id", description = "This endpoint accepts user id and returns a user response dto.")
    @ApiResponses(
            {@ApiResponse(responseCode = "200", description = "User fetched successfully",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = UserResponseDto.class))
            ),
                    @ApiResponse(responseCode = "404", description = "User Not Found",
                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorDto.class))
                    ),
                    @ApiResponse(responseCode = "500", description = "Internal Server Error",
                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorDto.class))
                    )
            })
    @GetMapping("/getUserByUserId/{userId}")
    public ResponseEntity<UserResponseDto> getUserByUserId(@PathVariable("userId") Long userId)
    {
        return new ResponseEntity<>(adminService.getUserByUserId(userId), HttpStatus.OK);
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
        return new ResponseEntity<>(adminService.addCategory(categoryRequestDto), HttpStatus.CREATED);
    }
}
