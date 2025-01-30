package com.content.platform.service;

import com.content.platform.dto.request.CategoryRequestDto;
import com.content.platform.dto.response.CategoryResponseDto;
import com.content.platform.dto.response.UserResponseDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AdminService
{

    private final UserService userService;

    private final CategoryService categoryService;

    @Autowired
    public AdminService(CategoryService categoryService, UserService userService)
    {
        this.categoryService = categoryService;
        this.userService = userService;
    }

    public List<UserResponseDto> getAllUsers()
    {
        return userService.getAllUsers();
    }


    public UserResponseDto getUserByUserId(Long userId)
    {
        return userService.getUserByUserId(userId);
    }

    public CategoryResponseDto addCategory(CategoryRequestDto categoryRequestDto)
    {
        return categoryService.addCategory(categoryRequestDto);
    }
}
