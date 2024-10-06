package com.apilivros.Services;

import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

import com.apilivros.Domain.Books;
import com.apilivros.Dto.BookDTO;
import com.apilivros.Dto.Mappers.ReviewMapper;
import com.apilivros.Repository.BooksRepository;
import com.apilivros.Services.Exceptions.ResourceNotFoundException;

@Component
public class BookService {
    
    @Autowired
    private BooksRepository bookRepository;

    public Page<BookDTO> findAll(Pageable pageable){
        Page<Books> books = bookRepository.findAll(pageable);
        return books.map(this::convertToDTO);   
    }

    public Page<BookDTO> findByTitle(String title, Pageable pageable){
        Page<Books> bookPage = bookRepository.findByTitle(title, pageable);
        return bookPage.map(BookDTO::new);
    }

    public Page<BookDTO> findByRating(Integer rating, Pageable pageable){
        Page<Books> bookPage = bookRepository.findByRating(rating, pageable); 
        return bookPage.map(BookDTO::new);   
    }

    public BookDTO findById(Integer id){
        Books book = bookRepository.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("Objeto não encontrado."));
        return convertToDTO(book);
    }
    public Books insertBook(BookDTO obj){
        Books book = fromDTO(obj);
        return bookRepository.save(book);
    }
    public void deleteBook(Integer id){
        findById(id);
        bookRepository.deleteById(id);
    }

    public Books fromDTO(BookDTO dto) {
        Books book = new Books();
        book.setId(dto.getId()); 
        book.setAuthor(dto.getAuthor());;
        book.setGenre(dto.getGenre());
        book.setRating(dto.getRating()); 
        book.setReviews(dto.getReviews()
                            .stream()
                            .map(ReviewMapper::fromDTO)
                            .collect(Collectors.toList()));
        book.setTitle(dto.getTitle());
        return book;
    }
    private BookDTO convertToDTO(Books book) {
        return new BookDTO(book);
    }
}
