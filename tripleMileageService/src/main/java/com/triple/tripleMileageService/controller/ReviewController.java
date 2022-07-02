package com.triple.tripleMileageService.controller;

import com.triple.tripleMileageService.domain.Place;
import com.triple.tripleMileageService.domain.Review;
import com.triple.tripleMileageService.domain.User;
import com.triple.tripleMileageService.form.ReviewForm;
import com.triple.tripleMileageService.repository.ReviewRepository;
import com.triple.tripleMileageService.service.PlaceService;
import com.triple.tripleMileageService.service.ReviewService;
import com.triple.tripleMileageService.service.UserService;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@Slf4j
@Controller
@RequiredArgsConstructor
public class ReviewController {

    private final ReviewService reviewService;
    private final ReviewRepository reviewRepository;
    private final UserService userService;
    private final PlaceService placeService;


    /**리뷰생성**/

    @GetMapping("/reviews/new")
    public String createForm(Model model) {
        List<User> users = userService.findAllUser();
        List<Place> places = placeService.findAllPlace();
        model.addAttribute("users", users);
        model.addAttribute("places", places);
        model.addAttribute("reviewForm", new ReviewForm());

        return "reviews/createReviewForm";
    }


    @PostMapping("/reviews/new")
    public String createReview(@Valid ReviewForm reviewForm ,
                                BindingResult result,
                                Model model){
        if (result.hasErrors()) {
            model.addAttribute("users", userService.findAllUser());
            model.addAttribute("places", placeService.findAllPlace());
            return "reviews/createReviewForm";
        }

        reviewService.createReview(reviewForm.getContent(), reviewForm.getUserId(), reviewForm.getPlaceId());

        return "redirect:/reviews";

    }




    /**
     * 리뷰 조회
     **/

    @GetMapping("/reviews")
    public String list(Model model) {
        List<Review> reviews = reviewService.findAllReview();
        model.addAttribute("reviews", reviews);
        return "reviews/reviewList";
    }

    /**
     * 리뷰 수정
     **/
    @GetMapping("/reviews/{reviewId}/edit")
    public String modifyReviewForm(@PathVariable("reviewId") String reviewId,
                                   Model model) {
        Review review = reviewService.findReview(reviewId);

        ReviewForm form = new ReviewForm();
        form.setReviewId(review.getUuid());
        form.setUserName(review.getUser().getName());
        form.setPlaceName(review.getPlace().getName());
        form.setContent(review.getContent());
//        form.setPhotoList();

//        System.out.println("=======================");
//        System.out.println("=======================");

        model.addAttribute("reviewForm", form);

        return "reviews/modifyReviewForm";
    }

    @PostMapping("/reviews/{reviewId}/edit")
    public String modifyReviewForm(@PathVariable("reviewId") String reviewId,
                                   @ModelAttribute("reviewForm") ReviewForm reviewForm) {

        reviewRepository.modifyReview(reviewId, reviewForm.getContent());
        return "redirect:/reviews";
    }




}
