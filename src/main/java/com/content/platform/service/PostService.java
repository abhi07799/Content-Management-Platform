package com.content.platform.service;

import com.content.platform.dto.request.PostRequestDto;
import com.content.platform.dto.response.PostResponseDto;
import com.content.platform.exception.CustomException;
import com.content.platform.exception.ResourceAlreadyExistException;
import com.content.platform.exception.ResourceNotFoundException;
import com.content.platform.model.PostModel;
import com.content.platform.model.PostType;
import com.content.platform.repository.CategoryRepository;
import com.content.platform.repository.PostRepository;
import com.content.platform.repository.UserRepository;
import com.content.platform.util.PlatformConstants;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PostService
{
    private final PostRepository postRepository;

    private final ModelMapper mapper;

    private final CategoryRepository categoryRepository;

    private final UserRepository userRepository;

    private final Logger log = LoggerFactory.getLogger(PostService.class);

    @Autowired
    public PostService(CategoryRepository categoryRepository, PostRepository postRepository, ModelMapper mapper, UserRepository userRepository)
    {
        this.categoryRepository = categoryRepository;
        this.postRepository = postRepository;
        this.mapper = mapper;
        this.userRepository = userRepository;
    }


    public PostResponseDto addPost(PostRequestDto postRequestDto)
    {
        try
        {
            log.info("Processing request to add post");
            Optional<PostModel> optionalPostModel = postRepository.findByPostTitle(postRequestDto.getPostTitle());

            if(optionalPostModel.isPresent())
            {
                log.warn("Attempt to add new post failed!!, Post with title {} already exist", postRequestDto.getPostTitle());
                throw new ResourceAlreadyExistException("Post already exist with name: " + postRequestDto.getPostTitle());
            }

            if(!categoryRepository.existsById(postRequestDto.getPostCategory().getId()))
            {
                log.warn("Attempt to add post failed!!!");
                throw new ResourceNotFoundException("Invalid Category ID: " + postRequestDto.getPostCategory().getId());
            }

            if(!userRepository.existsById(postRequestDto.getPostAuthor().getId()))
            {
                log.warn("Attempt to add post failed!!!");
                throw new ResourceNotFoundException("Invalid Author ID: " + postRequestDto.getPostAuthor().getId());
            }

            PostModel postModel = mapper.map(postRequestDto, PostModel.class);
            postModel.setPostType(PostType.valueOf(postRequestDto.getPostType().toUpperCase()));
            postModel = postRepository.save(postModel);
            log.info("Post added successfully");
            return mapper.map(postModel, PostResponseDto.class);
        }
        catch (ResourceAlreadyExistException ex)
        {
            log.error("Post already exist with title, {}", postRequestDto.getPostTitle(), ex);
            throw ex;
        }
        catch(ResourceNotFoundException ex)
        {
            log.error("No resource found!!! due to invalid category id or user id", ex);
            throw ex;
        }
        catch (Exception ex)
        {
            log.error("An Error occurred while trying to add post with exception: {}", ex.getMessage(), ex);
            throw ex;
        }
    }

    public List<PostResponseDto> getAllPosts()
    {
        try
        {
            log.info("Processing request to get all posts");
            List<PostModel> postsList = postRepository.findAll();

            if(postsList.isEmpty())
            {
                log.warn("Attempt to retrieve all posts failed!!!");
                throw new ResourceNotFoundException("No Posts found!!!");
            }

            List<PostResponseDto> postResponseDtoList = postsList.stream().map(post-> mapper.map(post, PostResponseDto.class)).toList();
            log.info("All Posts retrieved successfully");
            return postResponseDtoList;
        }
        catch(ResourceNotFoundException ex)
        {
            log.error("No posts found!!!", ex);
            throw ex;
        }
        catch(Exception ex)
        {
            log.error("An Unexpected error occurred while trying to retrieve all posts", ex);
            throw ex;
        }
    }

    public PostResponseDto getPostById(Long postId)
    {
        try
        {
            log.info("Processing request to get post by id {}", postId);
            Optional<PostModel> optionalPostModel = postRepository.findById(postId);
            if(optionalPostModel.isEmpty())
            {
                log.warn("Attempt to retrieve post by id failed!!!");
                throw new ResourceNotFoundException("No post found!!! for postId: " + postId);
            }

            PostModel postModel = optionalPostModel.get();
            log.info("Post retrieved successfully");
            return mapper.map(postModel, PostResponseDto.class);
        }
        catch(ResourceNotFoundException ex)
        {
            log.error("No post found!!! by postId: {}",postId, ex);
            throw ex;
        }
        catch(Exception ex)
        {
            log.error("An Unexpected error occurred while trying to retrieve post by id", ex);
            throw ex;
        }
    }

    public PostResponseDto getPostByTitle(String postTitle)
    {
        try
        {
            log.info("Processing request to get post by title {}", postTitle);
            Optional<PostModel> optionalPostModel = postRepository.findByPostTitle(postTitle);
            if(optionalPostModel.isEmpty())
            {
                log.warn("Attempt to retrieve post by title failed!!!");
                throw new ResourceNotFoundException("No post found by title: " + postTitle);
            }
            PostModel postModel = optionalPostModel.get();
            log.info("Post retrieved successfully!!");
            return mapper.map(postModel, PostResponseDto.class);
        }
        catch(ResourceNotFoundException ex)
        {
            log.error("No post found!!! by postTitle: {}",postTitle, ex);
            throw ex;
        }
        catch(Exception ex)
        {
            log.error("An Unexpected error occurred while trying to retrieve post by title", ex);
            throw ex;
        }
    }

    public List<PostResponseDto> getPostsByCategory(String categoryName)
    {
        try
        {
            log.info("Processing request to get posts by category {}", categoryName);
            List<PostModel> postModelList = postRepository.findByPostCategoryCategoryName(categoryName);

            if(postModelList.isEmpty())
            {
                log.warn("Attempt to retrieve posts by category failed!!!");
                throw new ResourceNotFoundException("No posts found!!! by category: " + categoryName);
            }

            List<PostResponseDto> postResponseDtoList = postModelList.stream().map(post-> mapper.map(post, PostResponseDto.class)).toList();
            log.info("All Posts retrieved successfully!");
            return postResponseDtoList;
        }
        catch(ResourceNotFoundException ex)
        {
            log.error("No posts found!!! by postCategoryName: {}",categoryName, ex);
            throw ex;
        }
        catch(Exception ex)
        {
            log.error("An Unexpected error occurred while trying to retrieve post by category", ex);
            throw ex;
        }
    }

    public PostResponseDto updatePostById(Long postId, PostRequestDto postRequestDto)
    {
        try
        {
            log.info("Processing request to update post by id {}", postId);
            Optional<PostModel> optionalPostModel = postRepository.findById(postId);
            if(optionalPostModel.isEmpty())
            {
                log.warn("Attempt to update post by id failed!!!");
                throw new ResourceNotFoundException("No post found by id: " + postId);
            }
            PostModel postModel = optionalPostModel.get();
            if(postRequestDto.getPostTitle() != null && !postRequestDto.getPostTitle().isEmpty())
            {
                if(postRepository.findByPostTitle(postRequestDto.getPostTitle()).isPresent())
                {
                    log.warn("Attempt to update post by title failed!!!, Post already exist with title: {}", postRequestDto.getPostTitle());
                    throw new ResourceAlreadyExistException("Post already exist with title: " + postRequestDto.getPostTitle());
                }
                postModel.setPostTitle(postRequestDto.getPostTitle());
            }
            if(postRequestDto.getPostDescription() != null && !postRequestDto.getPostDescription().isEmpty())
            {
                postModel.setPostDescription(postRequestDto.getPostDescription());
            }
            if(postRequestDto.getPostContent() != null && !postRequestDto.getPostContent().isEmpty())
            {
                postModel.setPostContent(postRequestDto.getPostContent());
            }
            if(postRequestDto.getPostType() != null && !postRequestDto.getPostType().isEmpty())
            {
                if(!PlatformConstants.POST_TYPE_LIST.contains(postRequestDto.getPostType().toUpperCase()))
                {
                    log.warn("Attempt to update post by type failed!!!, Invalid post type: {}", postRequestDto.getPostType());
                    throw new CustomException("Invalid post type: " + postRequestDto.getPostType());
                }
                postModel.setPostType(PostType.valueOf(postRequestDto.getPostType().toUpperCase()));
            }

            PostResponseDto postResponseDto = mapper.map(postRepository.save(postModel), PostResponseDto.class);
            log.info("Post updated successfully");
            return postResponseDto;
        }
        catch (ResourceNotFoundException ex)
        {
            log.error("No post found with post id: {}", postId);
            throw ex;
        }
        catch (ResourceAlreadyExistException ex)
        {
            log.error("Post already exist with title, {}", postRequestDto.getPostTitle(), ex);
            throw ex;
        }
        catch (NullPointerException ex)
        {
            log.error("An Error occurred while trying to update post by postId with exception: {}", ex.getMessage(), ex);
            throw new CustomException("An Unexpected error occurred while trying to update post by postId");
        }
        catch(CustomException ex)
        {
            log.error("An Error occurred while trying to update post by postId due to invalid post type with exception: {}", ex.getMessage(), ex);
            throw ex;
        }
        catch (Exception ex)
        {
            log.error("An Unexpected Error occurred while trying to update post by postId with exception: {}", ex.getMessage(), ex);
            throw ex;
        }
    }
}
