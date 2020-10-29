package com.lyl.web.service.impl;

import com.lyl.web.entity.Article;
import com.lyl.web.mapper.ArticleMapper;
import com.lyl.web.service.ArticleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ArticleServiceImpl implements ArticleService {

    @Autowired
    ArticleMapper articleMapper;

    @Override
    public Article findArticleByPicId(Integer parseInt) {
        return articleMapper.findArticleByPicId(parseInt);
    }
}
