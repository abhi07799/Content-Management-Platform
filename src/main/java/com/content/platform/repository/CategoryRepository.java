package com.content.platform.repository;

import com.content.platform.model.CategoryModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CategoryRepository extends JpaRepository<CategoryModel, Long>
{
    Optional<CategoryModel> findByCategoryName(String name);
}
