package com.lyl.common.util;


import com.lyl.common.exception.ApplicationException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

/**
 * 文件工具类
 *
 * @author WRQ
 * @date 2019/3/20
 * @since 1.0.0
 */
public class FileUtil {

    private final static Logger logger = LoggerFactory.getLogger(FileUtil.class);

    /**
     * 文件存放的根路径
     */
    private static final String FILE_PATH_ROOT = System.getProperty("java.io.tmpdir") + "/gw/fileCache/";

    /**
     * 将上传的文件写入缓存文件并保存
     *
     * @param filePath 文件路径
     * @param file     上传文件
     * @return 缓存文件
     * @throws IOException IO异常
     */
    public static File saveFile(String filePath, MultipartFile file) throws IOException {
        // 得到文件对象
        File fileCache = new File(FILE_PATH_ROOT + filePath + StringUtil.STR_FILE_SEPARATOR + file.getOriginalFilename());
        // 判断文件所在的目录是否存在，若不存在则创建
        if (!fileCache.getParentFile().exists()) {
            fileCache.getParentFile().mkdirs();
        }
        // 写文件
        file.transferTo(fileCache);
        return fileCache;
    }

    /**
     * 将指定内容写入缓存文件并保存
     *
     * @param filePath 文件路径
     * @param text     文件内容
     * @throws IOException IO异常
     */
    public static void saveFile(String filePath, String text) throws IOException {
        // 得到文件对象
        File file = new File(FILE_PATH_ROOT + filePath);
        // 判断文件所在的目录是否存在，若不存在则创建
        if (!file.getParentFile().exists()) {
            file.getParentFile().mkdirs();
        }
        // 写文件
        OutputStreamWriter writerStream = new OutputStreamWriter(new FileOutputStream(file), "UTF-8");
        BufferedWriter bufferedWriter = null;
        try {
            bufferedWriter = new BufferedWriter(writerStream);
            bufferedWriter.write(text);
        }catch (Exception e){
            logger.error(e.getMessage());
        }finally {
            if (null != bufferedWriter){
                bufferedWriter.close();
            }
            writerStream.close();
        }
    }

    /**
     * 将上传的文件写入缓存文件并保存
     *
     * @param filePath 文件路径
     * @param file     上传文件
     * @return 缓存文件
     * @throws IOException IO异常
     */
    public static File uploadFile(String filePath, MultipartFile file) throws IOException {
        // 得到文件对象
        File fileCache = new File(filePath+ StringUtil.STR_FILE_SEPARATOR + file.getOriginalFilename());
        // 判断文件所在的目录是否存在，若不存在则创建
        if (!fileCache.getParentFile().exists()) {
            fileCache.getParentFile().mkdirs();
        }
        // 写文件
        file.transferTo(fileCache);
        return fileCache;
    }

    /**
     * 将上传的文件写入缓存文件并保存
     *
     * @param filePath 文件路径
     * @param file     上传文件
     * @param newFileName 文件重新命名
     * @return 缓存文件
     * @throws IOException IO异常
     */
    public static File uploadFile(String filePath, MultipartFile file, String newFileName) throws IOException {
        // 得到文件对象
        File fileCache = new File(filePath+ StringUtil.STR_FILE_SEPARATOR + newFileName);
        // 判断文件所在的目录是否存在，若不存在则创建
        if (!fileCache.getParentFile().exists()) {
            fileCache.getParentFile().mkdirs();
        }
        // 写文件
        file.transferTo(fileCache);
        return fileCache;
    }

    /**
     * 压缩文件夹至缓存目录
     *
     * @param out      输出流
     * @param fileName 文件或文件夹名
     */
    public static void compressFileToZip(OutputStream out, String fileName) {
        ZipOutputStream zos = null;
        try {
            zos = new ZipOutputStream(out);
            File sourceFile = new File(FILE_PATH_ROOT + fileName);
            compress(zos, sourceFile, sourceFile.getName());
        } catch (Exception e) {
            throw new ApplicationException("Failed to compress folder to zip.");
        } finally {
            if (zos != null) {
                try {
                    zos.close();
                } catch (IOException e) {
                    logger.error(e.getMessage());
                }
            }
        }
    }

    /**
     * 删除缓存文件
     *
     * @param fileName 文件或文件夹
     */
    public static void delete(String fileName) {
        File sourceFile = new File(FILE_PATH_ROOT + fileName);
        if (sourceFile.isFile()) {
            if (!sourceFile.delete()){
                logger.error("file delete failed");
            }
        } else {
            File[] listFiles = sourceFile.listFiles();
            for (int i = 0; i < listFiles.length; i++) {
                if (!listFiles[i].delete()){
                    logger.error("file delete failed");
                }
            }
            if (!sourceFile.delete()){
                logger.error("file delete failed");
            }
        }
    }

    private static void compress(ZipOutputStream zos, File sourceFile, String fileName) throws Exception {
        byte[] buf = new byte[1024];
        if (sourceFile.isFile()) {
            // 向zip输出流中添加一个zip实体，name为zip实体的文件的名字
            zos.putNextEntry(new ZipEntry(fileName));
            // copy文件到zip输出流中
            FileInputStream in = null;
            try{
                in = new FileInputStream(sourceFile);
                int len;
                while ((len = in.read(buf)) != -1) {
                    zos.write(buf, 0, len);
                }
                zos.closeEntry();
            }catch (Exception e){
                logger.error(e.getMessage());
            }finally {
                if (null != in){
                    in.close();
                }
            }
        } else {
            File[] listFiles = sourceFile.listFiles();
            if (listFiles == null || listFiles.length == 0) {
                // 空文件夹的处理
                zos.putNextEntry(new ZipEntry(fileName + StringUtil.STR_SLASH));
                zos.closeEntry();
            } else {
                for (File file : listFiles) {
                    // 注意：file.getName()前面需要带上父文件夹的名字加一斜杠,
                    // 不然最后压缩包中就不能保留原来的文件结构,即：所有文件都跑到压缩包根目录下了
                    compress(zos, file, fileName + StringUtil.STR_SLASH + file.getName());
                }
            }
        }
    }

    /**
     * 文件下载
     * @param response
     * @param fileName
     */
    public static void download( HttpServletResponse response, String filePath, String fileName){
        //通过文件的保存文件夹路径加上文件的名字来获得文件
        //File file=new File("/data/",fileName);
        File file = new File(filePath+ StringUtil.STR_FILE_SEPARATOR + fileName);
        //当文件存在
        if(file.exists()){
            //首先设置响应的内容格式是force-download，那么你一旦点击下载按钮就会自动下载文件了
            response.setContentType("application/force-download");
            //通过设置头信息给文件命名，也即是，在前端，文件流被接受完还原成原文件的时候会以你传递的文件名来命名
            response.addHeader("Content-Disposition",String.format("attachment; filename=\"%s\"", file.getName()));
            //进行读写操作
            byte[] buffer=new byte[1024];
            FileInputStream fis=null;
            BufferedInputStream bis=null;
            try{
                fis=new FileInputStream(file);
                bis=new BufferedInputStream(fis);
                OutputStream os=response.getOutputStream();
                //从源文件中读
                int i=bis.read(buffer);
                while(i!=-1){
                    //写到response的输出流中
                    os.write(buffer,0,i);
                    i=bis.read(buffer);
                }
            }catch (IOException e){
                logger.error(e.getMessage());
            }finally {
                //善后工作，关闭各种流
                try {
                    if(bis!=null){
                        bis.close();
                    }
                    if(fis!=null){
                        fis.close();
                    }
                } catch (IOException e) {
                    logger.error(e.getMessage());
                }
            }
        }
    }

    /**
     * 二进制转文件
     * @param byteArray
     * @param targetPath
     */
    public static void readBin2Image(byte[] byteArray, String targetPath) {
        InputStream in = new ByteArrayInputStream(byteArray);
        File file = new File(targetPath);
        String path = targetPath.substring(0, targetPath.lastIndexOf("/"));
        if (!file.exists()) {
            new File(path).mkdir();
        }
        FileOutputStream fos = null;
        try {
            fos = new FileOutputStream(file);
            int len = 0;
            byte[] buf = new byte[1024];
            while ((len = in.read(buf)) != -1) {
                fos.write(buf, 0, len);
            }
            fos.flush();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (null != fos) {
                try {
                    fos.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

}
