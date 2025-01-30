package com.content.platform.repository;

import com.content.platform.model.UserModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<UserModel, Long>
{
    Optional<UserModel> findByUserMail(String username);

    Optional<UserModel> findByRole(String role);
}
