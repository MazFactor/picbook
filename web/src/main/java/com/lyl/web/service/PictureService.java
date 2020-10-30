package com.lyl.web.service;

import com.lyl.web.entity.Picture;
import com.lyl.web.vo.TimelineVO;

import java.util.Date;
import java.util.List;

public interface PictureService {
    List<Picture> findPicturesByDay(Date date);

    List<TimelineVO> findPicturesByTimeline();
}
