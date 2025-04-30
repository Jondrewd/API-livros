package com.apilivros.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.apilivros.Domain.Review;
import com.apilivros.Domain.pk.ReviewID;

import jakarta.transaction.Transactional;

public interface ReviewRepository extends JpaRepository<Review, ReviewID>{

    @Query("SELECT r FROM Review r WHERE r.id.bookId = :bookId AND r.id.profileId = :profileId")
    Review findByBookAndUser(@Param("bookId") Long bookId, @Param("profileId") Long profileId);
    
    @Modifying
    @Transactional
    @Query("DELETE FROM Review r WHERE r.id.bookId = :bookId AND r.id.profileId = :profileId")
    void deleteByBookAndUser(@Param("bookId") Long bookId, @Param("profileId") Long profileId);
}
