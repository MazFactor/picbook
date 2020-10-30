package com.lyl.web.controller;

import com.lyl.web.entity.Article;
import com.lyl.web.entity.Category;
import com.lyl.web.entity.Picture;
import com.lyl.web.service.ArticleService;
import com.lyl.web.service.CategoryService;
import com.lyl.web.service.PictureService;
import com.lyl.web.vo.TimelineVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.xml.crypto.Data;
import java.util.Date;
import java.util.List;

@Controller
@RequestMapping(value = "/")
public class DispatchController {

    @Autowired
    private ArticleService articleService;
    @Autowired
    private PictureService pictureService;
    @Autowired
    private CategoryService categoryService;

    @RequestMapping(value = "/")
    public String redirectIndex(){
        return "redirect:article?picId=1";
    }

    @RequestMapping(value = "/article")
    public String clickOnePic(Model model,
                              @RequestParam(value = "picId") String picId) {
        if(picId == null || picId.length() <= 0) return "error";
        // 文章
        Article article = articleService.findArticleByPicId(Integer.parseInt(picId));
        model.addAttribute("article", article);
        // 某天图片列表（默认当天）
        List<Picture> pictureList = pictureService.findPicturesByDay(new Date(System.currentTimeMillis()));
        model.addAttribute("pictures", pictureList);
        // 查询所有分类
        List<Category> categoryList = categoryService.findAllCategories();
        model.addAttribute("categories", categoryList);
        // 时间轴
        List<TimelineVO> timelineVOList = pictureService.findPicturesByTimeline();
        model.addAttribute("timeline", timelineVOList);
        return "index";
    }



    @RequestMapping(value = "/testCropper")
    public String testCropper(Model model, HttpServletRequest request) {
        return "test/testcropper";
    }
}
