package com.triple.triplePointApi.service;

import java.util.List;

public interface PointService {

    String createNewUserPoint(String userId);

    void addReviewPoint(String userId, String placeId, List<String> photos);

    void modReviewPoint(String userId, String placeId, List<String> photos);

    void deleteReview(String userId, String placeId);

    int getAllPoint();

    int getPointByUserId(String userId);

}
