package com.apilivros.apilivros.Services;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Component;

import com.apilivros.apilivros.Domain.Review;
import com.apilivros.apilivros.Domain.pk.ReviewID;
import com.apilivros.apilivros.Repository.ReviewRepository;
import com.apilivros.apilivros.Services.Exceptions.ResourceNotFoundException;

@Component
public class ReviewService {
    
    @Autowired
    private ReviewRepository repository;

    public Review findById(Integer bookId, Integer userId){
        try{
            Review obj = repository.findByBookAndUser(bookId, userId);
            return obj;

        }catch (EmptyResultDataAccessException e){
            throw new ResourceNotFoundException("Um dos id's nao pode ser encontrado.");
        }
    }
    public Review insert(Review obj){
        return repository.save(obj);
    }
    public void delete(Integer bookId, Integer userId){
        repository.deleteByBookAndUser(bookId, userId);
    }

    public Review editReview(Integer bookId, Integer userId, Review obj){
        Review review = repository.findByBookAndUser(bookId, userId);
        reviewUpdate(review, obj);
        return repository.save(review);
    }

    public void reviewUpdate(Review review, Review obj){
        review.setComment(obj.getComment());
        review.setScore(obj.getScore());
    }
}
