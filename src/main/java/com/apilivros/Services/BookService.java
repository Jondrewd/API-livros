package com.apilivros.Services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.apilivros.Domain.Books;
import com.apilivros.Dto.BookDTO;
import com.apilivros.Dto.Mappers.BookMapper;
import com.apilivros.Repository.BooksRepository;
import com.apilivros.Services.Exceptions.ResourceNotFoundException;

@Service
public class BookService {
    
    @Autowired
    private BooksRepository bookRepository;

    public Page<BookDTO> findAll(Pageable pageable){
        Page<Books> books = bookRepository.findAll(pageable);
        return books.map(BookMapper::convertBookToDTO);   
    }

    public Page<BookDTO> findByTitle(String title, Pageable pageable){
        Page<Books> bookPage = bookRepository.findByTitle(title, pageable);
        return bookPage.map(BookDTO::new);
    }

    public Page<BookDTO> findByRating(Integer rating, Pageable pageable){
        Page<Books> bookPage = bookRepository.findByRating(rating, pageable); 
        return bookPage.map(BookDTO::new);   
    }

   public Page<BookDTO> findByAuthor(String author, Pageable pageable){
        Page<Books> bookPage = bookRepository.findByAuthor(author, pageable); 
        return bookPage.map(BookDTO::new);   
    }

    public BookDTO findById(Integer id){
        Books book = bookRepository.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("Objeto não encontrado."));
        return BookMapper.convertBookToDTO(book);
    }
    public Books insertBook(BookDTO obj){
        Books book = BookMapper.fromDTO(obj);
        return bookRepository.save(book);
    }
    public void deleteBook(Integer id){
        findById(id);
        bookRepository.deleteById(id);
    }
}
