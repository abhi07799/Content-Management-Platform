package com.content.platform.dto.response;

import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserResponseDto
{
    private Long id;

    private String userFullName;

    private String userMail;

    private String userPassword;

    private String role;

    private LocalDateTime createdDate;

    private LocalDateTime updatedDate;
}
