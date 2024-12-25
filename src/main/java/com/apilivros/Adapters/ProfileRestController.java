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

import com.apilivros.Domain.Profile;
import com.apilivros.Dto.ProfileDTO;
import com.apilivros.Services.ProfileService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

@Tag(name = "Endpoints de Perfil")
@RestController
@RequestMapping("/profile")
public class ProfileRestController {

    @Autowired
    private ProfileService service;

    @Operation(summary = "Retorna uma lista paginada de perfis.")
    @GetMapping
    public ResponseEntity<Page<ProfileDTO>> findAll(
        @RequestParam(value = "page", defaultValue = "0") Integer page,
        @RequestParam(value = "size", defaultValue = "12") Integer size,
        @RequestParam(value = "direction", defaultValue = "asc") String direction){

            var sortDirection = "desc".equalsIgnoreCase(direction) ? Sort.Direction.DESC : Sort.Direction.ASC;
            Pageable pageable = PageRequest.of(page, size, Sort.by(sortDirection, "username"));
            Page<ProfileDTO> dtoPage = service.findAll(pageable);
            return ResponseEntity.ok(dtoPage);
    }

    @Operation(summary = "Retorna um perfil pelo seu ID.")
    @GetMapping(value = "/{id}")
    public ResponseEntity<ProfileDTO> findById(@PathVariable Integer id){
        ProfileDTO dto = service.findById(id);
        return ResponseEntity.ok().body(dto);
    }

    @Operation(summary = "Retorna um perfil pelo nome de usuário.")
    @GetMapping(value = "/username/{username}")
    public ResponseEntity<ProfileDTO> findByProfilename(@PathVariable String username){
        Profile user = service.findByProfilename(username);
        ProfileDTO dto = new ProfileDTO(user);
        return ResponseEntity.ok().body(dto);
    }

    @Operation(summary = "Cria um novo perfil.")
    @PostMapping
    public ResponseEntity<ProfileDTO> insert(@RequestBody ProfileDTO objDTO){
        ProfileDTO savedProfile = service.insert(objDTO);
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
            .buildAndExpand(savedProfile.getId()).toUri();
        return ResponseEntity.created(uri).body(savedProfile);
    }

    @Operation(summary = "Deleta um perfil pelo seu ID.")
    @DeleteMapping(value = "/{id}")
    public ResponseEntity<Void> delete(@PathVariable Integer id){
        service.delete(id);
        return ResponseEntity.noContent().build();
    }

    @Operation(summary = "Atualiza um perfil existente.")
    @PutMapping(value = "/{id}")
    public ResponseEntity<ProfileDTO> update(@PathVariable Integer id, @RequestBody ProfileDTO objDTO){
        ProfileDTO obj = service.update(id, objDTO);
        return ResponseEntity.ok().body(obj);
    }
}
