package com.lyl.web.service;

import com.lyl.web.entity.Article;
import com.lyl.web.entity.Picture;

import java.util.List;

public interface ArticleService {

    Article findArticleByPicId(Integer picId);
}
