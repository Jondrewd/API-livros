package com.apilivros.Repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.apilivros.Domain.Roles;

import java.util.Optional;

public interface RolesRepository extends JpaRepository<Roles, Integer>{
    
    Optional<Roles> findByName(String name);
    
}
