package com.content.platform.repository;

import com.content.platform.model.PostModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PostRepository extends JpaRepository<PostModel, Long>
{
    Optional<PostModel> findByPostTitle(String title);

    List<PostModel> findByPostCategoryCategoryName(String categoryName);
}
