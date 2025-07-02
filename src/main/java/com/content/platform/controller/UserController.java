package com.content.platform.controller;

import com.content.platform.dto.ErrorDto;
import com.content.platform.dto.request.UserRequestDto;
import com.content.platform.dto.response.UserResponseDto;
import com.content.platform.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Tag(name = "User", description = "User management APIs")
@RestController
@RequestMapping("/v1/user")
public class UserController
{
    private final UserService userService;

    @Autowired
    public UserController(UserService userService)
    {
        this.userService = userService;
    }

    @Operation(summary = "Fetch user profile", description = "This endpoint returns a user profile response dto.")
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
    @GetMapping("/getUserProfile")
    public ResponseEntity<UserResponseDto> getUserProfile()
    {
        return new ResponseEntity<>(userService.getUserProfile(), HttpStatus.OK);
    }

    @Operation(summary = "Update a user by user id", description = "This endpoint accepts user id, request user dto and returns a updated user response dto.")
    @ApiResponses(
            {@ApiResponse(responseCode = "200", description = "User updated successfully",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = UserResponseDto.class))
            ),
                    @ApiResponse(responseCode = "404", description = "User Not Found",
                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorDto.class))
                    ),
                    @ApiResponse(responseCode = "500", description = "Internal Server Error",
                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorDto.class))
                    )
            })
    @PutMapping("/updateUser/{userId}")
    public ResponseEntity<UserResponseDto> updateUserByUserId(@PathVariable("userId") Long userId, @RequestBody UserRequestDto userRequestDto)
    {
        return new ResponseEntity<>(userService.updateUserByUserId(userId, userRequestDto), HttpStatus.OK);
    }


}
