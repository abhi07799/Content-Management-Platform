package com.content.platform.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CategoryRequestDto
{

    @NotBlank(message = "Category name can not be null")
    private String categoryName;

    @NotBlank(message = "Category description can not be null")
    private String description;
}
