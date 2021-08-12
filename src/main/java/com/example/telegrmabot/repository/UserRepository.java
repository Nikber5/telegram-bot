package com.example.telegrmabot.repository;

import java.util.Optional;
import com.example.telegrmabot.model.User;
import org.springframework.data.repository.CrudRepository;

public interface UserRepository extends CrudRepository<User, Long> {
    Optional<User> findByUserName(String userName);
}
