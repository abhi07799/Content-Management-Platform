package com.content.platform.dto.response;


import com.content.platform.model.CategoryModel;
import com.content.platform.model.PostType;
import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Schema(description = "Post Response DTO")
@JsonInclude(JsonInclude.Include.NON_NULL)
public class PostResponseDto
{
    private Long id;
    private String postTitle;
    private String postDescription;
    private String postContent;
    private PostType postType;
    private CategoryModel postCategory;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

}