package com.example.lagoonria.repository;

import com.example.lagoonria.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface UserRepository extends JpaRepository<User, Integer> {
    @Query(value = "SELECT * FROM user WHERE email = :email", nativeQuery = true)
    User existByEmail(@Param("email") String email);
}
