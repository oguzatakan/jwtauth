package com.atakanoguz.jwtauth.repository;

import com.atakanoguz.jwtauth.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {

    User findByUserName(String name);

    User findByEmail(String email);

}
