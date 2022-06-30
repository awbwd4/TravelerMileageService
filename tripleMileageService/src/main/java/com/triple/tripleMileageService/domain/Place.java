package com.triple.tripleMileageService.domain;

import lombok.Getter;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;

@Entity
@Getter
@SequenceGenerator(
        name="PLACE_SEQ_GENERATOR",
        sequenceName = "PLACE_SEQ",
        initialValue = 1,
        allocationSize = 50
)
public class Place {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE,
            generator = "PLACE_SEQ_GENERATOR")
    @Column(name = "place_id")
    private Long id;

    @NotEmpty
    private String name;

    public Place(String name) {
        this.name = name;
    }
}
