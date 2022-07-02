package com.triple.tripleMileageService.repository;

import com.triple.tripleMileageService.domain.Photo;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PhotoRepository extends JpaRepository<Photo, String> {

    Photo save(Photo photo);

    Photo findByPhotoId(String photoId);

    List<Photo> findByReviewUuid(String reviewId);


}
