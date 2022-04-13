package com.atakanoguz.jwtauth.service;

import com.atakanoguz.jwtauth.entity.Role;
import com.atakanoguz.jwtauth.entity.User;
import com.atakanoguz.jwtauth.exception.ApiRequestException;
import com.atakanoguz.jwtauth.repository.RoleRepository;
import com.atakanoguz.jwtauth.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
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
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;

    private boolean isOwnerOrAdmin(User attemptUser, User data){

        var isAdmin = attemptUser.getRoles().stream().anyMatch(role -> role.getName().equals("ROLE_ADMIN"));

        var isOwner = attemptUser.getId() == data.getId();

        return isAdmin || isOwner;

    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        User user = userRepository.findByUserName(username);

        if (user == null)
            throw new UsernameNotFoundException("User is not exist.");

        List<SimpleGrantedAuthority> authorities = new ArrayList<>();

        user.getRoles().forEach(role -> authorities.add(new SimpleGrantedAuthority(role.getName())));

        return new org.springframework.security.core.userdetails.User(user.getUserName(),user.getPassword(),authorities);
    }


    @Override
    public User saveUser(User user) {

        Role userRole = roleRepository.findByName("ROLE_USER");

        user.setPassword(passwordEncoder.encode(user.getPassword()));

        user.getRoles().add(userRole);

        return userRepository.save(user);
    }

    @Override
    public User getUser(String username, Principal principal) {

        var data = userRepository.findByUserName(username);
        var principalUser = userRepository.findByUserName(principal.getName());

        if (data == null)
            throw new ApiRequestException("User is not exist");

        if (!isOwnerOrAdmin(principalUser, data))
            throw new ApiRequestException("You are not authorized to perform this action");

        return userRepository.findByUserName(username);
    }

    @Override
    public void updateUser(User user, Principal principal) {

        var principalUser = userRepository.findByUserName(principal.getName());

        if (user.getId() == 0)
            throw new ApiRequestException("User id must not empty");

        if (!userRepository.existsById(user.getId()))
            throw new ApiRequestException("User is not exist");

        if (!isOwnerOrAdmin(principalUser,user))
            throw new ApiRequestException("You are not authorized to perform this action");

        user.setPassword(passwordEncoder.encode(user.getPassword()));

        userRepository.save(user);

    }

    @Override
    public void deleteUser(String username, Principal principal) {

        var principalUser = userRepository.findByUserName(principal.getName());

        User user = userRepository.findByUserName(username);

        if (user == null)
            throw new ApiRequestException("User is not exist");

        if (!isOwnerOrAdmin(principalUser, user))
            throw new ApiRequestException("You are not authorized to perform this action");

        userRepository.delete(user);

    }

    @Override
    public List<User> getUsers() {
        return userRepository.findAll();
    }

}
