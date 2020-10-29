package com.lyl.web.mapper;

import com.lyl.web.entity.Article;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Component;

@Mapper
@Component
public interface ArticleMapper extends Mapper{

    Article findArticleByPicId(Integer parseInt);
}
