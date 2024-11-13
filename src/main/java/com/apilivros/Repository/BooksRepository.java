package com.apilivros.Repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.apilivros.Domain.Books;

public interface BooksRepository extends JpaRepository<Books, Integer>{
    
    @Query("SELECT b FROM Books b WHERE b.title LIKE LOWER(CONCAT('%',:title,'%'))")
    Page<Books> findByTitle(@Param("title") String title, Pageable pageable);

    @Query("SELECT b FROM Books b WHERE b.rating >= :rating AND b.rating < :rating + 1")
    Page<Books> findByRating(@Param("rating") Integer rating, Pageable pageable);

    @Query("SELECT b FROM Books b WHERE LOWER(b.author.name) LIKE LOWER(CONCAT('%', :author, '%'))")
    Page<Books> findByAuthor(@Param("author") String author, Pageable pageable);
}
