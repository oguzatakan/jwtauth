package com.atakanoguz.jwtauth.service;

import com.atakanoguz.jwtauth.entity.Role;
import com.atakanoguz.jwtauth.entity.User;
import com.atakanoguz.jwtauth.repository.RoleRepository;
import com.atakanoguz.jwtauth.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.security.Principal;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;


@Service
@RequiredArgsConstructor
@Transactional
public class UserServiceImpl implements UserService, UserDetailsService {

    private final UserRepository userRepository;
    private final RoleRepository repository;
    private final PasswordEncoder passwordEncoder;

    private boolean isOwnerOrAdmin(User attemptUser, User data){

        var isAdmin = attemptUser.getRoles().stream().anyMatch(role -> role.getName().equals("ROLE_ADMIN"));

        var isOwner = attemptUser.getId() == data.getId();

        return isAdmin || isOwner;

    }


    @Override
    public User saveUser(User user) {
        return null;
    }

    @Override
    public User getUser(String username, Principal principal) {
        return null;
    }

    @Override
    public void updateUser(User user, Principal principal) {

    }

    @Override
    public void deleteUser(String username, Principal principal) {

    }

    @Override
    public List<User> getUsers() {
        return null;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return null;
    }
}
