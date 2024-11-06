package com.fullstackdevops.userms.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.fullstackdevops.userms.model.User;

public interface UserRepository extends JpaRepository<User, Long> {
    boolean existsByEmail(String email);
    boolean existsByUsername(String username);

}
