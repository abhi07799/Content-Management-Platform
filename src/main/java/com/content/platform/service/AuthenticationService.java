package com.content.platform.service;

import com.content.platform.dto.request.AuthRequestDto;
import com.content.platform.dto.request.UserRequestDto;
import com.content.platform.dto.response.AuthResponseDto;
import com.content.platform.exception.ResourceAlreadyExistException;
import com.content.platform.model.Role;
import com.content.platform.model.UserModel;
import com.content.platform.repository.UserRepository;
import com.content.platform.security.JwtService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Optional;

@Service
public class AuthenticationService
{

    private final UserRepository userRepository;

    private final PasswordEncoder passwordEncoder;

    private final JwtService jwtService;

    private final AuthenticationManager authenticationManager;

    private final Logger log = LoggerFactory.getLogger(AuthenticationService.class);

    @Autowired
    public AuthenticationService(UserRepository userRepository, PasswordEncoder passwordEncoder, AuthenticationManager authenticationManager, JwtService jwtService)
    {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.authenticationManager = authenticationManager;
        this.jwtService = jwtService;
    }

    public AuthResponseDto signUp(UserRequestDto userRequestDto)
    {
        try
        {
            log.info("Processing request to add a new user");
            Optional<UserModel> optionalUserModel = userRepository.findByUserMail(userRequestDto.getUserMail());

            if (optionalUserModel.isPresent())
            {
                log.warn("Attempt to add a new user is failed, User already exist with email: {}", userRequestDto.getUserMail());
                throw new ResourceAlreadyExistException("User already exist with user mail: " + userRequestDto.getUserMail());
            }

            var user = UserModel.builder()
                    .userFullName(userRequestDto.getUserFullName())
                    .userMail(userRequestDto.getUserMail())
                    .userPassword(passwordEncoder.encode(userRequestDto.getUserPassword()))
                    .role(Role.valueOf(userRequestDto.getRole().toUpperCase()))
                    .build();

            userRepository.save(user);
            var jwtToken = jwtService.generateToken(user);
            var refreshToken = jwtService.generateRefresh(new HashMap<>(), user);
            return AuthResponseDto.builder()
                    .authToken(jwtToken)
                    .message("User Registration Successful")
                    .build();
        }
        catch (ResourceAlreadyExistException ex)
        {
            log.error("User already exists with mail: {} and exception: ", userRequestDto.getUserMail(), ex);
            throw ex;
        }
        catch (Exception ex)
        {
            log.error("An Unexpected error occurred while trying to add a new user with exception: {}", ex.getMessage(), ex);
            throw ex;
        }
    }

    public AuthResponseDto authenticate(AuthRequestDto authRequestDto)
    {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(authRequestDto.getUserMail(), authRequestDto.getUserPassword())
        );
        var user = userRepository.findByUserMail(authRequestDto.getUserMail()).orElseThrow(() -> new IllegalArgumentException("Invalid email or password"));
        var jwtToken = jwtService.generateToken(user);
        var refreshToken = jwtService.generateRefresh(new HashMap<>(), user);
        return AuthResponseDto.builder()
                .authToken(jwtToken)
                .refreshToken(refreshToken)
                .build();
    }

    public AuthResponseDto refreshToken(String refreshToken) {

        var user = userRepository.findByUserMail(jwtService.getEmailFromToken(refreshToken)).orElseThrow(() -> new IllegalArgumentException("Invalid refresh token"));
        var jwtToken = jwtService.generateToken(user);
        var newRefreshToken = jwtService.generateRefresh(new HashMap<>(), user);
        return AuthResponseDto.builder()
                .authToken(jwtToken)
                .refreshToken(newRefreshToken)
                .build();
    }

    public Boolean validateToken(String token) {
        return jwtService.validateToken(token);
    }


}
