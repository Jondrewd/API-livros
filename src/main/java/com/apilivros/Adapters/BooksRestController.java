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
import com.apilivros.Dto.BookDTO;
import com.apilivros.Services.BookService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

@Tag(name = "Endpoints de Livros")
@RestController
@RequestMapping("/books")
public class BooksRestController {
    
    @Autowired
    private BookService service;

    @Operation(summary = "Retorna todos os Livros registrados.")
    @GetMapping
    public ResponseEntity<Page<BookDTO>> findAll(
        @RequestParam(value = "page", defaultValue = "0") Integer page,
        @RequestParam(value = "size", defaultValue = "12") Integer size,
        @RequestParam(value = "direction", defaultValue = "asc") String direction){
            var sortDirection = "desc".equalsIgnoreCase(direction) ? Direction.DESC : Direction.ASC;
            Pageable pageable = PageRequest.of(page, size, Sort.by(sortDirection, "title"));
            return ResponseEntity.ok(service.findAll(pageable));
    }
    @Operation(summary = "Retorna Livros pelo titulo.")
    @GetMapping(value = "/title/{title}")
    public ResponseEntity<Page<BookDTO>> findByTitle(
        @PathVariable(value = "title") String title,
        @RequestParam(value = "page", defaultValue = "0") Integer page,
        @RequestParam(value = "size", defaultValue = "12") Integer size,
        @RequestParam(value = "direction", defaultValue = "asc") String direction){
           
            var sortDirection = "desc".equalsIgnoreCase(direction) ? Direction.DESC : Direction.ASC;
            Pageable pageable = PageRequest.of(page, size, Sort.by(sortDirection, "title"));
            return ResponseEntity.ok(service.findByTitle(title, pageable));
    }

    @Operation(summary = "Retorna Livros pela média de notas.")
    @GetMapping(value = "/rating/{rating}")
    public ResponseEntity<Page<BookDTO>> findByRating(
        @PathVariable(value = "rating") Integer rating,
        @RequestParam(value = "page", defaultValue = "0") Integer page,
        @RequestParam(value = "size", defaultValue = "12") Integer size,
        @RequestParam(value = "direction", defaultValue = "asc") String direction){
           
            var sortDirection = "desc".equalsIgnoreCase(direction) ? Direction.DESC : Direction.ASC;
            Pageable pageable = PageRequest.of(page, size, Sort.by(sortDirection, "title"));
            return ResponseEntity.ok(service.findByRating(rating, pageable));
    }
    @Operation(summary = "Retorna Livros pelo gênero.")
    @GetMapping(value = "/genre/{genre}")
    public ResponseEntity<Page<BookDTO>> findByGenre(
        @PathVariable(value = "genre") Integer genre,
        @RequestParam(value = "page", defaultValue = "0") Integer page,
        @RequestParam(value = "size", defaultValue = "12") Integer size,
        @RequestParam(value = "direction", defaultValue = "asc") String direction){
           
            var sortDirection = "desc".equalsIgnoreCase(direction) ? Direction.DESC : Direction.ASC;
            Pageable pageable = PageRequest.of(page, size, Sort.by(sortDirection, "title"));
            return ResponseEntity.ok(service.findByGenre(genre, pageable));
    }
    
    @Operation(summary = "Retorna Livros por Autor.")
    @GetMapping(value = "/author/{author}")
    public ResponseEntity<Page<BookDTO>> findByAuthor(
        @PathVariable(value = "author") String author,
        @RequestParam(value = "page", defaultValue = "0") Integer page,
        @RequestParam(value = "size", defaultValue = "12") Integer size,
        @RequestParam(value = "direction", defaultValue = "asc") String direction){
           
            var sortDirection = "desc".equalsIgnoreCase(direction) ? Direction.DESC : Direction.ASC;
            Pageable pageable = PageRequest.of(page, size, Sort.by(sortDirection, "title"));
            return ResponseEntity.ok(service.findByAuthor(author, pageable));
    }

    @Operation(summary = "Retorna Livro pelo ID.")
    @GetMapping(value = "/{id}")
    public ResponseEntity<BookDTO> findById(@PathVariable Integer id){
        BookDTO book = service.findById(id);
        return ResponseEntity.ok().body(book);
    }

    @Operation(summary = "Registra um novo Livro.")
    @PostMapping
    public ResponseEntity<BookDTO> insertBook(@RequestBody BookDTO objdDto) {
        Books obj = service.insertBook(objdDto);
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(obj.getId()).toUri();
        return ResponseEntity.created(uri).body(objdDto);
    }

    @Operation(summary = "Deleta Livro pelo ID.")
    @DeleteMapping
    public ResponseEntity<Void> deleteBook(Integer id){
        service.deleteBook(id);
        return ResponseEntity.noContent().build();
    }
    @Operation(summary = "Atualiza um Livro existente pelo ID.")
    @PutMapping(value = "/{id}")
    public ResponseEntity<BookDTO> updateBook(
        @PathVariable Integer id,
        @RequestBody BookDTO objDto) {
        BookDTO updatedBook = service.updateBook(id, objDto);
        return ResponseEntity.ok().body(updatedBook);
}
}
