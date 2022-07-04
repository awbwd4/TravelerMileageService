package com.triple.triplePointApi.controller;

import com.triple.triplePointApi.exception.NoUserPointDataException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.NoHandlerFoundException;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import javax.persistence.NoResultException;

@ControllerAdvice
@EnableWebMvc
public class ControllerExceptionHandler {

    private final Logger logger = LoggerFactory.getLogger(ControllerExceptionHandler.class);



    //잘못된요청인경우
    @ExceptionHandler(NoHandlerFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    protected ResponseEntity<PointEventResponse> NoHandlerFoundException(NoHandlerFoundException e) {
        logger.error("handleException", e);

        PointEventResponse response2
                = PointEventResponse
                .create()
                .status(HttpStatus.NOT_FOUND.value())
                .message("잘못된 접근입니다.");

        return new ResponseEntity<>(response2, HttpStatus.NOT_FOUND);
    }


    //조회시 사용자 데이터가 없는 경우
    @ExceptionHandler(NullPointerException.class)
    protected ResponseEntity<PointEventResponse> NoUserPointDataException(NullPointerException e) {
        logger.error("handleException", e);

        PointEventResponse response2
                = PointEventResponse
                .create()
                .status(HttpStatus.INTERNAL_SERVER_ERROR.value())
                .message("정보가 없습니다.");

        return new ResponseEntity<>(response2, HttpStatus.INTERNAL_SERVER_ERROR);
    }


    //POST 수행시 결과값이 없는 경우 없는 경우
    @ExceptionHandler(NoResultException.class)
    protected ResponseEntity<PointEventResponse> NoUserPointDataException(NoResultException e) {
        logger.error("handleException", e);

        PointEventResponse response2
                = PointEventResponse
                .create()
                .status(HttpStatus.INTERNAL_SERVER_ERROR.value())
                .message("정보가 없습니다.");

        return new ResponseEntity<>(response2, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    //모든 예외를 핸들링
    @ExceptionHandler(Exception.class)
    protected ResponseEntity<PointEventResponse> handleException(Exception e) {
        logger.error("handleException", e);

        PointEventResponse response2
                = PointEventResponse
                .create()
                .status(HttpStatus.INTERNAL_SERVER_ERROR.value())
                .message(e.toString());

        return new ResponseEntity<>(response2, HttpStatus.INTERNAL_SERVER_ERROR);
    }

}
