package com.triple.pointApi.domain;

import lombok.Getter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.validation.constraints.NotEmpty;

@Entity
@Getter
public class Point {

    @Id
    @GeneratedValue
    @Column(name = "point_id")
    private Long id;

    private int point;


}
