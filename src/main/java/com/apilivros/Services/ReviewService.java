package com.apilivros.Services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import com.apilivros.Domain.Review;
import com.apilivros.Dto.ReviewDTO;
import com.apilivros.Dto.Mappers.ReviewMapper;
import com.apilivros.Repository.ReviewRepository;
import com.apilivros.Services.Exceptions.ResourceNotFoundException;

@Service
public class ReviewService {
    
    @Autowired
    private ReviewRepository repository;

    public ReviewDTO findById(Integer bookId, Integer userId){
        try{
            Review review = repository.findByBookAndUser(bookId, userId);
            return ReviewMapper.convertToDTO(review);
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
    
    public Review fromDTO(ReviewDTO dto) {
        return ReviewMapper.fromDTO(dto);
    }
 
}
