package com.triple.dto;

import lombok.Data;
import lombok.ToString;

import java.util.List;

@Data
@ToString
public class PointEventRequest {//이벤트 관련 요청용 DTO

    private String type; //REVIEW
    private String action; //NEW, ADD, MOD, DELETE
    private String reviewId;
    private String content;
    private List<String> attachedPhotoIds;//첨부된 이미지의 id 배열값
    private String userId;
    private String placeId;


}


