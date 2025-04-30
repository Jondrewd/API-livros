package com.apilivros.Adapters;

import java.net.URI;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.apilivros.Domain.Review;
import com.apilivros.Dto.ReviewDTO;
import com.apilivros.Services.ReviewService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

@Tag(name = "Endpoints de Avaliações")
@RestController
@RequestMapping("/reviews")
public class ReviewRestController {
    
    @Autowired
    private ReviewService reviewService;

    @Operation(summary = "Retorna uma avaliação de um livro por usuário.")
    @GetMapping(value = "/{book_id}/{reviews_id}")
    public ResponseEntity<ReviewDTO> findById(@PathVariable Long book_id, @PathVariable Long reviews_id) {
        ReviewDTO dto = reviewService.findById(book_id, reviews_id);
        return ResponseEntity.ok().body(dto);
    }

    @Operation(summary = "Registra uma nova avaliação.")
    @PostMapping
    public ResponseEntity<?> insert(@RequestBody ReviewDTO obj) {
        reviewService.insert(obj);
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(obj.getProfileId()).toUri();
        return ResponseEntity.created(uri).body(obj);
    }

    @Operation(summary = "Deleta uma avaliação de um livro por usuário.")
    @DeleteMapping(value = "/{book_id}/{reviews_id}")
    public ResponseEntity<Void> delete(@PathVariable Long book_id, @PathVariable Long reviews_id) {
        reviewService.delete(book_id, reviews_id);
        return ResponseEntity.noContent().build();
    }

    @Operation(summary = "Atualiza uma avaliação existente.")
    @PutMapping(value = "/{book_id}/{reviews_id}")
    public ResponseEntity<ReviewDTO> update(@PathVariable Long book_id, @PathVariable Long reviews_id, @RequestBody ReviewDTO objDTO) {
        Review obj = reviewService.fromDTO(objDTO);
        obj = reviewService.editReview(book_id ,reviews_id, obj);
        return ResponseEntity.ok().body(new ReviewDTO(obj));
    }
}
