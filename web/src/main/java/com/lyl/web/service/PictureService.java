package com.lyl.web.service;

import com.lyl.web.entity.Picture;
import com.lyl.web.vo.TimelineVO;

import java.util.Date;
import java.util.List;

public interface PictureService {
    List<Picture> findPicturesByDay(Date date);

    List<TimelineVO> findAllTimeline();

    Picture findTheLatestPicture();

    List<Picture> findPicturesByCategoryId(Integer categoryId);

    List<Picture> findPicturesByTimeline(String timeline);

    void insertNewPicture(Picture newPicture);

    void clicksOfPicturePlusOne(Integer picId);
}
