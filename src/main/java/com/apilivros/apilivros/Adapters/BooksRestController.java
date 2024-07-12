package com.apilivros.apilivros.Adapters;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.apilivros.apilivros.Domain.Books;
import com.apilivros.apilivros.Services.BookService;

@RestController
@RequestMapping("/books")
public class BooksRestController {
    @Autowired
    private BookService service;

    @GetMapping
    public ResponseEntity<List<Books>> findAll(){
        List<Books> list = service.findAll();
        return ResponseEntity.ok().body(list);
    }
    @GetMapping(value = "/{id}")
    public ResponseEntity<Books> findById(@PathVariable Integer id){
        Books book = service.findById(id);
        return ResponseEntity.ok().body(book);
    }

}
