package com.triple.tripleMileageService.repository;


import com.triple.tripleMileageService.domain.Place;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Repository
@RequiredArgsConstructor
public class PlaceRepository {

    @PersistenceContext
    private EntityManager em;

    // 장소 등록
    public Long save(Place place) {
        System.out.println("=======PlaceRepository.save()=======");
        em.persist(place);
        return place.getId();
    }


    // 장소 조회
    public Place findPlace(Long id) {
        System.out.println("=======PlaceRepository.findMember()=======");
        return em.find(Place.class, id);
    }



}
