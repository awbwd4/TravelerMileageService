package com.triple.tripleMileageService.domain;


import lombok.Getter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.validation.constraints.NotEmpty;

@Entity
@Getter
public class Review {

    @Id
    @GeneratedValue
    @Column(name="review_id")
    private Long id;

    @NotEmpty
    private String content;

    private String photo; // photo 객체 필요

    public Review(String content) {
        this.content = content;
    }
}
