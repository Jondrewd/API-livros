package com.apilivros.Repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.apilivros.Domain.Roles;

import java.util.Optional;

public interface RolesRepository extends JpaRepository<Roles, Long>{
    
    Optional<Roles> findByName(String name);
    
}
