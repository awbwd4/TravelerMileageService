package com.triple.tripleMileageService.domain;

import lombok.Getter;

import javax.persistence.*;

@Entity
@Getter
@SequenceGenerator(
        name="POINT_HISTORY_SEQ_GENERATOR",
        sequenceName = "REVIEW_SEQ",
        initialValue = 1,
        allocationSize = 50
)
public class PointHistory {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE,
            generator = "POINT_HISTORY_SEQ_GENERATOR")
    @Column(name = "point_history_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "point_id")
    private Point point;

    @Enumerated(EnumType.STRING)
    private PointDiscriminator discriminator; //CREATE, INCREASE, DECREASE

//    @Column(name = "point_history")
//    private int point_history;



}
