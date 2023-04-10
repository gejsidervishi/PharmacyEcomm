package com.example.springFarmaci.repository;

import com.example.springFarmaci.models.User;
import org.hibernate.engine.spi.SessionDelegatorBaseImpl;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import javax.persistence.*;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaDelete;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.CriteriaUpdate;
import javax.persistence.metamodel.Metamodel;
import javax.transaction.Transactional;
import java.util.List;
import java.util.Map;

public interface UserRepository extends JpaRepository<User, Long> {

@Query(value = "SELECT * FROM user WHERE email = :email AND to_date IS NULL ", nativeQuery = true)
    public User findByEmail(@Param("email") String value);

    @Query(value = "SELECT * FROM user WHERE to_date IS NULL", nativeQuery = true)
    public List<User> findAll();


}
