package com.triple.triplePointApi.controller;

import lombok.Data;

@Data
public class PointEventResponse {

    //    private StatusEnum statusEnum;
    private int status;
    private String message;
    private Object data;


    static public PointEventResponse create() {
        return new PointEventResponse();
    }

    public PointEventResponse status(int status) {
        this.status = status;
        return this;
    }

    public PointEventResponse message(String message) {
        this.message = message;
        return this;
    }
    public PointEventResponse data(Object data) {
        this.data = data;
        return this;
    }



}
