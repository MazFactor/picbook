package com.lyl.web.service;

import com.github.pagehelper.PageInfo;
import com.lyl.web.entity.BL_Article;
import com.lyl.web.entity.BL_Comment;
import com.lyl.web.vo.*;
import com.lyl.web.vo.*;

import java.util.List;

public interface BL_ArticleService {
    BL_ArticleIndexVO findBlArticleById(BL_ArticleIndexVO bl_articleIndexVO);
    List<BL_ArticleIndexVO> queryList(BL_ArticleIndexVO query);
    PageInfo<BL_ArticleIndexVO> queryPage(BL_ArticleIndexVO query);
    void insertOneArticle(BL_Article bl_article );
    List<BL_ArticleIndexVO> queryMostPopulars(BL_ArticleIndexVO queryMostPopularsVO);
    List<BL_ArticleArchiveVO> queryArchives(BL_ArticleArchiveVO queryArticleArchiveVO);

    List<BL_CategoryVO> queryCategories(BL_CategoryVO queryCategoryVO);

    List<BL_CategoryVO> queryCategoriesWithArticleCount(BL_CategoryVO queryCategoryVO);

    List<BL_CommentVO> queryComments(BL_CommentVO queryCommentVO);

    List<BL_CommentVO> queryCommentsForSideBar(BL_CommentVO queryCommentVO);

    List<BL_TagVO> queryTags(BL_TagVO queryTagVO);

    void insertComment(BL_Comment bl_comment);
}
