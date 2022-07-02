package com.triple.tripleMileageService.service;


import com.triple.tripleMileageService.domain.Place;
import com.triple.tripleMileageService.domain.Review;
import com.triple.tripleMileageService.domain.User;
import com.triple.tripleMileageService.repository.PlaceRepository;
import com.triple.tripleMileageService.repository.ReviewRepository;
import com.triple.tripleMileageService.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.lang.reflect.Member;
import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class ReviewService {

    private final ReviewRepository reviewRepository;
    private final UserRepository userRepository;
    private final PlaceRepository placeRepository;


    @Transactional
    public String createReview(String reviewContent, String userUuid, String placeUuid) {

        System.out.println("=====ReviewService.createReview=====");
        // 사용자, 장소 조회
        User user = userRepository.findUser(userUuid);
        Place place = placeRepository.findPlace(placeUuid);

        //리뷰 생성
        Review createdReview = Review.createReview(reviewContent, user, place);

        return reviewRepository.create(createdReview);
    }

    public Review findReview(String uuid) {
        return reviewRepository.findReview(uuid);
    }

    public List<Review> findAllReview() {
        return reviewRepository.findAll();
    }

//
//    public Review modifyReview(String reviewId, String content) {
//
//
//
//        return findReview;
//    }
}
