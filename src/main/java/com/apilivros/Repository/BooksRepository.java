package com.apilivros.Repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.apilivros.Domain.Books;

public interface BooksRepository extends JpaRepository<Books, Integer>{
    
}
