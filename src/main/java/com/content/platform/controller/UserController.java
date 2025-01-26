package com.content.platform.controller;

import com.content.platform.dto.request.UserRequestDto;
import com.content.platform.dto.response.UserResponseDto;
import com.content.platform.service.UserService;
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

    @Operation(summary = "Create a new user", description = "This endpoint accepts user request dto and returns a user response dto.")
    @ApiResponses(
            {@ApiResponse(responseCode = "200", description = "User created successfully",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = UserResponseDto.class))
            ),
                    @ApiResponse(responseCode = "400", description = "Invalid input data",
                            content = @Content(mediaType = "application/json")
                    ),
                    @ApiResponse(responseCode = "500", description = "Internal Server Error",
                            content = @Content(mediaType = "application/json")
                    )
            })
    @PostMapping("/addUser")
    public ResponseEntity<UserResponseDto> addUser(@Valid @RequestBody UserRequestDto userRequestDto)
    {
        return new ResponseEntity<>(userService.addUser(userRequestDto), HttpStatus.CREATED);
    }


    @Operation(summary = "Fetch all users", description = "This endpoint returns a list of user response dto.")
    @ApiResponses(
            {@ApiResponse(responseCode = "200", description = "All Users fetched successfully",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = UserResponseDto.class))
            ),
                    @ApiResponse(responseCode = "404", description = "User Not Found",
                            content = @Content(mediaType = "application/json")
                    ),
                    @ApiResponse(responseCode = "500", description = "Internal Server Error",
                            content = @Content(mediaType = "application/json")
                    )
            })
    @GetMapping("/getAllUsers")
    public ResponseEntity<List<UserResponseDto>> getAllUsers()
    {
        return new ResponseEntity<>(userService.getAllUsers(), HttpStatus.OK);
    }

    @Operation(summary = "Fetch a user by user id", description = "This endpoint accepts user id and returns a user response dto.")
    @ApiResponses(
            {@ApiResponse(responseCode = "200", description = "User fetched successfully",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = UserResponseDto.class))
            ),
                    @ApiResponse(responseCode = "404", description = "User Not Found",
                            content = @Content(mediaType = "application/json")
                    ),
                    @ApiResponse(responseCode = "500", description = "Internal Server Error",
                            content = @Content(mediaType = "application/json")
                    )
            })
    @GetMapping("/getUserByUserId/{userId}")
    public ResponseEntity<UserResponseDto> getUserByUserId(@PathVariable("userId") Long userId)
    {
        return new ResponseEntity<>(userService.getUserByUserId(userId), HttpStatus.OK);
    }

    @Operation(summary = "Update a user by user id", description = "This endpoint accepts user id, request user dto and returns a updated user response dto.")
    @ApiResponses(
            {@ApiResponse(responseCode = "200", description = "User updated successfully",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = UserResponseDto.class))
            ),
                    @ApiResponse(responseCode = "404", description = "User Not Found",
                            content = @Content(mediaType = "application/json")
                    ),
                    @ApiResponse(responseCode = "500", description = "Internal Server Error",
                            content = @Content(mediaType = "application/json")
                    )
            })
    @PutMapping("/updateUser/{userId}")
    public ResponseEntity<UserResponseDto> updateUserByUserId(@PathVariable("userId") Long userId, @RequestBody UserRequestDto userRequestDto)
    {
        return new ResponseEntity<>(userService.updateUserByUserId(userId, userRequestDto), HttpStatus.OK);
    }


}
