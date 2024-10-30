package com.apilivros.Adapters;

import java.net.URI;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.apilivros.Domain.Author;
import com.apilivros.Dto.AuthorDTO;
import com.apilivros.Dto.Mappers.AuthorMapper;
import com.apilivros.Services.AuthorService;

@RestController
@RequestMapping("/author")
public class AuthorRestController {
    
    @Autowired
    private AuthorService service;

    @GetMapping
    public ResponseEntity<Page<AuthorDTO>> findAll(
        @RequestParam(value = "page", defaultValue = "0") Integer page,
        @RequestParam(value = "size", defaultValue = "12") Integer size,
        @RequestParam(value = "direction", defaultValue = "asc") String direction){
            
            var sortDirection = "desc".equalsIgnoreCase(direction) ? Sort.Direction.DESC : Sort.Direction.ASC;
            Pageable pageable = PageRequest.of(page, size, Sort.by(sortDirection, "username"));
            Page<AuthorDTO> dtoPage = service.findAll(pageable);
            return ResponseEntity.ok(dtoPage);
    }
    @GetMapping(value = "/{id}")
    public ResponseEntity<AuthorDTO> findById(@PathVariable Integer id){
        AuthorDTO dto = service.findById(id);
        return ResponseEntity.ok().body(dto);
    }

    @GetMapping(value = "/username/{username}")
    public ResponseEntity<AuthorDTO> findByAuthorname(@PathVariable String username){
        Author user = service.findByAuthorname(username);
        AuthorDTO dto = new AuthorDTO(user);
        return ResponseEntity.ok().body(dto);
    }

    @PostMapping
    public ResponseEntity<AuthorDTO> insert(@RequestBody AuthorDTO objDTO){
        Author obj = AuthorMapper.fromDTO(objDTO);
        obj = service.insert(obj);
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(obj.getId()).toUri();
        return ResponseEntity.created(uri).body(new AuthorDTO(obj));
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<Void> delete(@PathVariable Integer id){
        service.delete(id);
        return ResponseEntity.noContent().build();
    }

    @PutMapping(value = "/{id}")
    public ResponseEntity<AuthorDTO> update(@PathVariable Integer id, @RequestBody AuthorDTO objDTO){
        Author obj =  AuthorMapper.fromDTO(objDTO);
        obj = service.update(id, obj);
        return ResponseEntity.ok().body(new AuthorDTO(obj));
    }
}
