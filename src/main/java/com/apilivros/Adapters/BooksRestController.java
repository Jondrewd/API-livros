package com.apilivros.Adapters;

import java.net.URI;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.apilivros.Domain.Books;
import com.apilivros.Services.BookService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;


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
    @PostMapping
    public ResponseEntity<Books> insertBook(@RequestBody Books obj) {
        obj = service.insertBook(obj);
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(obj.getId()).toUri();
        return ResponseEntity.created(uri).body(obj);
    }

    @DeleteMapping
    public ResponseEntity<Void> deleteBook(Integer id){
        service.deleteBook(id);
        return ResponseEntity.noContent().build();
    }
    

}
