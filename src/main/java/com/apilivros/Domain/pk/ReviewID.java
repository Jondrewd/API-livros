package com.apilivros.Domain.pk;


import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;

@Embeddable
public class ReviewID {

    @Column(name = "book_id")
    private Long bookId;
    @Column(name = "user_id")
    private Long userId;

    
    public ReviewID() {
    }
    public ReviewID(Long bookId, Long userId) {
        this.bookId = bookId;
        this.userId = userId;
    }
    public Long getBookId() {
        return bookId;
    }
    public void setBookId(Long bookId) {
        this.bookId = bookId;
    }
    public Long getUserId() {
        return userId;
    }
    public void setUserId(Long userId) {
        this.userId = userId;
    }
    
}
