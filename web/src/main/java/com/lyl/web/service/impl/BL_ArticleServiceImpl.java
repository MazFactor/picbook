package com.lyl.web.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.jinghuan.common.util.FtpUtil;
import com.lyl.web.entity.BL_Comment;
import com.lyl.web.mapper.BL_ArticleMapper;
import com.lyl.web.entity.BL_Article;
import com.lyl.web.mapper.BL_CommentMapper;
import com.lyl.web.service.BL_ArticleService;
import com.lyl.web.util.HtmlUtils;
import com.lyl.web.vo.*;
import com.lyl.web.vo.*;
import net.coobird.thumbnailator.Thumbnails;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ResourceUtils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Service
public class BL_ArticleServiceImpl implements BL_ArticleService {
    @Autowired
    private BL_ArticleMapper bl_articleMapper;
    @Autowired
    private BL_CommentMapper bl_commentMapper;

    /**
     * 根据ID查找文章
     * @param bl_articleIndexVO 含文章ID
     * @return 文章
     */
    @Override
    @Transactional(readOnly = false)
    public BL_ArticleIndexVO findBlArticleById(BL_ArticleIndexVO bl_articleIndexVO) {
        // 更新点击数+1
        if(bl_articleMapper.updateClicksById(bl_articleIndexVO.getB1_id()) <= 0)
            System.out.println("更新文章点击数+1失败！");
        BL_ArticleIndexVO blArticleIndexVOResult = bl_articleMapper.searchBlArticleById(bl_articleIndexVO.getB1_id());
        if(null != blArticleIndexVOResult){
            // 缓存题图
            if(!downloadThematic(bl_articleIndexVO.getFtpImageParent(), blArticleIndexVOResult.getB1_thematic(), bl_articleIndexVO.getTargetImagePath(), blArticleIndexVOResult.getB1_thematic()))
                System.out.println("下载题图失败！");
            // 查询作者信息
            blArticleIndexVOResult.setBl_user(bl_articleMapper.searchBlUserById(blArticleIndexVOResult.getB0_id()));
        }
        return blArticleIndexVOResult;
    }

    @Override
    @Transactional(readOnly = true)
    public List<BL_ArticleIndexVO> queryList(BL_ArticleIndexVO query) {
        query.setDeleteFlag(false);  //默认未删除
        query.setSortString("create_time desc");  //按时间降序排列
        return bl_articleMapper.queryList(query);
    }

    /**
     * 首页查询
     * @param query
     * @return
     */
    @Override
    @Transactional(readOnly = true)
    public PageInfo<BL_ArticleIndexVO> queryPage(BL_ArticleIndexVO query) {
        query.setDeleteFlag(false);  //默认未删除
        query.setSortString("create_time desc");
        PageHelper.startPage(query.getPage(), query.getSize());
        List<BL_ArticleIndexVO> resultList = queryList(query);
        try {
            String path = ResourceUtils.getURL("classpath:").getPath();
            System.out.println(path);
        }catch (Exception e){
            System.out.println(e.getMessage());
        }
        for (BL_ArticleIndexVO bl_articleVo : resultList) {
            // 缓存题图
            if(!downloadThematic(query.getFtpImageParent(),bl_articleVo.getB1_thematic(),query.getTargetImagePath(),bl_articleVo.getB1_thematic()))
                System.out.println("下载题图失败！");
            // 将内容替换为摘要
            if (null != bl_articleVo.getB1_content() && bl_articleVo.getB1_content().length() > 0)
                bl_articleVo.setB1_content(HtmlUtils.getExcerpt(bl_articleVo.getB1_content()));
            // 查询作者信息
            bl_articleVo.setBl_user(bl_articleMapper.searchBlUserById(bl_articleVo.getB0_id()));
            // 查询标签
            String tagIds = bl_articleMapper.queryTagsByArticleId(bl_articleVo.getB1_id());
            String[] tagCollection = null;
            StringBuilder tagsString = new StringBuilder();
            if(null != tagIds && tagIds.length() > 0)
                tagCollection = tagIds.split("\\,");
            if(null != tagCollection && tagCollection.length > 0) {
                for (int i = 0; i < tagCollection.length; i++) {
                    BL_TagVO bl_tagVO;
                    if (null != tagCollection[i] && tagCollection[i].length() > 0) {
                        bl_tagVO = bl_articleMapper.queryTagById(Integer.parseInt(tagCollection[i]));
                        if (null != bl_tagVO && null != bl_tagVO.getB3_name() && bl_tagVO.getB3_name().length() > 0)
                            if (i != tagCollection.length - 1) {
                                tagsString.append(bl_tagVO.getB3_name());
                                tagsString.append(",");
                            } else
                                tagsString.append(bl_tagVO.getB3_name());

                    }
                }
            }
            if(tagsString.length() > 0)
                bl_articleVo.setB1_tags(tagsString.toString());
            else
                bl_articleVo.setB1_tags(null);
            // 统计评论数量
            bl_articleVo.setB1_commentsCount(bl_articleMapper.sumCommentsCount(bl_articleVo.getB1_id()));
        }
        return new PageInfo<>(resultList);
    }
    /**
     * 插入文章
     * @param bl_article 文章
     */
    @Override
    public void insertOneArticle(BL_Article bl_article ) {
        bl_articleMapper.insertOneArticle(bl_article);
    }

    /**
     * 查询最受欢迎文章
     * @return
     */
    @Override
    public List<BL_ArticleIndexVO> queryMostPopulars(BL_ArticleIndexVO queryMostPopularsVO) {
        queryMostPopularsVO.setSortString("B1_CLICKS desc");
        List<BL_ArticleIndexVO> resultList = bl_articleMapper.queryMostPopulars(queryMostPopularsVO);

        String orgImagename;
        for (BL_ArticleIndexVO bl_articleVo : resultList) {
            String[] thumbnails;
            String thumbnail = "";
            orgImagename = bl_articleVo.getB1_thematic();
            // 生成缩略图名称
            thumbnails = bl_articleVo.getB1_thematic().split("\\.");
            for (int i = 0; i < thumbnails.length; i++) {
                if(i==thumbnails.length-1)
                    thumbnail += "_thumb.";
                thumbnail += thumbnails[i];
            }
            bl_articleVo.setB1_thematic(thumbnail);
            // 缓存题图
            if(!downloadThematic(queryMostPopularsVO.getFtpImageParent(),orgImagename,queryMostPopularsVO.getTargetImagePath(),bl_articleVo.getB1_thematic()))
                System.out.println("下载题图失败！");
            else {
                try {
                    resizeImage(bl_articleVo.getB1_thematic(), bl_articleVo.getB1_thematic(), 60, 55, true);
                }catch (IOException e){
                    System.out.println(e.getMessage() + "重新生成图片大小失败！");
                }
            }
            // 将内容替换为摘要（15字）
            if (null != bl_articleVo.getB1_content()) {
                if(HtmlUtils.Html2Text(HtmlUtils.getExcerpt(bl_articleVo.getB1_content())).length() > 15)
                    bl_articleVo.setB1_content(HtmlUtils.Html2Text(HtmlUtils.getExcerpt(bl_articleVo.getB1_content())).substring(0, 14) + "...");
                else
                    bl_articleVo.setB1_content(HtmlUtils.Html2Text(HtmlUtils.getExcerpt(bl_articleVo.getB1_content())) + "...");
            }
            // 查询作者信息
            bl_articleVo.setBl_user(bl_articleMapper.searchBlUserById(bl_articleVo.getB0_id()));
        }
        return resultList;
    }

    /**
     * 归档列表
     * @param queryArticleArchiveVO
     * @return
     */
    @Override
    public List<BL_ArticleArchiveVO> queryArchives(BL_ArticleArchiveVO queryArticleArchiveVO) {
        queryArticleArchiveVO.setSortString("DATE_FORMAT(create_time,'%c月，%Y') desc");
        return bl_articleMapper.queryArchives(queryArticleArchiveVO);
    }

    /**
     * 分类列表
     * @param queryCategoryVO
     * @return
     */
    @Override
    public List<BL_CategoryVO> queryCategories(BL_CategoryVO queryCategoryVO) {
        queryCategoryVO.setSortString("create_time desc");
        return bl_articleMapper.queryCategories(queryCategoryVO);
    }

    /**
     * 分类列表（带文章数量）
     * @param queryCategoryVO
     * @return
     */
    @Override
    public List<BL_CategoryVO> queryCategoriesWithArticleCount(BL_CategoryVO queryCategoryVO) {
        queryCategoryVO.setSortString("create_time desc");
        List<BL_CategoryVO> initialCategoryVOs = bl_articleMapper.queryCategories(queryCategoryVO);
        for (BL_CategoryVO categoryVO:initialCategoryVOs) {
            Integer articleCoutStaticByCategory = bl_articleMapper.queryArticleCountByCategoryId(categoryVO.getB4_id());
            if(null != articleCoutStaticByCategory)
                categoryVO.setArticleCount(articleCoutStaticByCategory);
            else
                categoryVO.setArticleCount(0);
        }
        return initialCategoryVOs;
    }

    /**
     * 评论列表 (构建评论树的策略是利用mybatis将子评论先嵌套到父评论中，再将子评论从全集中剔除掉)
     * @param queryCommentVO
     * @return
     */
    @Override
    public List<BL_CommentVO> queryComments(BL_CommentVO queryCommentVO) {
        queryCommentVO.setSortString("create_time");
        // 查询父节点
        List<BL_CommentVO> allComments = bl_articleMapper.queryComments(queryCommentVO);
        return buildCommentTree(allComments);
    }

    /**
     * 评论列表（侧边栏）
     * @param queryCommentVO
     * @return
     */
    @Override
    public List<BL_CommentVO> queryCommentsForSideBar(BL_CommentVO queryCommentVO) {
        queryCommentVO.setSortString("create_time desc");
        List<BL_CommentVO> commentVOs = bl_commentMapper.queryCommentsForSideBar(queryCommentVO);
        for (BL_CommentVO commentVO:commentVOs) {
            // 将内容替换为摘要（25字）
            if (null != commentVO.getB2_content()) {
                if(HtmlUtils.Html2Text(HtmlUtils.getExcerpt(commentVO.getB2_content())).length() > 25)
                    commentVO.setB2_content(HtmlUtils.Html2Text(HtmlUtils.getExcerpt(commentVO.getB2_content())).substring(0, 24) + "...");
                else
                    commentVO.setB2_content(HtmlUtils.Html2Text(HtmlUtils.getExcerpt(commentVO.getB2_content())) + "...");
            }
        }
        return commentVOs;
    }

    /**
     * 标签列表
     * @param queryTagVO
     * @return
     */
    @Override
    public List<BL_TagVO> queryTags(BL_TagVO queryTagVO) {
        queryTagVO.setSortString("create_time desc");
        return bl_articleMapper.queryTags(queryTagVO);
    }

    /**
     * 添加新评论
     * @param bl_comment
     */
    @Override
    public void insertComment(BL_Comment bl_comment) {
        bl_articleMapper.insertComment(bl_comment);
    }

    /**
     * 下载文章图片到本地
     * @param content 文章内容
     * @return 下载结果：成功-true；失败-false
     */
    private boolean downloadArticleImages(String ftpImageParent, String content, String targetLocation){
        boolean downloadResult = true;
        List<String> imageList = HtmlUtils.getImageSrc(content);

        String[] name;
        try {
            for (String imageName : imageList) {
                name = imageName.split("\\/");
                FtpUtil.downSftpFile(name[name.length-1], ftpImageParent, name[name.length-1], targetLocation);
            }
        }catch (Exception e){
            System.out.println(e.getMessage());
//            logger.error(e.getMessage(), e);
            downloadResult = false;
        }
        return downloadResult;
    }

    /**
     * 下载文章图片到本地
     * @param orgImageName 题图名
     * @return 下载结果：成功-true；失败-false
     */
    private boolean downloadThematic(String ftpImageParent, String orgImageName, String targetLocation, String newImageName){
        boolean downloadResult = true;

        String[] orgImgName;
        String[] newImgName;
        try {
            orgImgName = orgImageName.split("\\/");
            newImgName = newImageName.split("\\/");
            FtpUtil.downSftpFile(orgImgName[orgImgName.length-1], ftpImageParent, newImgName[newImgName.length-1], targetLocation);
        }catch (Exception e){
            System.out.println(e.getMessage());
            downloadResult = false;
        }
        return downloadResult;
    }

    /**
     * 重新生成图片宽、高
     *
     * @param srcPath   图片路径
     * @param destPath  新生成的图片路径
     * @param newWith   新的宽度
     * @param newHeight 新的高度
     * @param forceSize 是否强制使用指定宽、高,false:会保持原图片宽高比例约束
     * @return
     * @throws IOException
     */
    public static boolean resizeImage(String srcPath, String destPath, int newWith, int newHeight, boolean forceSize) throws IOException {
        if (forceSize) {
            Thumbnails.of(srcPath).forceSize(newWith, newHeight).toFile(destPath);

        } else {
            Thumbnails.of(srcPath).width(newWith).height(newHeight).toFile(destPath);
        }
        return true;
    }

    /**
     * 构建评论树
     * @param allComments 所有评论
     * @return 评论树
     */
    private List<BL_CommentVO> buildCommentTree(List<BL_CommentVO> allComments){
        if(null == allComments || allComments.size() <=0 ) return null;
        List<BL_CommentVO> resultCommentTree = new ArrayList<>();
        for (BL_CommentVO node : allComments) {
            if(node.getB2_pid() != null) {
                continue;
            }
            resultCommentTree.add(node);
        }
        return resultCommentTree;
    }
}
