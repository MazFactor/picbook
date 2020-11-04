package com.lyl.web.controller;

import com.github.tomakehurst.wiremock.common.Json;
import com.lyl.common.util.Base64Util;
import com.lyl.common.util.FileUtil;
import com.lyl.web.base.Result.JsonResult;
import com.lyl.web.entity.Article;
import com.lyl.web.entity.Category;
import com.lyl.web.entity.Picture;
import com.lyl.web.service.ArticleService;
import com.lyl.web.service.CategoryService;
import com.lyl.web.service.PictureService;
import com.lyl.web.util.JsonResultUtil;
import com.lyl.web.vo.TimelineVO;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.xml.crypto.Data;
import java.io.File;
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
    @Transactional
    @RequestMapping(value = "/picture", method = RequestMethod.POST)
    public String clickOnePic(Model model,
                              @RequestParam(value = "picId") String picId,
                              @RequestParam(value = "prior_pic_id") String prior_pic_id) {
        if(picId == null || picId.length() <= 0) return "error";
        // 图片点击数加一
        if(prior_pic_id != null && prior_pic_id.length() > 0 && !prior_pic_id.equals(picId))
            pictureService.clicksOfPicturePlusOne(Integer.parseInt(picId));
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
        // 图片列表（按分类查询）
        List<Picture> pictureList = pictureService.findPicturesByCategoryId(Integer.parseInt(categoryId));
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

    @RequestMapping(value = "/add")
    public String testCropper(Model model, HttpServletRequest request) {
        SimpleDateFormat sdf = new SimpleDateFormat("M月 d, yyyy");
        model.addAttribute("nowDate", sdf.format(new Date()));
        Article emptyArticle = new Article();
        Picture emptyPicture = new Picture();
        emptyPicture.setCategory_id(-1);
        emptyPicture.setPic("");
        emptyPicture.setClicks(0);
        emptyPicture.setPic_id(-1);
        emptyArticle.setPic_id(-1);
        emptyArticle.setBrief("");
        emptyArticle.setPicture(emptyPicture);
        emptyArticle.setTitle("");
        emptyArticle.setArticle_id(-1);
        model.addAttribute("editingArticle", emptyArticle);
        model.addAttribute("command", 1);
        return "post";
    }

    @Transactional
    @RequestMapping(value = "/publish")
    public String publish(Model model,
                          @RequestParam(value = "title") String title,
                          @RequestParam(value = "thematic") String img,
                          @RequestParam(value = "brief") String brief,
                          @RequestParam(value = "category") String category){
        if(title == null || title.length() <= 0 || img == null || img.length() <= 0) return "error";
        // 判断是否新增分类，如果是则新增并获取新增分类ID；否则获取已存在分类ID
        Category categoryExisted;
        Integer categoryId = -1;
        if(category != null && category.length() > 0) {
            categoryExisted = categoryService.findCategoryByName(category);
            if(categoryExisted == null) {
                categoryExisted = new Category();
                categoryExisted.setCategory(category);
                categoryService.insertNewCategory(categoryExisted);
            }
            categoryId = categoryExisted.getCategory_id();
        }else {
            categoryId = 1;
        }
        if(categoryId == -1) return "error";
        // 保存图片并获取新增图片ID
        MultipartFile multipartFile = Base64Util.base64ToMultipart(img);
        File image;
        String imageUrlForFront = null;
        Integer picId = -1;
        Picture newPicture = new Picture();
        try {
            if(multipartFile != null) {
                image = FileUtil.uploadFile("D:\\images\\blog\\", multipartFile);
                if(image != null){
                    imageUrlForFront = "pic/" + image.getName();
                }
                // 保存图片（路径）
                if(imageUrlForFront != null && imageUrlForFront.length() > 0) {
                    newPicture.setPic(imageUrlForFront);
                    newPicture.setCategory_id(categoryId);
                    pictureService.insertNewPicture(newPicture);
                    picId = newPicture.getPic_id();
                }
            }
        }catch (Exception e){
            System.out.println("保存图片失败："+e.getMessage());
            return "error";
        }
        if(picId == -1) return "error";
        // 保存文章
        Integer newArticleId = -1;
        Article newArticle = new Article();
        newArticle.setTitle(title);
        newArticle.setPic_id(picId);
        newArticle.setPicture(newPicture);
        Document doc = Jsoup.parse(brief);
        String bodyHtml = doc.body() != null ? doc.body().html() : brief;
        newArticle.setBrief(bodyHtml);
        articleService.insertNewArticle(newArticle);
        newArticleId = newArticle.getArticle_id();
        if(newArticleId <= 0) return "error";
        return "redirect:/";
    }

    @Transactional
    @RequestMapping(value = "/delete")
    @ResponseBody
    public JsonResult deleteArticle(Model model,
                                    @RequestParam(value = "articleId") String articleId){
        if(articleId == null || articleId.length() <= 0) {
            return  JsonResultUtil.createJsonResult(false, null, "1", "文章删除失败！");
        }
        // 获取要删除的文章对象
        Article deletingArticle = articleService.findArticleById(Integer.parseInt(articleId));
        if(deletingArticle == null || deletingArticle.getPic_id() == null || deletingArticle.getPic_id() <= 0)
            return JsonResultUtil.createJsonResult(false, null, "1", "文章删除失败！");
        // 删除图片
        Picture deletingPicture = pictureService.findPictureById(deletingArticle.getPic_id());
        if(deletingPicture == null) return JsonResultUtil.createJsonResult(false, null, "1", "文章删除失败！");
        String[] imagePathInfo = deletingPicture.getPic().split("/");
        FileUtil.deleteFile("D:\\images\\blog\\" + imagePathInfo[1]);
        pictureService.deletePictureById(deletingPicture.getPic_id());
        // 删除文章
        articleService.deleteArticleById(Integer.parseInt(articleId));
        return JsonResultUtil.createJsonResult(true, null, "0", "文章删除成功！");
    }

    @RequestMapping(value = "/toEdit")
    public String editArticle(Model model, HttpServletRequest request){
        String articleId = request.getParameter("articleId");
        if(articleId == null || articleId.length() <= 0) return  "error";
        // 时间显示
        SimpleDateFormat sdf = new SimpleDateFormat("M月 d, yyyy");
        model.addAttribute("nowDate", sdf.format(new Date()));
        Article editingArticle = articleService.findArticleById(Integer.parseInt(articleId));
        if(editingArticle == null || editingArticle.getPicture() == null || editingArticle.getPicture().getCategory_id() == null) return "error";
        Category editCategory = categoryService.findCategoryById(editingArticle.getPicture().getCategory_id());
        if(editCategory == null) return "error";
        model.addAttribute("editingArticle", editingArticle);
        model.addAttribute("editCategory", editCategory);
        model.addAttribute("command", 0);
        return "post";
    }

    @RequestMapping(value = "/update")
    public String updateArticle(Model model,
                                @RequestParam(value = "title") String title,
                                @RequestParam(value = "thematic") String img,
                                @RequestParam(value = "brief") String brief,
                                @RequestParam(value = "category") String category) {
        if(title == null || title.length() <= 0 || img == null || img.length() <= 0) return "error";
        // 判断是否需要新增分类，如果是则新增并获取新增分类ID；否则获取已存在分类ID
        Integer targetCategoryId = checkCategoryExitByName(category);
        if(categoryId == -1) return "error";
        return "";
    }

    /**
     *
     * @param category
     * @return
     */
    private Integer checkCategoryExitByName(String category) {
        Category categoryExisted;
        Integer categoryId = -1;
        if(category != null && category.length() > 0) {
            categoryExisted = categoryService.findCategoryByName(category);
            if(categoryExisted == null) {
                categoryExisted = new Category();
                categoryExisted.setCategory(category);
                categoryService.insertNewCategory(categoryExisted);
            }
            categoryId = categoryExisted.getCategory_id();
        }else {
            categoryId = 1;
        }
        if(categoryId == -1) return -1;
        return categoryId;
    }
}
