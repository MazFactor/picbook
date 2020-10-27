//package com.lyl.web.controller;
//
//import com.github.pagehelper.PageInfo;
//import com.jinghuan.common.util.SftpClient;
//import com.lyl.web.service.impl.Bl_ArticleService;
//import com.lyl.web.service.Bl_ArticleService;
//import UploadFileService;
//import BL_Article;
//import BL_ArticleIndexVO;
//import net.coobird.thumbnailator.Thumbnails;
//import org.jsoup.Jsoup;
//import org.jsoup.nodes.Document;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Controller;
//import org.springframework.ui.Model;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RequestMethod;
//import org.springframework.web.bind.annotation.RequestParam;
//import org.springframework.web.bind.annotation.ResponseBody;
//import org.springframework.web.multipart.MultipartFile;
//
//import javax.servlet.http.HttpServletRequest;
//import java.io.File;
//import java.io.IOException;
//import java.util.HashMap;
//import java.util.Map;
//import java.util.Properties;
//
//@Controller
////@RequestMapping(value = "/")
//public class Bl_ArticleController {
//    /**
//     * 日志
//     */
////    private static final ZLSimpleLogger logger = ZLoggerFactory.getSimpleLogger(Bl_ArticleController.class);
//    /**
//     * 配置文件
//     */
//    private static final Properties PROPERTIES = new Properties();
//
//    /**
//     * 加载配置信息
//     */
//    static {
//        try {
//            PROPERTIES.load(Bl_ArticleController.class.getClassLoader().getResourceAsStream("ftp-tron.properties"));
//        } catch (Exception e) {
//            System.out.println(e.getMessage());
////            logger.error(e.getMessage(), e);
//        }
//    }
//
//    @Autowired
//    private Bl_ArticleService bl_articleService;
//    @Autowired
//    private UploadFileService uploadFileService;
//    @Autowired
//    private Bl_ArticleService downloadFileService;
//
//    /**
//     * 访问首页
//     *
//     * @param model   模型
//     * @param request 请求
//     * @return 返回值
//     */
//    @RequestMapping(value = "/index")
//    public String findBlArticleById(Model model, HttpServletRequest request) {
//        BL_ArticleIndexVO bl_articleIndexVO = new BL_ArticleIndexVO();
////        PageInfo<BL_ArticleIndexVO> articleList = bl_articleService.queryPage(bl_articleIndexVO);
////        model.addAttribute("articlePages", articleList);
//        return "index";
//    }
//
//    /**
//     * 加载更多（分页）
//     *
//     * @param pageNum 页码
//     * @return 指定页内容
//     */
//    @RequestMapping(value = "/searchPage", method = RequestMethod.POST)
//    @ResponseBody
//    public PageInfo<BL_ArticleIndexVO> searchPage(@RequestParam String pageNum) {
//        PageInfo<BL_ArticleIndexVO> pageList = null;
//        BL_ArticleIndexVO query = new BL_ArticleIndexVO();
//        query.setPage(Integer.parseInt(pageNum)); // 仅指定页码，每页数量在BaseEntity中指定
//        try {
////            pageList = bl_articleService.queryPage(query);
//        } catch (Exception e) {
//            System.out.println(e.getMessage());
////            logger.error(e.getMessage(), e);
//        }
//        return pageList;
//    }
//
//    /**
//     * 写文章
//     *
//     * @param model   模型
//     * @param request 请求
//     * @return 返回值
//     */
//    @RequestMapping(value = "/write")
//    public String go2write(Model model, HttpServletRequest request) {
//        return "write_tinymce_markdownime";
////        return "write_editormd";
//    }
//
//    /**
//     * 发布文章
//     *
//     * @param model   model
//     * @param title   标题
//     * @param content 内容
//     * @return 重定向到文章详情
//     */
//    @RequestMapping(value = "/submit", method = RequestMethod.POST)
//    public String postAnArticle(Model model,
//                                @RequestParam(value = "title") String title,
//                                @RequestParam(value = "content") String content) {
//        if (title == null || title.length() <= 0 || content == null || content.length() <= 0) return "error";
//        BL_Article bl_article = new BL_Article();
//        Document doc = Jsoup.parse(content);
//        String bodyHtml = doc.body() != null ? doc.body().html() : content;
//        bl_article.setB1_subject(title);
//        bl_article.setB1_content(bodyHtml);
//        bl_articleService.insertOneArticle(bl_article);
//        Integer b1_id = bl_article.getB1_id();
//        if (b1_id == null || b1_id <= 0) return "error";
//        model.addAttribute("b1_id", b1_id);
//        return "redirect:/detail?b1_id=" + b1_id.toString();
//    }
//
//    /**
//     * 图片上传（至sftp）
//     *
//     * @param multipartFile 图片文件
//     * @param request       请求
//     * @return 含图片地址信息的集合
//     */
//    @RequestMapping(value = "/uploadImage1", method = RequestMethod.POST)
//    @ResponseBody
//    public Map<String, String> uploadImage(@RequestParam(value = "thumbnail") MultipartFile multipartFile, HttpServletRequest request) {
//        SftpClient sftpClient = new SftpClient();
//        // 返回信息
//        Map<String, String> map = new HashMap<>();
//        // 服务端根目录
//        String resourceImageRealPath = request.getSession().getServletContext().getRealPath("/");
//        String fileParent = PROPERTIES.getProperty("ftp.zuck.fileparent");
//        // 图片名称
//        String imageName;
//        try {
//            imageName = uploadFileService.uploadTinyImage(resourceImageRealPath, fileParent, multipartFile);
//            map.put("url", fileParent + "/" + imageName);
//        }catch (Exception e){
//            System.out.println(e.getMessage());
////            logger.error(e.getMessage(), e);
//            map.put("url", "error");
//        }
//        return map;
//    }
//
//    /**
//     * 文章详情
//     *
//     * @param model   model
//     * @param request request
//     * @return 返回值
//     */
//    @RequestMapping(value = "/detail", method = RequestMethod.GET)
//    public String checkArticleDetail(Model model, HttpServletRequest request) {
//        String param = request.getParameter("b1_id");
//        BL_Article targetArticle;
//        // 图片缓存路径
//        String targetImagePath = request.getSession().getServletContext().getRealPath("/") + PROPERTIES.getProperty("ftp.zuck.fileparent");
//        File file = new File(targetImagePath);
//        // 图片ftp存储路径
//        String ftpImageParent = PROPERTIES.getProperty("ftp.zuck.fileparent");
//        // 创建缓存路径
//        if(!file.exists())
//            file.mkdirs();
//        // 文章详情
//        if (param != null && !param.isEmpty() && Integer.parseInt(param) > 0) {
//            targetArticle = bl_articleService.findBlArticleById(Integer.parseInt(param));
//            if(targetArticle != null) {
//                // 下载文件中的图片到本地
//                if (downloadFileService.downloadArticleImages(ftpImageParent, targetArticle.getB1_content(), targetImagePath))
//                    model.addAttribute("article", targetArticle);
//                else
//                    System.out.println("文章图片下载失败！");
////                    logger.error("文章图片下载失败！");
//            }
//            else
//                return "error";
//        } else {
//            return "error";
//        }
//        return "detail";
//    }
//
//    /**
//     * 重新生成图片宽、高
//     *
//     * @param srcPath   图片路径
//     * @param destPath  新生成的图片路径
//     * @param newWith   新的宽度
//     * @param newHeight 新的高度
//     * @param forceSize 是否强制使用指定宽、高,false:会保持原图片宽高比例约束
//     * @return
//     * @throws IOException
//     */
//    public static boolean resizeImage(String srcPath, String destPath, int newWith, int newHeight, boolean forceSize) throws IOException {
//        if (forceSize) {
//            Thumbnails.of(srcPath).forceSize(newWith, newHeight).toFile(destPath);
//
//        } else {
//            Thumbnails.of(srcPath).width(newWith).height(newHeight).toFile(destPath);
//        }
//        return true;
//    }
//}
