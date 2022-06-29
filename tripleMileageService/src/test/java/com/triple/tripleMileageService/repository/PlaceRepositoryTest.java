package com.triple.tripleMileageService.repository;

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
public class PlaceRepositoryTest {

    @Autowired
    PlaceRepository placeRepository;

    @Test
    @Transactional
    @Rollback(value = false)
    public void testSavePlace() {
        //given
        Place place = new Place("placeA");

        //when
        Long savePlace = placeRepository.save(place);
        Place findPlace = placeRepository.findPlace(savePlace);

        //then
        // 장소 아이디 검증
        Assertions.assertThat(findPlace.getId()).isEqualTo(place.getId());
        // 장소 이름 검증
        Assertions.assertThat(findPlace.getName()).isEqualTo(place.getName());

    }



}