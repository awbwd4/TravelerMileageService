package com.triple.pointApi.dto;

import lombok.Data;

import java.util.List;

@Data
public class PointInfoDto {

    String type;
    String action;
    String reviewId;
    String content;
    List<String> photos;
    String userId;
    String placeId;

}
