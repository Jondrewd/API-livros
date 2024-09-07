package com.apilivros.Services;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

import com.apilivros.Domain.Books;
import com.apilivros.Repository.BooksRepository;
import com.apilivros.Services.Exceptions.ResourceNotFoundException;

@Component
public class BookService {
    
    @Autowired
    private BooksRepository bookRepository;

    public Page<Books> findAll(Pageable pageable){
        return bookRepository.findAll(pageable);    
    }
    public Books findById(Integer id){
        try{
        Optional<Books> obj = bookRepository.findById(id);
        return obj.get();
        }catch (EmptyResultDataAccessException e){
            throw new ResourceNotFoundException(id);
        }
    }
    public Books insertBook(Books obj){
        return bookRepository.save(obj);
    }
    public void deleteBook(Integer id){
        findById(id);
        bookRepository.deleteById(id);
    }
}
