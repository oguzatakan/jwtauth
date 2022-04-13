package com.atakanoguz.jwtauth.service;

import com.atakanoguz.jwtauth.entity.Role;
import com.atakanoguz.jwtauth.entity.User;
import com.atakanoguz.jwtauth.repository.RoleRepository;
import com.atakanoguz.jwtauth.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class RoleServiceImpl implements RoleService{

    private final RoleRepository roleRepository;
    private final UserRepository userRepository;


    @Override
    public List<Role> getRoles() {
        return roleRepository.findAllByOrderById();
    }

    @Override
    public Role saveRole(Role role) {
        return roleRepository.save(role);
    }

    @Override
    public void addRoleToUser(String username, String roleName) {

        User user = userRepository.findByUserName(username);
        Role role = roleRepository.findByName(roleName);

        user.getRoles().add(role);

    }
}
