package com.triple.tripleMileageService.domain;

import lombok.Getter;

import javax.persistence.*;

@Entity
@Getter
@SequenceGenerator(
        name="POINT_SEQ_GENERATOR",
        sequenceName = "POINT_SEQ",
        initialValue = 1,
        allocationSize = 50
)
public class Point {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE,
                    generator = "POINT_SEQ_GENERATOR"
    )
    @Column(name = "point_id")
    private Long id;

    private int point;

}
