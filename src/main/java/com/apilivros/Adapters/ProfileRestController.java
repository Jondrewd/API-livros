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

import com.apilivros.Dto.ProfileDTO;
import com.apilivros.Dto.ProfileEditDTO;
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
    public ResponseEntity<ProfileDTO> findById(@PathVariable Long id){
        ProfileDTO dto = service.findById(id);
        return ResponseEntity.ok().body(dto);
    }

    @Operation(summary = "Retorna um perfil pelo nome de usuário.")
    @GetMapping(value = "/username/{username}")
    public ResponseEntity<ProfileDTO> findByusername(@PathVariable String username){
        ProfileDTO dto = service.findByusername(username);
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
    public ResponseEntity<Void> delete(@PathVariable Long id){
        service.delete(id);
        return ResponseEntity.noContent().build();
    }

    @Operation(summary = "Atualiza um perfil existente.")
    @PutMapping(value = "/{id}")
    public ResponseEntity<ProfileDTO> update(@PathVariable Long id, @RequestBody ProfileEditDTO objDTO){
        ProfileDTO obj = service.update(id, objDTO);
        return ResponseEntity.ok().body(obj);
    }

    @Operation(summary = "Permite que um perfil siga outro.")
    @PutMapping(value = "/{idFollower}/follow/{idProfile}")
    public ResponseEntity<String> follow(
            @PathVariable Long idFollower, 
            @PathVariable Long idProfile) {
        service.follow(idFollower, idProfile);
        return ResponseEntity.ok("Perfil com ID " + idFollower + " agora segue o perfil com ID " + idProfile);
    }

    @Operation(summary = "Faz com que um perfil deixe de seguir outro.")
    @DeleteMapping(value = "/{idFollower}/follow/{idProfile}")
    public ResponseEntity<String> unfollow(
            @PathVariable Long idFollower, 
            @PathVariable Long idProfile) {
        service.unfollow(idFollower, idProfile);
        return ResponseEntity.ok("Perfil com ID " + idFollower + " deixou de seguir " + idProfile);
    }

    @Operation(summary = "Adiciona um livro a WishList de um perfil")
    @PutMapping(value = "/{profileId}/wishlist/{bookId}")
    public ResponseEntity<String> addToWishlist(
            @PathVariable Long profileId,
            @PathVariable Long bookId) {
        service.addToWishlist(profileId, bookId);
        return ResponseEntity.ok("Livro com ID " + bookId + " adicionado à wishlist do perfil com ID " + profileId);
    }

    @Operation(summary = "Remove um livro da WishList de um perfil")
    @DeleteMapping(value = "/{profileId}/wishlist/{bookId}")
    public ResponseEntity<String> removeFromWishlist(
            @PathVariable Long profileId,
            @PathVariable Long bookId) {
        service.removeFromWishlist(profileId, bookId);
        return ResponseEntity.ok("Livro com ID " + bookId + " removido da wishlist do perfil com ID " + profileId);
    }

    @Operation(summary = "Adiciona um livro da Lista de favoritos de um perfil")
    @PutMapping(value = "/{profileId}/favorites/{bookId}")
    public ResponseEntity<String> addToFavorites(
            @PathVariable Long profileId,
            @PathVariable Long bookId) {
        service.addToFavorites(profileId, bookId);
        return ResponseEntity.ok("Livro com ID " + bookId + " adicionado aos favoritos do perfil com ID " + profileId);
    }

    @Operation(summary = "Remove um livro da Lista de favoritos de um perfil")
    @DeleteMapping(value = "/{profileId}/favorites/{bookId}")
    public ResponseEntity<String> removeFromFavorites(
            @PathVariable Long profileId,
            @PathVariable Long bookId) {
        service.removeFromFavorites(profileId, bookId);
        return ResponseEntity.ok("Livro com ID " + bookId + " removido dos favoritos do perfil com ID " + profileId);
    }

}
