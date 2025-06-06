package com.browna.teller_back.repositories;

import com.browna.teller_back.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {
        Optional<User> findByUsername(String username);

        Boolean existsByUsername(String username);
}

