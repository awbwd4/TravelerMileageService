package com.triple.tripleMileageService.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import java.util.UUID;

@Entity
@Getter
@Setter
public class Place {

    @Id
    @Column(name = "place_uuid")
    private String uuid;

    @NotEmpty
    private String name;

    private int review_history_count;
    //달렸던 리뷰의 수(삭제건 포함)

    private Place() {}


    /**=====생성메서드=====**/
    public static Place createPlace(String name) {
        Place place = new Place();
        place.setName(name);
        place.setUuid(UUID.randomUUID().toString());
        place.setName(name);
        return place;
    }

    /**
     * =====비즈니스 로직=====
     **/
    public void increaseReviewCount() {
        this.review_history_count += 1;
    }




}
