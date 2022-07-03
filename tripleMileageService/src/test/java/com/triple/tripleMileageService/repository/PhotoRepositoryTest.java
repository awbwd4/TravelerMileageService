package com.triple.tripleMileageService.repository;

import com.triple.tripleMileageService.domain.Photo;
import com.triple.tripleMileageService.domain.Review;
import org.assertj.core.api.Assertions;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.annotation.ReadOnlyProperty;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

import static org.junit.Assert.*;


@RunWith(SpringRunner.class)
@SpringBootTest
public class PhotoRepositoryTest {

    @Autowired
    PhotoRepository photoRepository;
    @Autowired
    ReviewRepository reviewRepository;

    @PersistenceContext
    EntityManager em;

    @Test
    @Transactional
    @Rollback(value = false)
    public void 사진저장() {

        //given
        String reviewId = "4d6a8d23-efa5-4710-bc41-7d6e5e9132f6";
        Review review = reviewRepository.findReview(reviewId);

        Photo photo1 = Photo.createPhoto(review, "original4", "stored1", 100);
        Photo photo2 = Photo.createPhoto(review, "original5", "stored2", 100);
        Photo photo3 = Photo.createPhoto(review, "original6", "stored3", 100);


        //when
        photoRepository.save(photo1);
        photoRepository.save(photo2);
        photoRepository.save(photo3);

        review.getPhotos().add(photo1);
        review.getPhotos().add(photo2);
        review.getPhotos().add(photo3);

    }


    @Test
    @Transactional
//    @Rollback(value = false)
    public void 사진아이디로검색() {
        //given
        String reviewId = "4d6a8d23-efa5-4710-bc41-7d6e5e9132f6";
        Review review = reviewRepository.findReview(reviewId);


        Photo photo = Photo.createPhoto(review, "original", "stored", 100);



        photoRepository.save(photo);

        //when
        Photo photoById = photoRepository.findByPhotoId("photoId");


        //then
        Assertions.assertThat(photoById.getPhotoId()).isEqualTo(photo.getPhotoId());



    }



    @Test
    @Transactional
//    @Rollback(value = false)
    public void 리뷰아이디로검색() {
        //given
        String reviewId = "4d6a8d23-efa5-4710-bc41-7d6e5e9132f6";
        Review review = reviewRepository.findReview(reviewId);

        //when
        List<Photo> photoByReviewId = photoRepository.findByReviewUuid(review.getUuid());


        //then
        for (Photo photo1 : photoByReviewId) {
            System.out.println("photo1.getPhotoId() = " + photo1.getPhotoId());
        }
    }

    @Test
    @Transactional
    @Rollback
    public void 테스트() {

    }




}