package com.triple.triplePointApi.service;

import java.util.List;

public interface PointService {

    String createNewUserPoint(String userId);

    String addReviewPoint(String userId, String placeId, List<String> photos);

    String modReviewPoint(String userId, String placeId, List<String> photos);

    boolean deleteReview(String userId, String placeId);

    int getAllPoint();

    int getPointByUserId(String userId);

}
