package com.triple.triplePointApi.repository;

import com.triple.triplePointApi.domain.Point;

import java.util.List;

public interface PointRepository {

    String create(Point point);

    String modifyPoint(String userId, int point);

    Point find(String uuid);

    Point getPointByUserId(String userId);

    List<Point> getAllPoints();


}
