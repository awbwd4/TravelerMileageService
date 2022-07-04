package com.triple.triplePointApi.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class UserPoint {

    private String userId;
    private int point;

}
