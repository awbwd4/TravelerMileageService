package com.triple.triplePointApi.controller;


import com.triple.triplePointApi.dto.PointEventRequest;
import com.triple.triplePointApi.dto.StatusEnum;
import com.triple.triplePointApi.dto.UserPoint;
import com.triple.triplePointApi.service.PointService;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.nio.charset.Charset;

@Slf4j
@RestController
@RequiredArgsConstructor
public class PointController {

    private final PointService pointService;

    /**
     * 포인트 조회
     **/
    @GetMapping(path="/points/{userId}")
    public ResponseEntity<PointEventResponse> userPoint1(@PathVariable String userId) {

        int pointByUserId = pointService.getPointByUserId(userId);
        UserPoint userPoint = new UserPoint(userId, pointByUserId);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(new MediaType("application", "json", Charset.forName("UTF-8")));

        PointEventResponse response = new PointEventResponse();
        response.setStatus(200);
        response.setMessage("성공");
        response.setData(userPoint);

        return new ResponseEntity<>(response, headers, HttpStatus.OK);

    }

    /**
     * 포인트 생성(신규가입자) / 추가(리뷰등록시) / 수정 / 삭제 시
     **/
    @PostMapping("/events")
    public ResponseEntity<PointEventResponse>  pointEvents(@RequestBody @Valid PointEventRequest request) {
        //포인트 생성시(신규가입)
        if (request.getAction().equals("NEW")) {
            log.info("===============POST /events : NEW============");
            pointService.createNewUserPoint(request.getUserId());
        }
        //포인트 추가시(리뷰등록)
        else if (request.getAction().equals("ADD")) {
            log.info("===============POST /events : ADD====={}=======", request.getAttachedPhotoIds());
            pointService.addReviewPoint(request.getUserId(), request.getPlaceId(), request.getAttachedPhotoIds());
        }
        //포인트 수정시(리뷰 수정)
        else if (request.getAction().equals("MOD")) {
            log.info("===============POST /events : MOD============");
            pointService.modReviewPoint(request.getUserId(), request.getPlaceId(), request.getAttachedPhotoIds());
        }
        //포인트 삭제시(리뷰 삭제)
        else if (request.getAction().equals("DELETE")) {
            log.info("===============POST /events : DELETE============");
            pointService.deleteReview(request.getUserId(), request.getPlaceId());
        }

        log.info("===============응답하기=======기====");
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(new MediaType("application", "json", Charset.forName("UTF-8")));

        PointEventResponse response = new PointEventResponse();
        response.setStatus(200);
        response.setMessage("성공");
//        response.setData(userPoint);

        return new ResponseEntity<>(response, headers, HttpStatus.OK);
    }
}

