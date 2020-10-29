package com.lyl.web.controller;

import com.lyl.web.entity.Article;
import com.lyl.web.service.ArticleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@Controller
@RequestMapping(value = "/")
public class DispatchController {

    @Autowired
    private ArticleService articleService;

    @RequestMapping(value = "/")
    public String redirectIndex(){
        return "redirect:article?picId=1";
    }

    @RequestMapping(value = "/article")
    public String clickOnePic(Model model,
                              @RequestParam(value = "picId") String picId) {
        if(picId == null || picId.length() <= 0) return "error";
        Article article = articleService.findArticleByPicId(Integer.parseInt(picId));
        model.addAttribute("article", article);
        return "index";
    }

    @RequestMapping(value = "/testCropper")
    public String testCropper(Model model, HttpServletRequest request) {
        return "test/testcropper";
    }
}
