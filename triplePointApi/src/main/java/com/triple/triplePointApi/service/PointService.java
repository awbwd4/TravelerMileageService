package com.triple.triplePointApi.service;

import com.triple.triplePointApi.exception.*;

import java.util.List;

public interface PointService {

    String createNewUserPoint(String userId) throws DuplicateDataException;

    String addReviewPoint(String userId, String placeId, String reviewId, List<String> photos) throws NotEnoughPointException, NoUserPointDataException, NoSuitableReviewException, DuplicateDataException;

    String modReviewPoint(String userId, String placeId, String reviewId, List<String> photos) throws NotEnoughPointException, NoUserPointDataException, NoSuitableReviewException;

    boolean deleteReview(String userId, String placeId, String reviewId) throws AbnormalPointException, NotEnoughPointException, NoUserPointDataException, NoSuitableReviewException;

    int getAllPoint();

    int getPointByUserId(String userId);

}
