package com.atakanoguz.jwtauth.repository;

import com.atakanoguz.jwtauth.entity.Role;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface RoleRepository extends JpaRepository<Role, Short> {

    Role findByName(String name);

    List<Role> findAllByOrderById();
}
