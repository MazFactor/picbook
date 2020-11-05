package com.lyl.web.mapper;

import com.lyl.web.entity.Article;
import com.lyl.web.entity.Picture;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Component;

import java.util.List;

@Mapper
@Component
public interface ArticleMapper extends Mapper{

    Article findArticleByPicId(Integer parseInt);

    void insertNewArticle(Article newArticle);

    Article findArticleById(Integer articleId);

    void deleteArticleById(Integer articleId);

    void updateArticle(Article newArticle);
}
