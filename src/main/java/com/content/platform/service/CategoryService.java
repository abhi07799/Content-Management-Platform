package com.content.platform.service;

import com.content.platform.dto.request.CategoryRequestDto;
import com.content.platform.dto.response.CategoryResponseDto;
import com.content.platform.exception.ResourceAlreadyExistException;
import com.content.platform.exception.ResourceNotFoundException;
import com.content.platform.model.CategoryModel;
import com.content.platform.repository.CategoryRepository;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CategoryService
{
    private final CategoryRepository categoryRepository;

    private final ModelMapper mapper;

    private final Logger log = LoggerFactory.getLogger(CategoryService.class);

    @Autowired
    public CategoryService(CategoryRepository categoryRepository, ModelMapper modelMapper)
    {
        this.categoryRepository = categoryRepository;
        this.mapper = modelMapper;
    }

    public CategoryResponseDto addCategory(CategoryRequestDto categoryRequestDto)
    {
        try
        {
            log.info("Processing request to add category");
            Optional<CategoryModel> optionalCategoryModel = categoryRepository.findByCategoryName(categoryRequestDto.getCategoryName());

            if(optionalCategoryModel.isPresent())
            {
                log.warn("Attempt to add new category failed!!, Category with name {} already exist", categoryRequestDto.getCategoryName());
                throw new ResourceAlreadyExistException("Category already exist with name: " + categoryRequestDto.getCategoryName());
            }
            CategoryModel category = mapper.map(categoryRequestDto, CategoryModel.class);
            CategoryResponseDto savedCategory = mapper.map(categoryRepository.save(category), CategoryResponseDto.class);
            log.info("Category added successfully");
            return savedCategory;
        }
        catch (ResourceAlreadyExistException ex)
        {
            log.error("Category already exist with name, {}", categoryRequestDto.getCategoryName(), ex);
            throw ex;
        }
        catch (Exception ex)
        {
            log.error("An Error occurred while trying to add category with exception: {}", ex.getMessage(), ex);
            throw ex;
        }
    }

    public List<CategoryResponseDto> getAllCategories()
    {
        try
        {
            log.info("Processing request to retrieve all categories");
            List<CategoryModel> categoriesList = categoryRepository.findAll();

            if(categoriesList.isEmpty())
            {
                log.warn("Attempt to retrieve all categories failed!!!");
                throw new ResourceNotFoundException("No categories found!!!");
            }

            List<CategoryResponseDto> categoryResponseDtoList = categoriesList.stream().map(categoryModel -> mapper.map(categoryModel, CategoryResponseDto.class)).toList();
            log.info("All Categories retrieved successfully");
            return categoryResponseDtoList;
        }
        catch(ResourceNotFoundException ex)
        {
            log.error("No categories found!!!", ex);
            throw ex;
        }
        catch(Exception ex)
        {
            log.error("An Unexpected error occurred while trying to retrieve all categories", ex);
            throw ex;
        }
    }

    public CategoryResponseDto getCategoryById(Long categoryId)
    {
        try
        {
            log.info("Processing request to retrieve category by id, {}", categoryId);
            Optional<CategoryModel> optionalCategoryModel = categoryRepository.findById(categoryId);
            if (optionalCategoryModel.isEmpty())
            {
                log.warn("Attempt to retrieve category failed!!!");
                throw new ResourceNotFoundException("No category found!!! for categoryId: " + categoryId);
            }

            CategoryModel category = optionalCategoryModel.get();
            log.info("Category retrieved successfully");
            return mapper.map(category, CategoryResponseDto.class);
        }
        catch(ResourceNotFoundException ex)
        {
            log.error("No categories found!!! by categoryId: {}",categoryId, ex);
            throw ex;
        }
        catch(Exception ex)
        {
            log.error("An Unexpected error occurred while trying to retrieve category by id", ex);
            throw ex;
        }
    }

    public CategoryResponseDto getCategoryByName(String categoryName)
    {
        try
        {
            log.info("Processing request to retrieve category by name, {}", categoryName);
            Optional<CategoryModel> optionalCategoryModel = categoryRepository.findByCategoryName(categoryName);
            if (optionalCategoryModel.isEmpty())
            {
                log.warn("Attempt to retrieve category by name, failed!!!");
                throw new ResourceNotFoundException("No category found!!! for category name: " + categoryName);
            }

            CategoryModel category = optionalCategoryModel.get();
            log.info("Category retrieved successfully!!");
            return mapper.map(category, CategoryResponseDto.class);
        }
        catch(ResourceNotFoundException ex)
        {
            log.error("No categories found!!! by category name: {}",categoryName, ex);
            throw ex;
        }
        catch(Exception ex)
        {
            log.error("An Unexpected error occurred while trying to retrieve category by name", ex);
            throw ex;
        }
    }



}
