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
import java.text.SimpleDateFormat;
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
    public String redirectIndex(Model model){
        // 查询最近上传的图片的ID
        Picture picture = pictureService.findTheLatestPicture();
        if(picture == null) return "error";
        // 根据图片ID查询文章
        Article article = articleService.findArticleByPicId(picture.getPic_id());
        if(article == null) return "error";
        model.addAttribute("article", article);
        // 某天图片列表（最近一天）
        List<Picture> pictureList = pictureService.findPicturesByDay(picture.getCreate_time());
        if(pictureList == null || pictureList.size() <= 0) return "error";
        model.addAttribute("pictures", pictureList);
        // 查询所有分类
        List<Category> categoryList = categoryService.findAllCategories();
        if(categoryList == null || categoryList.size() <= 0) return "error";
        model.addAttribute("categories", categoryList);
        // 时间轴
        List<TimelineVO> timelineVOList = pictureService.findAllTimeline();
        if(timelineVOList == null || timelineVOList.size() <=0 ) return "error";
        model.addAttribute("timeline", timelineVOList);
        return "index";
    }

    /**
     * 点击右侧图片
     * @param model 模型
     * @param picId 图片ID
     * @return 文章预览模块
     */
    @RequestMapping(value = "/picture", method = RequestMethod.POST)
    public String clickOnePic(Model model,
                              @RequestParam(value = "picId") String picId) {
        if(picId == null || picId.length() <= 0) return "error";
        // 文章
        Article article = articleService.findArticleByPicId(Integer.parseInt(picId));
        if(article == null) return "error";
        model.addAttribute("article", article);
        return "preview";
    }

    @RequestMapping(value = "/category", method = RequestMethod.POST)
    public String clickOneCategory(Model model,
                              @RequestParam(value = "categoryId") String categoryId) {
        if(categoryId == null || categoryId.length() <= 0) return "error";
        // 文章
        List<Picture> pictureList = pictureService.findPicturesByCategoryId(Integer.parseInt(categoryId));
        if(pictureList == null || pictureList.size() <= 0) return "error";
        model.addAttribute("pictures", pictureList);
        return "pics";
    }

    @RequestMapping(value = "/timeline", method = RequestMethod.POST)
    public String clickOneTimeline(Model model,
                                   @RequestParam(value = "timeString") String timeString) {
        if(timeString == null || timeString.length() <= 0) return "error";
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

        // 文章
        List<Picture> pictureList = pictureService.findPicturesByTimeline(timeString);
        if(pictureList == null || pictureList.size() <= 0) return "error";
        model.addAttribute("pictures", pictureList);
        return "pics";
    }

    @RequestMapping(value = "/post")
    public String testCropper(Model model, HttpServletRequest request) {
        return "post";
    }
}
