package com.triple.tripleMileageService.repository;

import com.triple.tripleMileageService.domain.Photo;
import org.assertj.core.api.Assertions;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.annotation.ReadOnlyProperty;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.junit.Assert.*;


@RunWith(SpringRunner.class)
@SpringBootTest
public class PhotoRepositoryTest {

    @Autowired
    PhotoRepository photoRepository;

//
//    @Test
//    @Transactional
//    @Rollback(value = false)
//    public void 사진저장() {
//
//        Photo photo = new Photo();
//        photo.setPhotoId("photoId");
//        photo.setReviewId("reviewId");
//        photo.setOriginalPhotoName("original");
//        photo.setStoredPhotoPath("stored");
//
//        photoRepository.save(photo);
//
//    }


//    @Test
//    @Transactional
////    @Rollback(value = false)
//    public void 사진아이디로검색() {
//        //given
//        Photo photo = new Photo();
//        photo.setPhotoId("photoId");
////        photo.setReviewId("reviewId");
//        photo.setOriginalPhotoName("original");
//        photo.setStoredPhotoPath("stored");
//
//        photoRepository.save(photo);
//
//        //when
//        Photo photoById = photoRepository.findByPhotoId("photoId");
//
//
//        //then
//        Assertions.assertThat(photoById.getPhotoId()).isEqualTo(photo.getPhotoId());
//
//
//
//    }


//
//    @Test
//    @Transactional
////    @Rollback(value = false)
//    public void 리뷰아이디로검색() {
//        //given
//        Photo photo = new Photo();
//        photo.setPhotoId("photoId");
//        photo.setReviewId("reviewId");
//        photo.setOriginalPhotoName("original");
//        photo.setStoredPhotoPath("stored");
//
//        photoRepository.save(photo);
//
//        //when
//        List<Photo> photoByReviewId = photoRepository.findByReviewId("reviewId");
//
//
//        //then
//        for (Photo photo1 : photoByReviewId) {
//            System.out.println("photo1.getPhotoId() = " + photo1.getPhotoId());
//        }
//    }

    @Test
    @Transactional
    @Rollback
    public void 테스트() {

    }




}