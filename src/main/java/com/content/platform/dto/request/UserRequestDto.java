package com.content.platform.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserRequestDto
{
    @NotBlank(message = "User Full Name can not be null, blank or empty")
    private String userFullName;

    @NotBlank(message = "User Mail can not be null, blank or empty")
    private String userMail;

    @NotBlank(message = "User Password can not be null, blank or empty")
    private String userPassword;

    @NotBlank(message = "User Role can not be null, blank or empty")
    @Pattern(regexp = "^(?i)(user|admin|author)$", message = "Role must be one of: user, admin, or author")
    private String role;

}
