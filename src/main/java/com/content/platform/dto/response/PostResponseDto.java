package com.content.platform.dto.response;


import com.content.platform.model.CategoryModel;
import com.content.platform.model.PostType;
import com.content.platform.model.UserModel;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Schema(description = "Post Response DTO")
public class PostResponseDto
{
    private Long id;
    private String postTitle;
    private String postDescription;
    private String postContent;
    private PostType postType;
    private CategoryModel postCategory;
    private UserModel postAuthor;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

}