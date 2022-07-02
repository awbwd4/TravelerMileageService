package com.triple.tripleMileageService.service;

import com.triple.tripleMileageService.domain.Place;
import com.triple.tripleMileageService.domain.Review;
import com.triple.tripleMileageService.domain.User;
import com.triple.tripleMileageService.repository.ReviewRepository;
import com.triple.tripleMileageService.repository.UserRepository;
import org.assertj.core.api.Assertions;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.Assert.*;
@RunWith(SpringRunner.class)
@SpringBootTest
public class ReviewServiceTest {

    @Autowired
    ReviewService reviewService;
    ReviewRepository reviewRepository;
    UserService userService;
    PlaceService placeService;

    @Test
    @Transactional
    @Rollback(value = false)
    public void testCreateReview() {
        //given
        String reviewContent = "reviewB";
        String userUuid = "30445233-a198-42f7-bee9-456cf0b963d6";
        String placeUuid = "df621453-537c-425e-a11d-6f0542a15bba";
        //user와 place테이블에 데이터가 있어야 테스트 가능.

        //when
        String createdReviewId = reviewService.createReview(reviewContent, userUuid, placeUuid);
        Review findReview = reviewService.findReview(createdReviewId);

        //then
        //리뷰 검증
        Assertions.assertThat(createdReviewId).isEqualTo(findReview.getUuid());
        //리뷰 작성자 검증
        Assertions.assertThat(findReview.getUser().getUuid()).isEqualTo(userUuid);
        //리뷰 대상 장소 검증
        Assertions.assertThat(findReview.getPlace().getUuid()).isEqualTo(placeUuid);

    }


    @Test
    @Transactional
    @Rollback(value = false)
    public void 리뷰수정테스트() {
        //given
        String reviewContent = "reviewC";
        String reviewId = "e850b960-bbdd-47b5-b039-5da640e71008";

        //when
        Review review = reviewRepository.modifyReview(reviewId, reviewContent);

        //then
        //리뷰 검증
        Assertions.assertThat(review.getContent()).isEqualTo(reviewContent);

    }

    @Test
    @Transactional
    @Rollback(value = false)
    public void 리뷰검색테스트() {
        String reviewId = "e850b960-bbdd-47b5-b039-5da640e71008";
        Review review = reviewService.findReview(reviewId);

        System.out.println("review.getUser().getName() = " + review.getUser().getName());

    }



}