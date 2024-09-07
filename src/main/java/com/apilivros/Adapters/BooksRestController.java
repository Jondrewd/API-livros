package com.apilivros.Adapters;

import java.net.URI;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
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
    public ResponseEntity<Page<Books>> findAll(
        @RequestParam(value = "page", defaultValue = "0") Integer page,
        @RequestParam(value = "size", defaultValue = "12") Integer size,
        @RequestParam(value = "direction", defaultValue = "asc") String direction){
            var sortDirection = "desc".equalsIgnoreCase(direction) ? Direction.DESC : Direction.ASC;
            Pageable pageable = PageRequest.of(page, size, Sort.by(sortDirection, "title"));
            return ResponseEntity.ok(service.findAll(pageable));
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
