package com.lyl.web.service.impl;

import com.lyl.web.entity.Picture;
import com.lyl.web.mapper.PictureMapper;
import com.lyl.web.service.PictureService;
import com.lyl.web.vo.TimelineVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class PictureServiceImpl implements PictureService {

    @Autowired
    PictureMapper pictureMapper;

    @Override
    public List<Picture> findPicturesByDay(Date theDate) {
        return pictureMapper.findPicturesByDay(theDate);
    }

    @Override
    public List<TimelineVO> findAllTimeline() {
        return pictureMapper.findAllTimeline();
    }

    @Override
    public Picture findTheLatestPicture() {
        return pictureMapper.findTheLatestPicture();
    }

    @Override
    public List<Picture> findPicturesByCategoryId(Integer categoryId) {
        return pictureMapper.findPicturesByCategoryId(categoryId);
    }

    @Override
    public List<Picture> findPicturesByTimeline(String timeline) {
        return pictureMapper.findPicturesByTimeline(timeline);
    }
}
