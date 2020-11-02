package com.lyl.web.mapper;

import com.lyl.web.entity.Picture;
import com.lyl.web.vo.TimelineVO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.List;

@Mapper
@Component
public interface PictureMapper {
    List<Picture> findPicturesByDay(@Param("theDate")Date theDate);

    List<TimelineVO> findAllTimeline();

    Picture findTheLatestPicture();

    List<Picture> findPicturesByCategoryId(Integer categoryId);

    List<Picture> findPicturesByTimeline(String timeline);

    void insertNewPicture(Picture newPicture);
}
