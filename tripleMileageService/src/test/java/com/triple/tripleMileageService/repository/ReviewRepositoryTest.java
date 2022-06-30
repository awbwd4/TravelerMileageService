package com.triple.tripleMileageService.repository;

import com.triple.tripleMileageService.domain.Place;
import com.triple.tripleMileageService.domain.Review;
import com.triple.tripleMileageService.domain.User;
import org.assertj.core.api.Assertions;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;


@RunWith(SpringRunner.class)
@SpringBootTest
public class ReviewRepositoryTest {

    @Autowired
    ReviewRepository reviewRepository;

    @Test
    @Transactional
    @Rollback(value = false)
    public void testCreateReview() {
        //given
        User user = User.createUser("userA", "pointId");
        Place place = Place.createPlace("placeA");

        Review review = Review.createReview("reviewContent", user, place);

        //when
        String createdReview = reviewRepository.create(review);
        Review findReview = reviewRepository.findReview(createdReview);

//        //then
//        // 1차 캐시내 엔티티 검증
        Assertions.assertThat(findReview).isEqualTo(review);
        // 리뷰 아이디 검증
        Assertions.assertThat(findReview.getUuid()).isEqualTo(review.getUuid());
        // 리뷰 내용 검증
        Assertions.assertThat(findReview.getContent()).isEqualTo(review.getContent());


    }



}