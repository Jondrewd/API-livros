package com.apilivros.Repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.apilivros.Domain.Permission;

import java.util.Optional;

public interface PermissionRepository extends JpaRepository<Permission, Integer>{
    
    Optional<Permission> findByName(String name);
}
