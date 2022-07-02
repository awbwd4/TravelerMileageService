package com.triple.tripleMileageService.controller;

import com.triple.tripleMileageService.domain.Place;
import com.triple.tripleMileageService.form.PlaceForm;
import com.triple.tripleMileageService.service.PlaceService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import javax.validation.Valid;
import java.util.List;

@Slf4j
@Controller
@RequiredArgsConstructor
public class PlaceController {

    private final PlaceService placeService;


    /**
     * 장소 등록
     * **/
    @GetMapping("/places/new")
    public String createForm(Model model) {
        log.info("place create form");
        model.addAttribute("placeForm", new PlaceForm());
        return "places/createPlaceForm";
    }

    @PostMapping("/places/new")
    public String create(@Valid PlaceForm form, BindingResult result) {

        if (result.hasErrors()) {
            return "places/createPlaceForm";
        }
        Place createdPlace = Place.createPlace(form.getName());
        placeService.createPlace(createdPlace);

        return "redirect:/places";
    }

    /**
     * 장소 조회
     **/
    @GetMapping("/places")
    public String list(Model model) {
        List<Place> places = placeService.findAllPlace();
        model.addAttribute("places", places);

        return "places/placeList";
    }


}
