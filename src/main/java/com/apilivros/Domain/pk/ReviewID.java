package com.apilivros.Domain.pk;


import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;

@Embeddable
public class ReviewID {

    @Column(name = "book_id")
    private Long bookId;
    @Column(name = "profile_id")
    private Long profileId;

    
    public ReviewID() {
    }
    public ReviewID(Long bookId, Long profileId) {
        this.bookId = bookId;
        this.profileId = profileId;
    }
    public Long getBookId() {
        return bookId;
    }
    public void setBookId(Long bookId) {
        this.bookId = bookId;
    }
    public Long getUserId() {
        return profileId;
    }
    public void setUserId(Long profileId) {
        this.profileId = profileId;
    }
    
}
