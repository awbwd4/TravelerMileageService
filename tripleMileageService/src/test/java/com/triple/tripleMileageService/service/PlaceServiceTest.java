package com.triple.tripleMileageService.service;

import com.triple.tripleMileageService.domain.Place;
import org.assertj.core.api.Assertions;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.Assert.*;


@RunWith(SpringRunner.class)
@SpringBootTest
public class PlaceServiceTest {

    @Autowired
    PlaceService placeService;


    @Test
    @Transactional
    @Rollback(value = false)
    public void createPlace() {
        //given
        Place place = Place.createPlace("placeA");

        //when
        String createdPlaceId = placeService.createPlace(place);
        Place findPlace = placeService.findPlace(createdPlaceId);

        //then
        Assertions.assertThat(createdPlaceId).isEqualTo(findPlace.getUuid());
        Assertions.assertThat(findPlace.getName()).isEqualTo(place.getName());
    }


}