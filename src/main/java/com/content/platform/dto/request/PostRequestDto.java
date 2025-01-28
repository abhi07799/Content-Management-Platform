package com.content.platform.dto.request;

import com.content.platform.model.CategoryModel;
import com.content.platform.model.UserModel;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PostRequestDto
{
    @NotBlank(message = "Post Title Can Not Be Null, Blank or Empty")
    @Size(min = 5, max = 50, message = "Post Title must be between 10 and 100 characters")
    private String postTitle;

    @NotBlank(message = "Post Description Can Not Be Null, Blank or Empty")
    @Size(min = 50, max = 200, message = "Post Description must be between 50 and 200 characters")
    private String postDescription;

    @NotBlank(message = "Post Content Can Not Be Null, Blank or Empty")
    @Size(min = 100, message = "Post Content must be at least 100 characters")
    private String postContent;

    @NotBlank(message = "Post Type Can Not Be Null, Blank or Empty")
    @Pattern(regexp = "^(?i)(article|blog|case_study|news|ebook|diy_tutorial)$", message = "Type must be one of these: article, blog, case_study, news, ebook or diy_tutorial")
    private String postType;

    @NotNull(message = "Post Category Can Not Be Null")
    private CategoryModel postCategory;

    @NotNull(message = "Post Author Can Not Be Null")
    private UserModel postAuthor;

}
