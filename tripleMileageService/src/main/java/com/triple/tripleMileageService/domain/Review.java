package com.triple.tripleMileageService.domain;


import lombok.Getter;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;

@Entity
@Getter
@SequenceGenerator(
        name="REVIEW_SEQ_GENERATOR",
        sequenceName = "REVIEW_SEQ",
        initialValue = 1,
        allocationSize = 50
)
public class Review {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE,
            generator = "REVIEW_SEQ_GENERATOR")
    @Column(name="review_id")
    private Long id;

    @NotEmpty
    private String content;

    private String photo; // photo 객체 필요

    public Review(String content) {
        this.content = content;
    }
}
