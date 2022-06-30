package com.triple.tripleMileageService.service;


import com.triple.tripleMileageService.domain.Place;
import com.triple.tripleMileageService.domain.Review;
import com.triple.tripleMileageService.domain.User;
import com.triple.tripleMileageService.repository.ReviewRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.lang.reflect.Member;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class ReviewService {

    private final ReviewRepository reviewRepository;


    public String createReview(Review review, User user, Place place) {

        System.out.println("=====ReviewService.createReview=====");

        //리뷰 생성
        Review createdReview = Review.createReview(review.getContent(), user, place);

        return reviewRepository.create(createdReview);
    }

    public Review findReview(String uuid) {
        return reviewRepository.findReview(uuid);
    }



}
