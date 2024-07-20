package com.apilivros.apilivros.Adapters;

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

import com.apilivros.apilivros.Domain.Review;
import com.apilivros.apilivros.Services.ReviewService;

@RestController
@RequestMapping("/reviews")
public class ReviewRestController {
    
    @Autowired
    private ReviewService reviewService;

    @GetMapping(value = "/{id}")
    public ResponseEntity<Review> findById(@PathVariable Integer id){
        Review review = reviewService.findById(id);
        return ResponseEntity.ok().body(review);
    }
    @PostMapping
    public ResponseEntity<Review> insert(@RequestBody Review obj){
        obj = reviewService.insert(obj);
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(obj.getId()).toUri();
        return ResponseEntity.created(uri).body(obj);
    }
    @DeleteMapping(value = "/{id}")
    public ResponseEntity<Void> delete(@PathVariable Integer id){
        reviewService.delete(id);
        return ResponseEntity.noContent().build();
    } 
    @PutMapping(value = "/{id}")
    public ResponseEntity<Review> update(@PathVariable Integer id, @RequestBody Review obj){
        obj = reviewService.editReview(id, obj);
        return ResponseEntity.ok().body(obj);
    }
    
}
