package com.example.springFarmaci.service;

import com.example.springFarmaci.dto.UserDTO;
import com.example.springFarmaci.models.Roles;
import com.example.springFarmaci.repository.RoleRepository;
import com.example.springFarmaci.repository.UserRepository;
import com.example.springFarmaci.models.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.*;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaDelete;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.CriteriaUpdate;
import javax.persistence.metamodel.Metamodel;
import java.util.*;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private RoleRepository roleRepository;

    public List<User> findAll() {
        return userRepository.findAll();
    }

    public User findOne(String email) {
        return userRepository.findByEmail(email);
    }

    public User signUp(UserDTO userDTO) {
        User user = new User();
        user.setFirstName(userDTO.getFirstName());
        user.setLastName(userDTO.getLastName());
        user.setEmail(userDTO.getEmail());
        user.setPassword(passwordEncoder.encode(userDTO.getPassword()));
        user.setRoles(new HashSet<>(Arrays.asList(roleRepository.findById(1L).get())));
        return userRepository.save(user);
    }

    public User addUser(UserDTO userDTO) {
        User user = new User();
        user.setFirstName(userDTO.getFirstName());
        user.setLastName(userDTO.getLastName());
        user.setEmail(userDTO.getEmail());
        user.setPassword(userDTO.getPassword());
        if (userDTO.getRole().equals("User")){
            user.setRoles(new HashSet<>(Arrays.asList(roleRepository.getOne(1L))));
        } else {
            user.setRoles(new HashSet<>(Arrays.asList(roleRepository.getOne(2L))));
        }
        user.setCreatedAt(new Date());
        return userRepository.save(user);
    }

    public void deleteUser(Long id) {
        Optional<User> user = userRepository.findById(id);
        if(user.isPresent()) {
            for(Roles role:user.get().getRoles()) {
                user.get().removeRole(role);
            }
            userRepository.deleteById(id);
        }

    }

    public void updateUser(UserDTO userDTO, Long id) {
        Optional<User> user = userRepository.findById(id);
        if(user.isPresent()){
            user.get().setFirstName(userDTO.getFirstName());
            user.get().setLastName(userDTO.getLastName());
            user.get().setEmail(userDTO.getEmail());
            user.get().setPassword(userDTO.getPassword());
            userRepository.save(user.get());
        }

    }

    public void updatePermission(Long id) {
        Optional<User> user = userRepository.findById(id);
        if(user.isPresent()) {
            user.get().getRoles().add(roleRepository.findById(2L).get());
            userRepository.save(user.get());
        }
    }

    private void verifyRoles() {
        if(!roleRepository.findById(1L).isPresent()) {
            roleRepository.save(new Roles("User"));
        }
        if(!roleRepository.findById(2L).isPresent()) {
            roleRepository.save(new Roles("Admin"));
        }
    }

    public Boolean isEmailPresent(String email) {
        User user = userRepository.findByEmail(email);
        if(user == null) {
            return false;
        }
        return true;
    }
}
