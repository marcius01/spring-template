package com.skullprogrammer.project.repository;

import com.skullprogrammer.project.model.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRespository extends JpaRepository<UserEntity, Long> {

    public Optional<UserEntity> findByUsername(String username);
}
