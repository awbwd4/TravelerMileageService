package com.triple.tripleMileageService.service;


import com.triple.tripleMileageService.domain.Place;
import com.triple.tripleMileageService.repository.PlaceRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class PlaceService {

    private final PlaceRepository placeRepository;

    @Transactional
    public String createPlace(Place place) {

        System.out.println("=======PlaceService.join=====");

        Place createdPlace = Place.createPlace(place.getName());

        return placeRepository.save(createdPlace);

    }

    public Place findPlace(String uuid) {
        return placeRepository.findPlace(uuid);
    }


}
