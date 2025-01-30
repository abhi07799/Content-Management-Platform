package com.content.platform.controller;

import com.content.platform.dto.request.AuthRequestDto;
import com.content.platform.dto.request.UserRequestDto;
import com.content.platform.dto.response.AuthResponseDto;
import com.content.platform.service.AuthenticationService;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Tag(name = "Authentication", description = "User authentication APIs")
@RestController
@RequestMapping("/v1/auth")
public class AuthenticationController
{

    private final AuthenticationService authenticationService;

    @Autowired
    public AuthenticationController(AuthenticationService authenticationService)
    {
        this.authenticationService = authenticationService;
    }

    @PostMapping("/signUp")
    public ResponseEntity<AuthResponseDto> signUp(@Valid @RequestBody UserRequestDto userRequestDto)
    {
        return new ResponseEntity<>(authenticationService.signUp(userRequestDto), HttpStatus.CREATED);
    }

    @PostMapping("/login")
    public ResponseEntity<AuthResponseDto> login(@Valid @RequestBody AuthRequestDto authRequestDto)
    {
        return new ResponseEntity<>(authenticationService.authenticate(authRequestDto), HttpStatus.OK);
    }

    @PostMapping("/refreshToken")
    public ResponseEntity<AuthResponseDto> refresh(@RequestParam("token") String refreshToken)
    {
        return new ResponseEntity<>(authenticationService.refreshToken(refreshToken), HttpStatus.OK);

    }

    @GetMapping("/validateToken")
    public ResponseEntity<Boolean> validateToken(@RequestParam("token") String token)
    {
        return new ResponseEntity<>(authenticationService.validateToken(token), HttpStatus.OK);
    }
}
