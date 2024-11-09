package com.apilivros.Dto.Mappers;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

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
    public static List<ReviewDTO> convertReviewsToDTO(List<Review> reviews) {
        if (reviews == null) {
            return new ArrayList<>();
        }
        return reviews
                .stream()
                .map(ReviewDTO::new) 
                .collect(Collectors.toList());
    }
    public static ReviewDTO convertToDTO(Review review) {
        return new ReviewDTO(review);
    }
}