package com.lyl.web.service.impl;

import com.lyl.common.util.FileUtil;
import com.lyl.common.util.FtpUtil;
import com.lyl.web.service.UploadFileService;
//import com.jinghuan.zlog.ZLSimpleLogger;
//import com.jinghuan.zlog.ZLoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.UUID;

@Service
public class UploadFileServiceImpl implements UploadFileService {
//    private static final ZLSimpleLogger logger = ZLoggerFactory.getSimpleLogger(UploadFileServiceImpl.class);

    @Override
    public String uploadTinyImage(String resourceReelParent, String fileParent, MultipartFile multipartFile) {
        String uploadedFileName = null;
        // 1.生成文件到本地缓存（重新命名避免重复）
        String originalFileName = multipartFile.getOriginalFilename();
        String suffix;
        // 获取本地缓存文件后缀
        if (originalFileName != null && originalFileName.length() > 0) {
            suffix = originalFileName.substring(originalFileName.lastIndexOf('.'));
        } else {
            System.out.println("本地缓存文件不存在！");
//            logger.error("本地缓存文件不存在！");
            return null;
        }
        // 本地文件缓存路径
        String fileLocalRealParent = resourceReelParent + fileParent;
        File file;
        try {
            file = FileUtil.uploadFile(fileLocalRealParent, multipartFile, UUID.randomUUID() + suffix);
        } catch (IOException e) {
            System.out.println(e.getMessage());
//            logger.error(e.getMessage(), e);
            return null;
        }
        // 2.上传至ftp服务器
        if (file != null) {
            if (FtpUtil.uploadFileBySftp(fileParent, file)) {
                System.out.println("文件" + file.getName() + "\t上传成功！");
//                logger.info("文件" + file.getName() + "\t上传成功！");
                // 3.返回文件名(编辑器显示用)
                uploadedFileName = file.getName();
                // 4.删除服务器缓存文件
                if(!file.delete()){
                    System.out.println("删除服务器缓存文件失败！");
//                    logger.error("删除服务器缓存文件失败！");
                }
            } else {
                System.out.println("文件" + file.getName() + "\t上传失败！");
//                logger.info("文件" + file.getName() + "\t上传失败！");
                return null;
            }
        } else {
            System.out.println("文件不存在，无法上传ftp！");
//            logger.info("文件不存在，无法上传ftp！");
            return null;
        }
        return uploadedFileName;
    }
}
