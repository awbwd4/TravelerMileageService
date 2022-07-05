package com.triple.triplePointApi.repository;

import com.triple.triplePointApi.domain.Point;
import com.triple.triplePointApi.exception.NoUserPointDataException;
import com.triple.triplePointApi.exception.NotEnoughPointException;

import java.util.List;

public interface PointRepository {

    String create(Point point);

    String modifyPoint(String userId, int point) throws NotEnoughPointException, NoUserPointDataException;

    Point find(String uuid);

    Point getPointByUserId(String userId);

    List<Point> getAllPoints();


}
