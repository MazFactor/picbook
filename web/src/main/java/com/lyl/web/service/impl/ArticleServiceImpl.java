package com.lyl.web.service.impl;

import com.lyl.web.entity.Article;
import com.lyl.web.entity.Picture;
import com.lyl.web.mapper.ArticleMapper;
import com.lyl.web.service.ArticleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ArticleServiceImpl implements ArticleService {

    @Autowired
    ArticleMapper articleMapper;

    @Override
    public Article findArticleByPicId(Integer picId) {
        return articleMapper.findArticleByPicId(picId);
    }

    @Override
    public void insertNewArticle(Article newArticle) {
        articleMapper.insertNewArticle(newArticle);
    }

    @Override
    public Article findArticleById(Integer articleId) {
        return articleMapper.findArticleById(articleId);
    }

    @Override
    public void deleteArticleById(Integer articleId) {
        articleMapper.deleteArticleById(articleId);
    }

    @Override
    public void updateArticle(Article newArticle) {
        articleMapper.updateArticle(newArticle);
    }


}
