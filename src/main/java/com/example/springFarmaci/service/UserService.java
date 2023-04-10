package com.example.springFarmaci.service;

import com.example.springFarmaci.dto.UserDTO;
import com.example.springFarmaci.models.Roles;
import com.example.springFarmaci.repository.RoleRepository;
import com.example.springFarmaci.repository.UserRepository;
import com.example.springFarmaci.models.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.Query;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.persistence.*;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaDelete;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.CriteriaUpdate;
import javax.persistence.metamodel.Metamodel;
import java.util.*;

@Service
public class UserService {
    final EntityManager em = new EntityManager() {
        @Override
        public void persist(Object o) {

        }

        @Override
        public <T> T merge(T t) {
            return null;
        }

        @Override
        public void remove(Object o) {

        }

        @Override
        public <T> T find(Class<T> aClass, Object o) {
            return null;
        }

        @Override
        public <T> T find(Class<T> aClass, Object o, Map<String, Object> map) {
            return null;
        }

        @Override
        public <T> T find(Class<T> aClass, Object o, LockModeType lockModeType) {
            return null;
        }

        @Override
        public <T> T find(Class<T> aClass, Object o, LockModeType lockModeType, Map<String, Object> map) {
            return null;
        }

        @Override
        public <T> T getReference(Class<T> aClass, Object o) {
            return null;
        }

        @Override
        public void flush() {

        }

        @Override
        public void setFlushMode(FlushModeType flushModeType) {

        }

        @Override
        public FlushModeType getFlushMode() {
            return null;
        }

        @Override
        public void lock(Object o, LockModeType lockModeType) {

        }

        @Override
        public void lock(Object o, LockModeType lockModeType, Map<String, Object> map) {

        }

        @Override
        public void refresh(Object o) {

        }

        @Override
        public void refresh(Object o, Map<String, Object> map) {

        }

        @Override
        public void refresh(Object o, LockModeType lockModeType) {

        }

        @Override
        public void refresh(Object o, LockModeType lockModeType, Map<String, Object> map) {

        }

        @Override
        public void clear() {

        }

        @Override
        public void detach(Object o) {

        }

        @Override
        public boolean contains(Object o) {
            return false;
        }

        @Override
        public LockModeType getLockMode(Object o) {
            return null;
        }

        @Override
        public void setProperty(String s, Object o) {

        }

        @Override
        public Map<String, Object> getProperties() {
            return null;
        }

        @Override
        public javax.persistence.Query createQuery(String s) {
            return null;
        }

        @Override
        public <T> TypedQuery<T> createQuery(CriteriaQuery<T> criteriaQuery) {
            return null;
        }

        @Override
        public javax.persistence.Query createQuery(CriteriaUpdate criteriaUpdate) {
            return null;
        }

        @Override
        public javax.persistence.Query createQuery(CriteriaDelete criteriaDelete) {
            return null;
        }

        @Override
        public <T> TypedQuery<T> createQuery(String s, Class<T> aClass) {
            return null;
        }

        @Override
        public javax.persistence.Query createNamedQuery(String s) {
            return null;
        }

        @Override
        public <T> TypedQuery<T> createNamedQuery(String s, Class<T> aClass) {
            return null;
        }

        @Override
        public javax.persistence.Query createNativeQuery(String s) {
            return null;
        }

        @Override
        public javax.persistence.Query createNativeQuery(String s, Class aClass) {
            return null;
        }

        @Override
        public javax.persistence.Query createNativeQuery(String s, String s1) {
            return null;
        }

        @Override
        public StoredProcedureQuery createNamedStoredProcedureQuery(String s) {
            return null;
        }

        @Override
        public StoredProcedureQuery createStoredProcedureQuery(String s) {
            return null;
        }

        @Override
        public StoredProcedureQuery createStoredProcedureQuery(String s, Class... classes) {
            return null;
        }

        @Override
        public StoredProcedureQuery createStoredProcedureQuery(String s, String... strings) {
            return null;
        }

        @Override
        public void joinTransaction() {

        }

        @Override
        public boolean isJoinedToTransaction() {
            return false;
        }

        @Override
        public <T> T unwrap(Class<T> aClass) {
            return null;
        }

        @Override
        public Object getDelegate() {
            return null;
        }

        @Override
        public void close() {

        }

        @Override
        public boolean isOpen() {
            return false;
        }

        @Override
        public EntityTransaction getTransaction() {
            return null;
        }

        @Override
        public EntityManagerFactory getEntityManagerFactory() {
            return null;
        }

        @Override
        public CriteriaBuilder getCriteriaBuilder() {
            return null;
        }

        @Override
        public Metamodel getMetamodel() {
            return null;
        }

        @Override
        public <T> EntityGraph<T> createEntityGraph(Class<T> aClass) {
            return null;
        }

        @Override
        public EntityGraph<?> createEntityGraph(String s) {
            return null;
        }

        @Override
        public EntityGraph<?> getEntityGraph(String s) {
            return null;
        }

        @Override
        public <T> List<EntityGraph<? super T>> getEntityGraphs(Class<T> aClass) {
            return null;
        }
    };
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

    public void deleteUserRoleById(Long id) {
        String sql = "DELETE FROM user_role WHERE id = " + id;
        Query query = (Query) em.createNativeQuery(sql);
    }

    public void deleteUser(Long id) {
        Optional<User> user = userRepository.findById(id);
        if(user.isPresent()) {
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
