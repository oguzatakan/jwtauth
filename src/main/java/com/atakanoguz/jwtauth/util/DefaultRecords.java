package com.atakanoguz.jwtauth.util;

import com.atakanoguz.jwtauth.entity.Role;
import com.atakanoguz.jwtauth.entity.User;
import com.atakanoguz.jwtauth.service.RoleService;
import com.atakanoguz.jwtauth.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class DefaultRecords implements CommandLineRunner {

    private final RoleService roleService;
    private final UserService userService;

    @Override
    public void run(String... args) throws Exception{
        //created roles
        List<Role> roles = List.of(
                new Role((short) 1, "ROLE_SUPER_ADMIN"),
                new Role((short) 2, "ROLE_ADMIN"),
                new Role((short) 3, "ROLE_USER"));

        //roles save to db
        roles.forEach(roleService::saveRole);

        //created users
        List<User> users = List.of(
                new User("root", "Super Admin", "superadmin@testuser.com",
                        "root@1234"),

                new User("admin", "Admin", "admin@testuser.com",
                        "admin@1234"),

                new User("user", "User", "user@testuser.com",
                        "user@1234"),

                new User("testuser", "Test User", "testuser@testuser.com",
                        "testuser@1234")
        );
        //Users save to db
        users.forEach(userService::saveUser);

        //roles save to users
        roles.forEach(role -> roleService.addRoleToUser("root", role.getName()));
        roleService.addRoleToUser("admin", roles.get(1).getName());


    }


}
