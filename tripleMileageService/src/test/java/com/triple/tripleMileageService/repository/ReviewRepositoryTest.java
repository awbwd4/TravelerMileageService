package com.triple.tripleMileageService.repository;

import com.triple.tripleMileageService.domain.Review;
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
    public void testSaveReview() {
        //given
        Review review = new Review("reviewA");
        //when
        Long saveMember = reviewRepository.save(review);
        Review findReview = reviewRepository.findReview(saveMember);


        //then
        // 리뷰 아이디 검증
        Assertions.assertThat(findReview.getId()).isEqualTo(review.getId());
        // 리뷰 내용 검증
        Assertions.assertThat(findReview.getContent()).isEqualTo(review.getContent());


    }



}