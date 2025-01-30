package com.content.platform.controller;

import com.content.platform.dto.ErrorDto;
import com.content.platform.dto.request.PostRequestDto;
import com.content.platform.dto.response.PostResponseDto;
import com.content.platform.service.PostService;
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
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "Post", description = "Post management APIs")
@RestController
@RequestMapping("/v1/posts")
public class PostController
{
    private final PostService postService;

    @Autowired
    public PostController(PostService postService)
    {
        this.postService = postService;
    }

    @Operation(summary = "Create a new post", description = "This endpoint accepts post request dto and returns a post response dto.")
    @ApiResponses(
            {@ApiResponse(responseCode = "200", description = "Post created successfully",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = PostResponseDto.class))
            ),
                    @ApiResponse(responseCode = "400", description = "Invalid input data",
                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorDto.class))
                    ),
                    @ApiResponse(responseCode = "404", description = "Resource Not Found",
                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorDto.class))
                    ),
                    @ApiResponse(responseCode = "409", description = "Post Already Exist",
                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorDto.class))
                    ),
                    @ApiResponse(responseCode = "500", description = "Internal Server Error",
                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorDto.class))
                    )
            })
    @PreAuthorize("hasAuthority('AUTHOR')")
    @PostMapping("/addPost")
    public ResponseEntity<PostResponseDto> addPost(@Valid @RequestBody PostRequestDto postRequestDto)
    {
        return new ResponseEntity<>(postService.addPost(postRequestDto), HttpStatus.CREATED);
    }


    @Operation(summary = "Fetch all posts", description = "This endpoint returns a list of post response dto.")
    @ApiResponses(
            {@ApiResponse(responseCode = "200", description = "All Posts fetched successfully",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = PostResponseDto.class))
            ),
                    @ApiResponse(responseCode = "404", description = "Post Not Found",
                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorDto.class))
                    ),
                    @ApiResponse(responseCode = "500", description = "Internal Server Error",
                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorDto.class))
                    )
            })
    @GetMapping("/getAllPosts")
    public ResponseEntity<List<PostResponseDto>> getAllPosts()
    {
        return new ResponseEntity<>(postService.getAllPosts(), HttpStatus.OK);
    }


    @Operation(summary = "Fetch a post by post id", description = "This endpoint accepts post id and returns a post response dto.")
    @ApiResponses(
            {@ApiResponse(responseCode = "200", description = "Post fetched successfully",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = PostResponseDto.class))
            ),
                    @ApiResponse(responseCode = "404", description = "Post Not Found",
                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorDto.class))
                    ),
                    @ApiResponse(responseCode = "500", description = "Internal Server Error",
                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorDto.class))
                    )
            })
    @GetMapping("/getPostById/{postId}")
    public ResponseEntity<PostResponseDto> getPostById(@PathVariable Long postId)
    {
        return new ResponseEntity<>(postService.getPostById(postId), HttpStatus.OK);
    }


    @Operation(summary = "Fetch a post by post title", description = "This endpoint accepts post title and returns a post response dto.")
    @ApiResponses(
            {@ApiResponse(responseCode = "200", description = "Post fetched successfully",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = PostResponseDto.class))
            ),
                    @ApiResponse(responseCode = "404", description = "Post Not Found",
                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorDto.class))
                    ),
                    @ApiResponse(responseCode = "500", description = "Internal Server Error",
                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorDto.class))
                    )
            })
    @GetMapping("/getPostByTitle")
    public ResponseEntity<PostResponseDto> getPostByTitle(@RequestParam String postTitle)
    {
        return new ResponseEntity<>(postService.getPostByTitle(postTitle), HttpStatus.OK);
    }

    @Operation(summary = "Fetch all posts by post category", description = "This endpoint accepts category name and returns a list of post response dto.")
    @ApiResponses(
            {@ApiResponse(responseCode = "200", description = "Posts fetched successfully",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = PostResponseDto.class))
            ),
                    @ApiResponse(responseCode = "404", description = "Post Not Found",
                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorDto.class))
                    ),
                    @ApiResponse(responseCode = "500", description = "Internal Server Error",
                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorDto.class))
                    )
            })
    @GetMapping("/getPostsByCategory")
    public ResponseEntity<List<PostResponseDto>> getPostsByCategory(@RequestParam String categoryName)
    {
        return new ResponseEntity<>(postService.getPostsByCategory(categoryName), HttpStatus.OK);
    }

    @Operation(summary = "Update a post by post id", description = "This endpoint accepts post id, request post dto and returns a updated post response dto.")
    @ApiResponses(
            {@ApiResponse(responseCode = "200", description = "Post updated successfully",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = PostResponseDto.class))
            ),
                    @ApiResponse(responseCode = "404", description = "Post Not Found",
                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorDto.class))
                    ),
                    @ApiResponse(responseCode = "409", description = "Post Already Exist",
                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorDto.class))
                    ),
                    @ApiResponse(responseCode = "500", description = "Internal Server Error",
                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorDto.class))
                    )
            })
    @PutMapping("/updatePost/{postId}")
    public ResponseEntity<PostResponseDto> updatePost(@PathVariable Long postId, @RequestBody PostRequestDto postRequestDto)
    {
        return new ResponseEntity<>(postService.updatePostById(postId, postRequestDto), HttpStatus.OK);
    }
    
}
