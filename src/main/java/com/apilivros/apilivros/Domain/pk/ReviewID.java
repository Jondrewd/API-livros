package com.apilivros.apilivros.Domain.pk;


import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;

@Embeddable
public class ReviewID {

    @Column(name = "book_id")
    private Integer bookId;
    @Column(name = "user_id")
    private Integer userId;

    
    public ReviewID() {
    }
    public ReviewID(Integer bookId, Integer userId) {
        this.bookId = bookId;
        this.userId = userId;
    }
    public Integer getBookId() {
        return bookId;
    }
    public void setBookId(Integer bookId) {
        this.bookId = bookId;
    }
    public Integer getUserId() {
        return userId;
    }
    public void setUserId(Integer userId) {
        this.userId = userId;
    }
    
}
