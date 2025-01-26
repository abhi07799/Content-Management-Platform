package com.content.platform.dto.response;

import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CategoryResponseDto
{
    private Long id;

    private String categoryName;

    private String description;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;
}
