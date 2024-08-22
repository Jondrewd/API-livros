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
import com.apilivros.Services.ReviewService;

@RestController
@RequestMapping("/reviews")
public class ReviewRestController {
    
    @Autowired
    private ReviewService reviewService;

    @GetMapping(value = "/{book_id}/{user_id}")
    public ResponseEntity<Review> findById(@PathVariable Integer book_id, @PathVariable Integer user_id){
        Review review = reviewService.findById(book_id, user_id);
        return ResponseEntity.ok().body(review);
    }
    @PostMapping
    public ResponseEntity<Review> insert(@RequestBody Review obj){
        obj = reviewService.insert(obj);
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(obj.getId()).toUri();
        return ResponseEntity.created(uri).body(obj);
    }
    @DeleteMapping(value = "/{book_id}/{user_id}")
    public ResponseEntity<Void> delete(@PathVariable Integer book_id, @PathVariable Integer user_id){
        reviewService.delete(book_id, user_id);
        return ResponseEntity.noContent().build();
    } 
    @PutMapping(value = "/{book_id}/{user_id}")
    public ResponseEntity<Review> update(@PathVariable Integer book_id, @PathVariable Integer user_id, @RequestBody Review obj){
        obj = reviewService.editReview(book_id, user_id, obj);
        return ResponseEntity.ok().body(obj);
    }
    
}
