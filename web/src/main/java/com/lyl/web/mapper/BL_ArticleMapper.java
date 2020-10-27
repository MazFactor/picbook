package com.lyl.web.mapper;

import com.lyl.web.entity.BL_Article;
import com.lyl.web.entity.BL_Comment;
import com.lyl.web.entity.BL_User;
import com.lyl.web.vo.*;
import com.lyl.web.vo.*;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;

@Mapper
@Component
public interface BL_ArticleMapper extends Mapper{
    /**
     * @Description 根据主键查询文章
     * @param b1_id 主键
     * @return 文章
     */
    BL_ArticleIndexVO searchBlArticleById(Integer b1_id);
    List<HashMap> searchArticleList();
    List<BL_ArticleIndexVO> queryList(BL_ArticleIndexVO query);
    void insertOneArticle(BL_Article bl_article);

    List<BL_ArticleIndexVO> queryMostPopulars(BL_ArticleIndexVO queryMostPopularsVO);

    List<BL_ArticleArchiveVO> queryArchives(BL_ArticleArchiveVO queryArticleArchiveVO);

    List<BL_CategoryVO> queryCategories(BL_CategoryVO queryCategoryVO);

    List<BL_CommentVO> queryComments(BL_CommentVO queryCommentVO);

    List<BL_TagVO> queryTags(BL_TagVO queryTagVO);

    int updateClicksById(Integer b1_id);

    BL_User searchBlUserById(Integer b0_id);

    Integer sumCommentsCount(Integer b0_id);

    String queryTagsByArticleId(Integer b1_id);

    BL_TagVO queryTagById(int parseInt);

    Integer queryArticleCountByCategoryId(Integer b4_id);

    void insertComment(BL_Comment bl_comment);
}
