package com.content.platform.service;

import com.content.platform.dto.request.UserRequestDto;
import com.content.platform.dto.response.UserResponseDto;
import com.content.platform.exception.CustomException;
import com.content.platform.exception.ResourceAlreadyExistException;
import com.content.platform.exception.ResourceNotFoundException;
import com.content.platform.model.Role;
import com.content.platform.model.UserModel;
import com.content.platform.repository.UserRepository;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class UserService
{

    private final UserRepository userRepository;

    private final ModelMapper mapper;

    private final Logger log = LoggerFactory.getLogger(UserService.class);

    @Autowired
    public UserService(ModelMapper mapper, UserRepository userRepository)
    {
        this.mapper = mapper;
        this.userRepository = userRepository;
    }

    /***
     * Add a new user
     * @param userRequestDto data transfer object containing user details
     * @return {@link UserResponseDto} a dto containing user details
     * @throws ResourceAlreadyExistException if the user already exist
     * @throws CustomException if an unexpected error occurs
     */
    public UserResponseDto addUser(UserRequestDto userRequestDto)
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

            UserModel userModel = mapper.map(userRequestDto, UserModel.class);
            userModel.setRole(Role.valueOf(userRequestDto.getRole().toUpperCase()));
            userModel = userRepository.save(userModel);
            log.info("User added successfully with email: {}", userRequestDto.getUserMail());
            return mapper.map(userModel, UserResponseDto.class);
        }
        catch (ResourceAlreadyExistException ex)
        {
            log.error("User already exists with mail: {} and exception: ", userRequestDto.getUserMail(), ex);
            throw ex;
        }
        catch (Exception ex)
        {
            log.error("An error occurred while trying to add a new user with exception: {}", ex.getMessage(), ex);
            throw ex;
        }
    }

    /***
     * Returns all users
     * @return {@link UserResponseDto} a list of dto containing all users details
     * @throws ResourceNotFoundException if no users found
     * @throws CustomException if unexpected error occurs
     */
    public List<UserResponseDto> getAllUsers()
    {
        try
        {
            log.info("Processing request to get all users");
            List<UserModel> userModelList = userRepository.findAll();

            if (userModelList.isEmpty())
            {
                log.warn("Attempt to get all users failed, No users found");
                throw new ResourceNotFoundException("No users found");
            }

            List<UserResponseDto> userResponseDtoList = userModelList.stream().map(userModel -> mapper.map(userModel, UserResponseDto.class)).collect(Collectors.toList());
            log.info("Users found successfully");
            return userResponseDtoList;
        }
        catch (ResourceNotFoundException ex)
        {
            log.error("No users found");
            throw ex;
        }
        catch (Exception ex)
        {
            log.error("An error occurred while trying to get all users with exception: {}", ex.getMessage(), ex);
            throw ex;
        }
    }

    /***
     * To get user by user id
     * @param userId id of the user, trying to fetch
     * @return {@link UserResponseDto} a dto containing the specific user details
     * @throws ResourceNotFoundException if user not found
     * @throws CustomException if unexpected error occurs
     */
    public UserResponseDto getUserByUserId(Long userId)
    {
        try
        {
            log.info("Processing request to get user by userId");
            Optional<UserModel> optionalUserModel = userRepository.findById(userId);
            if (optionalUserModel.isEmpty())
            {
                log.warn("Attempt to get user by id failed, No user found for userId: {}", userId);
                throw new ResourceNotFoundException("No user found with id: " + userId);
            }
            log.info("User found successfully with userId: {}", userId);
            return mapper.map(optionalUserModel.get(), UserResponseDto.class);
        }
        catch (ResourceNotFoundException ex)
        {
            log.error("No user found with userId: {}", userId);
            throw ex;
        }
        catch (Exception ex)
        {
            log.error("An Error occurred while trying to get user by userId with exception: {}", ex.getMessage(), ex);
            throw ex;
        }
    }


    /***
     * Updates the user by user's id
     * @param userId id of user to be updated
     * @param userRequestDto dto containing user details
     * @return dto containing updated user details
     * @throws ResourceNotFoundException if user not found
     * @throws CustomException if unexpected error occurs
     */
    public UserResponseDto updateUserByUserId(Long userId, UserRequestDto userRequestDto)
    {
        try
        {
            log.info("Processing request to update user by userId");
            Optional<UserModel> optionalUserModel = userRepository.findById(userId);
            if (optionalUserModel.isEmpty())
            {
                log.warn("Attempt to update user by user id failed, No user found for userId: {}", userId);
                throw new ResourceNotFoundException("No user found with id: " + userId);
            }
            UserModel userModel = optionalUserModel.get();
            if (userRequestDto.getUserFullName() != null && !userRequestDto.getUserFullName().isEmpty())
            {
                userModel.setUserFullName(userRequestDto.getUserFullName());
            }
            if (userRequestDto.getUserMail() != null && !userRequestDto.getUserMail().isEmpty())
            {
                userModel.setUserMail(userRequestDto.getUserMail());
            }
            UserResponseDto updatedUserResponseDto = mapper.map(userRepository.save(userModel), UserResponseDto.class);
            log.info("User updated successfully with userId: {}", userId);
            return updatedUserResponseDto;
        }
        catch (ResourceNotFoundException ex)
        {
            log.error("No user found with user id: {}", userId);
            throw ex;
        }
        catch (NullPointerException ex)
        {
            log.error("An Error occurred while trying to update user by userId with exception: {}", ex.getMessage(), ex);
            throw new CustomException("An Unexpected error occurred while trying to update user by userId");
        }
        catch (Exception ex)
        {
            log.error("An Error occurred while trying to update user by userId with exception: {}", ex.getMessage(), ex);
            throw ex;
        }
    }


}
