package com.triple.triplePointApi.dto;

import lombok.Data;

@Data
public class PointEventResponse {

    private StatusEnum statusEnum;
    private String message;
    private Object data;


}
