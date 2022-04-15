package com.atakanoguz.jwtauth.repository;

import com.atakanoguz.jwtauth.entity.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RoleRepository extends JpaRepository<Role, Short> {

    Role findByName(String name);

    List<Role> findAllByOrderById();
}
