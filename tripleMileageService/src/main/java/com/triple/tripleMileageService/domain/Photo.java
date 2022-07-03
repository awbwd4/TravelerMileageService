package com.triple.tripleMileageService.domain;

import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.UUID;


@Getter @Setter
@Entity
public class Photo {


    @Id
    @Column(name = "photo_id")
    private String photoId;


    @NotNull
    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "review_id")
    private Review review;

    @NotEmpty
    private String originalPhotoName;
    @NotEmpty
    private String storedPhotoPath;

    private long photoSize;


    //==생성메서드==//
    public static Photo createPhoto(Review review, String originalPhotoName, String storedPhotoPath, long photoSize) {
        Photo photo = new Photo();
        photo.setPhotoId(UUID.randomUUID().toString());
        photo.setReview(review);
        photo.setOriginalPhotoName(originalPhotoName);
        photo.setStoredPhotoPath(storedPhotoPath);
        photo.setPhotoSize(photoSize);
//        review.getPhotos().add(photo);
        return photo;
    }

    //==연관관계 메서드 : 사진 첨부시 사용==//
    public void addReview(Review review) {
        this.review = review;
        review.getPhotos().add(this);
    }
}
