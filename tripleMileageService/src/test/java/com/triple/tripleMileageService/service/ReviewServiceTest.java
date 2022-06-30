package com.triple.tripleMileageService.service;

import com.triple.tripleMileageService.domain.Place;
import com.triple.tripleMileageService.domain.Review;
import com.triple.tripleMileageService.domain.User;
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
    @Autowired
    UserService userService;

    @Autowired
    PlaceService placeService;
//
//    @Test
//    @Transactional
//    @Rollback(value = false)
//    public void testCreateReview() {
//        //givien
//
//        User findUser = userService.findUser(userService.join(new User("joinUserA")));
//        Place findPlace = placeService.findPlace(placeService.createPlace(new Place("placeA")));
//
//        Review review = new Review("reviewContentA");
//
//        //when
//        Long createdReviewId = reviewService.createReview(review, findUser, findPlace);
//        Review findReview = reviewService.findReview(createdReviewId);
//
//        //then
//
//        //리뷰 검증
//        Assertions.assertThat(createdReviewId).isEqualTo(findReview.getId());
//        //리뷰 작성자 검증
//        Assertions.assertThat(findReview.getUser().getName()).isEqualTo(findUser.getName());
//        //리뷰 대상 장소 검증
//        Assertions.assertThat(findReview.getPlace().getName()).isEqualTo(findPlace.getName());
//
//    }
//


}