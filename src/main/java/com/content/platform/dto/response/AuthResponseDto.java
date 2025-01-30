package com.content.platform.dto.response;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AuthResponseDto
{
    private String authToken;
    private String refreshToken;
    private String message;
}
