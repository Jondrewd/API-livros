package com.apilivros.apilivros.Services;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Component;

import com.apilivros.apilivros.Domain.Review;
import com.apilivros.apilivros.Repository.ReviewRepository;
import com.apilivros.apilivros.Services.Exceptions.ResourceNotFoundException;

@Component
public class ReviewService {
    
    @Autowired
    private ReviewRepository repository;

    public Review findById(Integer id){
        try{
            Optional<Review> obj = repository.findById(id);
            return obj.get();

        }catch (EmptyResultDataAccessException e){
            throw new ResourceNotFoundException(id);
        }
    }
    public Review insert(Review obj){
        return repository.save(obj);
    }
    public void delete(Integer id){
        findById(id);
        repository.deleteById(id);
    }

    public Review editReview(Integer id, Review obj){
        Review review = findById(id);
        reviewUpdate(review, obj);
        return repository.save(review);
    }

    public void reviewUpdate(Review review, Review obj){
        review.setComment(obj.getComment());
        review.setScore(obj.getScore());
    }
}
