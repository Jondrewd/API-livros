package com.apilivros.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.apilivros.Domain.Profile;

public interface ProfileRepository extends JpaRepository<Profile, Integer>{

    @Query("SELECT u FROM User u WHERE u.username = :username")
    Profile findByUsername(@Param("username") String username);

}