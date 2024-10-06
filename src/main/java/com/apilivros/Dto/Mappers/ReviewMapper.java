package com.apilivros.Dto.Mappers;

import com.apilivros.Domain.Review;
import com.apilivros.Dto.ReviewDTO;

public class ReviewMapper {
    public static Review fromDTO(ReviewDTO dto) {
        Review review = new Review();
        review.setId(dto.getBookId(), dto.getUserId());
        review.setComment(dto.getComment());
        review.setScore(dto.getScore());
        return review;
    }
}