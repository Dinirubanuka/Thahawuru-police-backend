package com.thahawuru_police.application.repository;

import com.thahawuru_police.application.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;


import java.util.Optional;
import java.util.UUID;

public interface UserRepository extends JpaRepository<User, UUID> {

    Optional<User> findUserByEmail(String email);
    Optional<User> findUserByNic(String nic);
}