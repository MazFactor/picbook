package com.lyl.web.controller;

import org.apache.commons.io.FileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import java.util.HashMap;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.File;

@Controller
public class FileDealerController {

    // 日志logger
    private Logger logger = LoggerFactory.getLogger(FileDealerController.class);

//    @Resource(name = "userFileService")
//    private FileDealerService userFileService;

    @RequestMapping(value = "/uploadfiles", method = RequestMethod.POST)
    @ResponseBody
    public Map<String, String> uploadImage(@RequestParam(value="thumbnail") MultipartFile multipart, HttpServletRequest request, HttpServletResponse response) throws IllegalStateException, IOException {
        //返回信息
        Map<String, String> map = new HashMap<>();
        StringBuffer realUrlOnServer = request.getRequestURL();

        //可以在上传文件的同时接收其它参数
        //logger.info("收到用户[" + gameId + "]的文件上传请求");
        //如果用的是Tomcat服务器，则文件会上传到\\%TOMCAT_HOME%\\webapps\\YourWebProject\\upload\\文件夹中
        //这里实现文件上传操作用的是commons.io.FileUtils类,它会自动判断/upload是否存在,不存在会自动创建
        String realPath = request.getSession().getServletContext().getRealPath("/uploadfiles");
        //设置响应给前台内容的数据格式
        response.setContentType("text/plain; charset=UTF-8");
        //上传文件的原名(即上传前的文件名字)
        String originalFilename;
        //如果只是上传一个文件,则只需要MultipartFile类型接收文件即可,而且无需显式指定@RequestParam注解
        //如果想上传多个文件,那么这里就要用MultipartFile[]类型来接收文件,并且要指定@RequestParam注解
        //上传多个文件时,前台表单中的所有<input type="file"/>的name都应该是myfiles,否则参数里的myfiles无法获取到所有上传的文件
//        for(MultipartFile myfile : myfiles){
            originalFilename = multipart.getOriginalFilename();
            logger.info("文件原名: " + originalFilename);
            logger.info("文件名称: " + multipart.getName());
            logger.info("文件长度: " + multipart.getSize());
            logger.info("文件类型: " + multipart.getContentType());
            logger.info("========================================");
            try {
                //这里不必处理IO流关闭的问题,因为FileUtils.copyInputStreamToFile()方法内部会自动把用到的IO流关掉
                //此处也可以使用Spring提供的MultipartFile.transferTo(File dest)方法实现文件的上传
                FileUtils.copyInputStreamToFile(multipart.getInputStream(), new File(realPath, originalFilename));
                realUrlOnServer.append("/");
                realUrlOnServer.append(originalFilename);
            } catch (IOException e) {
                logger.info("文件[" + originalFilename + "]上传失败,堆栈轨迹如下");
                e.printStackTrace();
//                throw e;
            }
//        }
        map.put("url", realUrlOnServer.toString());
        return map;
    }
}

