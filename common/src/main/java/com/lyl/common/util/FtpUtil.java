package com.lyl.common.util;


import com.lyl.common.factory.FtpClientFactory;
//import com.jinghuan.zlog.ZLSimpleLogger;
//import com.jinghuan.zlog.ZLoggerFactory;
import org.apache.commons.net.ftp.FTP;
import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPFile;

import java.io.*;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

/**
 * FTP工具类
 *
 * @author WRQ
 * @date 2019/4/16
 * @since 1.0.0
 */
public class FtpUtil {

    /**
     * 日志
     */
//    private static ZLSimpleLogger logger = ZLoggerFactory.getSimpleLogger(FtpUtil.class);
    /**
     * 配置文件
     */
    private static final Properties PROPERTIES = new Properties();

    /**
     * 加载配置信息
     */
    static {
        try {
            PROPERTIES.load(FtpUtil.class.getClassLoader().getResourceAsStream("ftp-tron.properties"));
        } catch (Exception e) {
            System.out.println(e.getMessage());
//            logger.error(e.getMessage(), e);
        }
    }

    /**
     * 字符集：GBK
     */
    public static final String CHARSET_TYPE_GBK = "GBK";
    /**
     * 字符集：UTF-8
     */
    public static final String CHARSET_TYPE_UTF = "UTF-8";
    /**
     * 文件扩展名：txt
     */
    public static final String FILE_EXTENSION_TXT = ".TXT";

    /**
     * 下载FTP指定路径下的所有文件到本地指定路径，并将文件移动到备份路径
     *
     * @param ftpPath   FTP服务器路径
     * @param localPath 本地缓存路径
     * @param bakPath   FTP服务器备份路径
     * @return 文件名称列表
     */
    public static List<String> downloadFile(String ftpPath, String localPath, String bakPath) {
        List<String> fileNames = new ArrayList<>();
        // 获取FTP客户端
        FTPClient ftpClient = FtpClientFactory.getFtpClient();
        FileOutputStream fos = null;
        try {
            // 设置被动模式
            ftpClient.enterLocalPassiveMode();
            // 设置以二进制流的方式传输
            ftpClient.setFileType(FTP.BINARY_FILE_TYPE);
            // 设置缓冲区大小
            ftpClient.setBufferSize(1024 * 1024);
            // 递归目标目录
            FTPFile[] fs = ftpClient.listFiles(ftpPath);
            for (FTPFile ff : fs) {
                if (StringUtil.STR_PERIOD.equals(ff.getName()) || (StringUtil.STR_PERIOD + StringUtil.STR_PERIOD).equals(ff.getName())) {
                    continue;
                }
                fileNames.add(ff.getName());
                // 创建本地路径下的缓存文件
                File localFile = new File(localPath, ff.getName());
                // 判断文件所在的目录是否存在，若不存在则创建
                if (!localFile.getParentFile().exists()) if (!localFile.getParentFile().mkdirs())
                    System.out.println("ftp创建目录失败！");
//                    logger.error("ftp创建目录失败！");
                fos = new FileOutputStream(localFile);
                /* 若成功缓存文件到本地则移动文件到备份路径 */
                if (ftpClient.retrieveFile(ftpPath + StringUtil.STR_SLASH + ff.getName(), fos)) {
                    ftpClient.rename(ftpPath + StringUtil.STR_SLASH + ff.getName(), bakPath + StringUtil.STR_SLASH + ff.getName());
                }
            }
        } catch (IOException e) {
            System.out.println(e.getMessage());
//            logger.error(e.getMessage(), e);
        } finally {
            try {
                if (fos != null) {
                    fos.close();
                }
                ftpClient.logout();
            } catch (IOException e) {
                System.out.println(e.getMessage());
//                logger.error(e.getMessage(), e);
            }
        }
        return fileNames;
    }

    /**
     * 上传文件到指定FTP目录
     *
     * @param path 目标目录
     * @param fileName 文件名称
     * @param input 文件输入流
     * @return 上传结果
     */
    public static boolean uploadFile(String path, String fileName, InputStream input) {
        boolean success = false;
        // 获取FTP客户端
        FTPClient ftpClient = FtpClientFactory.getFtpClient();
        try {
            ftpClient.setControlEncoding(CHARSET_TYPE_UTF);
            ftpClient.setFileTransferMode(FTPClient.BINARY_FILE_TYPE);
            ftpClient.enterLocalPassiveMode();
            if(!ftpClient.changeWorkingDirectory(path)){
                ftpClient.mkd(path);
            }else {
                ftpClient.changeWorkingDirectory(path);
            }
            ftpClient.storeFile(fileName, input);
            // 判断文件是否存，如果存在则返回 true
            FTPFile[] fs = ftpClient.listFiles(path);
            for (FTPFile ff : fs) {
                if (fileName.equals(ff.getName())) {
                    success = true;
                    break;
                }
            }
            // success = true;
        } catch (IOException e) {
            System.out.println(e.getMessage());
//            logger.error(e.getMessage(), e);
        } finally {
            try {
                if (input != null) {
                    input.close();
                }
                ftpClient.logout();
            } catch (IOException e) {
                System.out.println(e.getMessage());
//                logger.error(e.getMessage(), e);
            }
        }
        return success;
    }

    /**
     * sftp上传
     * @param path 目标目录（相对ftp根目录）
     * @param file 文件
     * @return true：成功；false：失败
     */
    public static boolean uploadFileBySftp(String path, File file) {
        FileInputStream inputStream = null;
        // 获取FTP客户端
        SftpClient sftpClient = FtpClientFactory.getSftpClient();
        String sftpWorkPath = PROPERTIES.getProperty("ftp.zuck.root") + "/" + path;
        try {
            inputStream = new FileInputStream(file);
            boolean uploadResult = sftpClient.uploadToFtp(sftpWorkPath, file.getName(), inputStream);
            if(uploadResult)
                System.out.println("上传ftp成功！");
//                logger.info("上传ftp成功！");
            else
                System.out.println("上传ftp失败！");
//                logger.info("上传ftp失败！");
        } catch (Exception e) {
            System.out.println(e.getMessage());
//            logger.error(e.getMessage(), e);
            return false;
        }finally {
            try {
                if (inputStream != null) {
                    inputStream.close();
                }
                // 关闭ftp连接
                sftpClient.logout();
            } catch (IOException e) {
                System.out.println(e.getMessage());
//                logger.error(e.getMessage(), e);
            }
        }
        return true;
    }

    /**
     * ftp上传
     * @param path 目标目录（相对ftp根目录）
     * @param file 文件
     * @return true：成功；false：失败
     */
    public static boolean uploadToFtp(String path, File file) {
        FileInputStream inputStream = null;
        // 获取FTP客户端
        FTPClient ftpClient = FtpClientFactory.getFtpClient();
        try {
            inputStream = new FileInputStream(file);
            if(!ftpClient.changeWorkingDirectory(path)){
                ftpClient.mkd(path);
            }else {
                ftpClient.changeWorkingDirectory(path);
            }
            ftpClient.setControlEncoding("UTF-8");
            ftpClient.setFileType(FTP.BINARY_FILE_TYPE);
            ftpClient.enterLocalPassiveMode();
            ftpClient.storeFile(file.getName(), new FileInputStream(file));
        } catch (IOException e) {
            System.out.println(e.getMessage());
//            logger.error(e.getMessage(), e);
            return false;
        }finally {
            try {
                if (inputStream != null) {
                    inputStream.close();
                }
                // 关闭ftp连接
                ftpClient.logout();
            } catch (IOException e) {
                System.out.println(e.getMessage());
//                logger.error(e.getMessage(), e);
            }
        }
        return true;
    }

    /**
     * 读取本地文件内容
     *
     * @param filePath 文件路径（包括名称）
     * @return 文件内容
     */
    public static String readFile(String filePath) {
        StringBuilder result = new StringBuilder();
        InputStreamReader isr = null;
        BufferedReader br = null;
        try {
            File file = new File(filePath);
            isr = new InputStreamReader(new FileInputStream(file), CHARSET_TYPE_GBK);
            br = new BufferedReader(isr);
            String s;
            // 使用readLine方法逐行读取
            while ((s = br.readLine()) != null) {
                if (result.length() != 0) {
                    result.append(StringUtil.STR_LINE_SEPARATOR);
                }
                result.append(s);
            }
        } catch (IOException e) {
//            logger.error(e.getMessage(), e);
            System.out.println(e.getMessage());
        } finally {
            try {
                if (isr != null) {
                    isr.close();
                }
                if (br != null) {
                    br.close();
                }
            } catch (IOException e) {
//                logger.error(e.getMessage(), e);
                System.out.println(e.getMessage());
            }
        }
        return result.toString();
    }

    /**
     * 删除本地文件
     *
     * @param filePath 被删除文件的文件名
     * @return 删除结果
     */
    public static boolean deleteFile(String filePath) {
        boolean flag = false;
        File file = new File(filePath);
        // 路径为文件且不为空则进行删除
        if (file.isFile() && file.exists()) {
            file.delete();
            flag = true;
        }
        return flag;
    }

    /**
     * @Description //TODO 写txt文件
     * @Date 14:17 2020/1/15
     * @Param [musicInfo]
     * @return void
     **/
    public static void writeToText(String fileName, List<Object> list) throws IOException, IllegalAccessException {
        String path = fileName + FILE_EXTENSION_TXT;
        File file = new File(path);
        if(!file.exists()){
            file.getParentFile().mkdirs();
        }
        file.createNewFile();
        // write
        FileWriter fw = new FileWriter(file, true);
        BufferedWriter bw = new BufferedWriter(fw);
        for (Object obj:list){
            StringBuilder builder = new StringBuilder();
            Class cls = obj.getClass();
            Field[] fields = cls.getDeclaredFields();
            for(int i=0; i<fields.length; i++){
                Field f = fields[i];
                f.setAccessible(true);
                if (i!=0 && null == f.get(obj)) {
                    builder.append(StringUtil.STR_TAB);
                }else {
                    builder.append(f.get(obj));
                    builder.append(StringUtil.STR_TAB);
                }
            }
            bw.write(builder.toString()+"\r\n");
            bw.flush();
        }
        bw.close();
        fw.close();
    }

    /**
     * 下载sftp文件
     * @param newFileName 新文件名称
     * @param src 文件路径
     * @param fileName 文件名称
     * @param des 下载到本地的路径
     * @throws Exception
     */
    public static void downSftpFile(String fileName,String src, String newFileName, String des) throws Exception {
        // 获取FTP客户端
        SftpClient sftpClient = FtpClientFactory.getSftpClient();
        String sftpWorkPath = PROPERTIES.getProperty("ftp.zuck.root") + "/" + src;
        byte[] fileBytes=null;
        try {
            fileBytes=sftpClient.download(sftpWorkPath, fileName);
            if(fileBytes != null){
                FileUtil.readBin2Image(fileBytes, des + "/" + newFileName);
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }

}
