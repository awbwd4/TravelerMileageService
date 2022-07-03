package com.triple.triplePointApi.controller;


import com.triple.triplePointApi.dto.PointEventRequest;
import com.triple.triplePointApi.service.PointOuterService;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@Slf4j
@RestController
@RequiredArgsConstructor
public class PointController {

    private final PointOuterService pointOuterService;


    @GetMapping("/posts")
    public String get() {
        return "hello json";
    }

    @PostMapping("/post")
    public PostResponse post(@RequestBody PointEventRequest request) {
        String content = request.getContent();
        log.info("==============================");

        System.out.println(request.toString());

        System.out.println(request.getAction().getClass().getName());
        String action = request.getAction();

        if (request.getAction().equals("NEW")) {

            String newUserPointId =   pointOuterService.createNewUserPoint(request.getUserId());
            return new PostResponse(newUserPointId);
        }
        log.info("==============================");
        return new PostResponse(content);
    }

    @Data
    static class PostResponse{
        private String a;

        public PostResponse(String a) {
            this.a = a;
        }
    }




    /**
     * 포인트 조회
     * **/
//    @GetMapping("/points")


    /**
     * 포인트 생성(신규가입자) / 추가(리뷰등록시) / 수정 / 삭제 시
     **/
    @PostMapping("/events")
    public void pointEvents(@RequestBody @Valid PointEventRequest request) {
        //포인트 생성시(신규가입)
        if (request.getAction().equals("NEW")) {
            log.info("===============POST /events : NEW============");
            pointOuterService.createNewUserPoint(request.getUserId());
        }
        //포인트 추가시(리뷰등록)
        else if (request.getAction().equals("ADD")) {
            log.info("===============POST /events : ADD============");
            pointOuterService.addReviewPoint(request.getUserId(), request.getPlaceId(), request.getAttachedPhotoIds());
        }
        //포인트 수정시(리뷰 수정)
        else if (request.getAction().equals("MOD")) {
            log.info("===============POST /events : MOD============");
            pointOuterService.modReviewPoint(request.getUserId(), request.getPlaceId(), request.getAttachedPhotoIds());
        }
        //포인트 삭제시(리뷰 삭제)
        else if (request.getAction().equals("DELETE")) {
            log.info("===============POST /events : DELETE============");
            pointOuterService.deleteReview(request.getUserId(), request.getPlaceId());
        }
    }



}
