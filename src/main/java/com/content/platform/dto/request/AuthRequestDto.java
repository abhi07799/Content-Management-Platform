package com.content.platform.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AuthRequestDto
{
    @NotBlank(message = "User Mail can not be null, blank or empty")
    private String userMail;
    @NotBlank(message = "User Mail can not be null, blank or empty")
    private String userPassword;
}
