package com.apilivros.apilivros.Services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Component;

import com.apilivros.apilivros.Domain.Books;
import com.apilivros.apilivros.Repository.BooksRepository;
import com.apilivros.apilivros.Services.Exceptions.ResourceNotFoundException;

@Component
public class BookService {
    
    @Autowired
    private BooksRepository bookRepository;

    public List<Books> findAll(){
        return bookRepository.findAll();
    }
    public Books findById(Integer id){
        try{
        Optional<Books> obj = bookRepository.findById(id);
        return obj.get();
        }catch (EmptyResultDataAccessException e){
            throw new ResourceNotFoundException(id);
        }
    }

}