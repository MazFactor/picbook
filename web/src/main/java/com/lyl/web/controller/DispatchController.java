package com.lyl.web.controller;

import com.github.pagehelper.PageInfo;
import com.jinghuan.common.util.SftpClient;
import com.lyl.web.entity.BL_Article;
import com.lyl.web.entity.BL_Comment;
import com.lyl.web.service.impl.BL_ArticleServiceImpl;
import com.lyl.web.service.UploadFileService;
import com.lyl.web.service.impl.BL_CommentServiceImpl;
import com.lyl.web.vo.*;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

@Controller
@RequestMapping(value = "/")
public class DispatchController {

    /**
     * 配置文件
     */
    private static final Properties PROPERTIES = new Properties();
    /**
     * 加载配置信息
     */
    static {
        try {
            PROPERTIES.load(DispatchController.class.getClassLoader().getResourceAsStream("ftp-tron.properties"));
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    @Autowired
    private BL_ArticleServiceImpl bl_articleService;
    @Autowired
    private BL_CommentServiceImpl bl_commentService;
    @Autowired
    private UploadFileService uploadFileService;

    @RequestMapping(value = "/")
    public String redirectIndex(){
        return "index";
    }

    /**
     * 首页
     * @param model
     * @param request
     * @return
     */
    @RequestMapping(value = "/blog_large_sidebar")
    public String list(Model model, HttpServletRequest request, @RequestParam String pageNum) {
        // 图片缓存路径
        String targetImagePath = request.getSession().getServletContext().getRealPath("/") + PROPERTIES.getProperty("ftp.zuck.fileparent");
        File file = new File(targetImagePath);
        // 图片ftp存储路径
        String ftpImageParent = PROPERTIES.getProperty("ftp.zuck.fileparent");
        // 创建缓存路径
        if(!file.exists())
            file.mkdirs();
        // 查询文章列表
        BL_ArticleIndexVO query = new BL_ArticleIndexVO();
        query.setFtpImageParent(ftpImageParent);
        query.setTargetImagePath(targetImagePath);
        // 仅指定页码，每页数量在BaseEntity中指定
        if(null != pageNum && pageNum.trim().length() > 0)
            query.setPage(Integer.parseInt(pageNum));
        else
            query.setPage(0);
        PageInfo<BL_ArticleIndexVO> articleList = bl_articleService.queryPage(query);
        model.addAttribute("articlePages", articleList);
        //最受欢迎列表
        BL_ArticleIndexVO queryMostPopularsVO = new BL_ArticleIndexVO();
        queryMostPopularsVO.setFtpImageParent(ftpImageParent);
        queryMostPopularsVO.setTargetImagePath(targetImagePath);
        List<BL_ArticleIndexVO> mostPopulars = bl_articleService.queryMostPopulars(queryMostPopularsVO);
        model.addAttribute("mostPopulars", mostPopulars);
        //归档列表
        BL_ArticleArchiveVO queryArticleArchiveVO = new BL_ArticleArchiveVO();
        List<BL_ArticleArchiveVO> archives = bl_articleService.queryArchives(queryArticleArchiveVO);
        model.addAttribute("archives", archives);
        //分类列表
        BL_CategoryVO queryCategoryVO = new BL_CategoryVO();
        List<BL_CategoryVO> categories = bl_articleService.queryCategoriesWithArticleCount(queryCategoryVO);
        model.addAttribute("categories", categories);
        //评论列表
        BL_CommentVO queryCommentVO = new BL_CommentVO();
        List<BL_CommentVO> comments = bl_articleService.queryCommentsForSideBar(queryCommentVO);
        model.addAttribute("comments", comments);
        //标签列表
        BL_TagVO queryTagVO = new BL_TagVO();
        List<BL_TagVO> tags = bl_articleService.queryTags(queryTagVO);
        model.addAttribute("tags", tags);
        return "blog_large_sidebar";
    }

    /**
     * 详情页
     * @param model
     * @param request
     * @return
     */
    @RequestMapping(value = "/blog_post")
    public String detail(Model model, HttpServletRequest request) {
        String requestUrl = request.getRequestURI();
        String param = request.getParameter("b1_id");
        BL_ArticleIndexVO targetArticle;

        // 图片缓存路径
        String targetImagePath = request.getSession().getServletContext().getRealPath("/") + PROPERTIES.getProperty("ftp.zuck.fileparent");
        File file = new File(targetImagePath);
        // 图片ftp存储路径
        String ftpImageParent = PROPERTIES.getProperty("ftp.zuck.fileparent");
        // 创建缓存路径
        if(!file.exists())
            file.mkdirs();
        // 查询文章列表
        BL_ArticleIndexVO bl_articleIndexVO = new BL_ArticleIndexVO();
        bl_articleIndexVO.setB1_id(Integer.parseInt(param));
        bl_articleIndexVO.setFtpImageParent(ftpImageParent);
        bl_articleIndexVO.setTargetImagePath(targetImagePath);
        // 文章详情
        if (param != null && !param.isEmpty() && Integer.parseInt(param) > 0) {
            targetArticle = bl_articleService.findBlArticleById(bl_articleIndexVO);
            if(targetArticle != null)
                model.addAttribute("article", targetArticle);
            else
                return "error";
        } else {
            return "error";
        }
        //最受欢迎列表
        BL_ArticleIndexVO queryMostPopularsVO = new BL_ArticleIndexVO();
        queryMostPopularsVO.setFtpImageParent(ftpImageParent);
        queryMostPopularsVO.setTargetImagePath(targetImagePath);
        List<BL_ArticleIndexVO> mostPopulars = bl_articleService.queryMostPopulars(queryMostPopularsVO);
        model.addAttribute("mostPopulars", mostPopulars);
        //归档列表
        BL_ArticleArchiveVO queryArticleArchiveVO = new BL_ArticleArchiveVO();
        List<BL_ArticleArchiveVO> archives = bl_articleService.queryArchives(queryArticleArchiveVO);
        model.addAttribute("archives", archives);
        //分类列表
        BL_CategoryVO queryCategoryVO = new BL_CategoryVO();
        List<BL_CategoryVO> categories = bl_articleService.queryCategoriesWithArticleCount(queryCategoryVO);
        model.addAttribute("categories", categories);
        //查询当前文章下的评论信息
        BL_CommentVO queryCommentVO = new BL_CommentVO();
        queryCommentVO.setB1_id(bl_articleIndexVO.getB1_id());
        List<BL_CommentVO> comments = bl_commentService.queryComments(queryCommentVO);
        model.addAttribute("comments", comments);
        //标签列表
        BL_TagVO queryTagVO = new BL_TagVO();
        List<BL_TagVO> tags = bl_articleService.queryTags(queryTagVO);
        model.addAttribute("tags", tags);
        return "blog_post";
    }

    /**
     * 新增
     * @param model
     * @param request
     * @return
     */
    @RequestMapping(value = "/blog_write")
    public String go2write(Model model, HttpServletRequest request) {
        return "blog_write";
    }

    /**
     * 图片上传（至sftp）
     *
     * @param multipartFile 图片文件
     * @param request       请求
     * @return 含图片地址信息的集合
     */
    @RequestMapping(value = "/uploadImage", method = RequestMethod.POST)
    @ResponseBody
    public Map<String, String> uploadImage(@RequestParam(value = "thumbnail") MultipartFile multipartFile, HttpServletRequest request) {
        SftpClient sftpClient = new SftpClient();
        // 返回信息
        Map<String, String> map = new HashMap<>();
        // 服务端根目录
        String resourceImageRealPath = request.getSession().getServletContext().getRealPath("/");
        String fileParent = PROPERTIES.getProperty("ftp.zuck.fileparent");
        // 图片名称
        String imageName;
        try {
            imageName = uploadFileService.uploadTinyImage(resourceImageRealPath, fileParent, multipartFile);
            map.put("url", fileParent + "/" + imageName);
        }catch (Exception e){
            System.out.println(e.getMessage());
            map.put("url", "error");
        }
        return map;
    }

    /**
     * 发布
     * @param model   model
     * @param title   标题
     * @param content 内容
     * @return 重定向到文章详情
     */
    @RequestMapping(value = "/publish", method = RequestMethod.POST)
    public String postAnArticle(Model model,
                                @RequestParam(value = "thematic") String thematic,
                                @RequestParam(value = "title") String title,
                                @RequestParam(value = "content") String content) {
        if (title == null || title.length() <= 0 || content == null || content.length() <= 0) return "error";
        BL_Article bl_article = new BL_Article();
        Document doc = Jsoup.parse(content);
        String bodyHtml = doc.body() != null ? doc.body().html() : content;
        bl_article.setB1_thematic(thematic);
        bl_article.setB1_subject(title);
        bl_article.setB1_content(bodyHtml);
        bl_articleService.insertOneArticle(bl_article);
        Integer b1_id = bl_article.getB1_id();
        if (b1_id == null || b1_id <= 0) return "error";
        model.addAttribute("b1_id", b1_id);
        return "redirect:/blog_post?b1_id=" + b1_id.toString();
    }


    /**
     * 添加新评论
     * @param model
     * @param articleId
     * @param content
     * @return
     */
    @Transactional
    @RequestMapping(value = "/newComment", method = RequestMethod.POST)
    public String insertNewComment(Model model,
                                                         @RequestParam(value = "articleId") String articleId,
                                                         @RequestParam(value = "content") String content,
                                   @RequestParam(value = "parentCommentId") String parentCommentId){
        if(articleId == null || articleId.length() <= 0 || content == null || content.length() <= 0) return null;
        // 插入评论
        BL_Comment bl_comment = new BL_Comment();
        bl_comment.setB1_id(Integer.parseInt(articleId));
        bl_comment.setB2_content(content);
        bl_comment.setB0_id(0);
        bl_comment.setB0_name("Crucio");
        if(parentCommentId != null && parentCommentId.length() > 0 && Integer.parseInt(parentCommentId) != -1)
            bl_comment.setB2_pid(Integer.parseInt(parentCommentId));
        bl_commentService.insertNewComment(bl_comment);
        // 获取新添加的评论id
        Integer newCommentId = bl_comment.getB2_id();
        if(newCommentId == null || newCommentId <=0) return null;
        //查询当前文章下的评论信息
        BL_CommentVO queryCommentVO = new BL_CommentVO();
        queryCommentVO.setB1_id(Integer.parseInt(articleId));
        List<BL_CommentVO> comments = bl_commentService.queryComments(queryCommentVO);
        model.addAttribute("comments", comments);
        return "comments";
    }

    /**
     * 根据ID查询评论
     * @param commentId 评论ID
     * @return 评论实体
     */
    @ResponseBody
    @RequestMapping(value = "/comment", method = RequestMethod.POST)
    public BL_CommentVO queryCommentById(@RequestParam(value = "commentId") String commentId){
        if(commentId == null || commentId.length() <= 0) return null;
        return bl_commentService.queryCommentById(Integer.parseInt(commentId));
    }

    @RequestMapping(value = "/testCropper")
    public String testCropper(Model model, HttpServletRequest request) {
        return "test/testcropper";
    }
}
