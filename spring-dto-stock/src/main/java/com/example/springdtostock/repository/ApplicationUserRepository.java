package com.example.springdtostock.repository;

import com.example.springdtostock.entity.ApplicationUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface ApplicationUserRepository extends JpaRepository<ApplicationUser, Integer> {
    @Query("select u from ApplicationUser u left join fetch u.roles where u.username = :username")
    Optional<ApplicationUser> findApplicationUserByUsername(String username);
}