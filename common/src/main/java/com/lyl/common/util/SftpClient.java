package com.lyl.common.util;

import com.jcraft.jsch.*;
import org.apache.poi.util.IOUtils;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.util.Vector;

/**
 * Created by zhenghao on 2018/9/18.
 */
public class SftpClient {
    /**
     * 日志
     */
//    private static final ZLSimpleLogger logger = ZLoggerFactory.getSimpleLogger(SftpClient.class);

    private ChannelSftp sftp;

    private Session session;
    /**
     * SFTP 登录用户名
     */
    private String username;
    /**
     * SFTP 登录密码
     */
    private String password;
    /**
     * 私钥
     */
    private String privateKey;
    /**
     * SFTP 服务器地址IP地址
     */
    private String host;
    /**
     * SFTP 端口
     */
    private int port;


    /**
     * 构造基于密码认证的sftp对象
     */
    public SftpClient(String username, String password, String host, int port) {
        this.username = username;
        this.password = password;
        this.host = host;
        this.port = port;
    }

    /**
     * 构造基于秘钥认证的sftp对象
     */
    public SftpClient(String username, String host, int port, String privateKey) {
        this.username = username;
        this.host = host;
        this.port = port;
        this.privateKey = privateKey;
    }

    public SftpClient() {
    }


    /**
     * 连接sftp服务器
     */
    public void login() {
        try {
            JSch jsch = new JSch();
            if (privateKey != null) {
                jsch.addIdentity(privateKey);// 设置私钥
            }
            session = jsch.getSession(username, host, port);
            if (password != null) {
                session.setPassword(password);
            }
            Properties config = new Properties();
            config.put("StrictHostKeyChecking", "no");
            session.setConfig(config);
            session.connect();
            Channel channel = session.openChannel("sftp");
            channel.connect();
            sftp = (ChannelSftp) channel;
        } catch (JSchException e) {
//            logger.error(e.getMessage(), e);
        }
    }

    /**
     * 关闭连接 server
     */
    public void logout() {
        if (sftp != null) {
            if (sftp.isConnected()) {
                sftp.disconnect();
            }
        }
        if (session != null) {
            if (session.isConnected()) {
                session.disconnect();
            }
        }
    }

    /**
     * 将输入流的数据上传到sftp作为文件。文件完整路径=basePath+directory
     * @param directory    上传到该目录
     * @param sftpFileName sftp端文件名
     */
    public boolean uploadToFtp(String directory, String sftpFileName, InputStream input) throws SftpException {
            if (directory != null && !"".equals(directory)) {
                sftp.cd(directory);
            }
            sftp.put(input, sftpFileName);  //上传文件
            return true;

    }

    /**
     * 切换路径
     * @param directory
     * @throws SftpException
     */
    public void cd(String directory) throws SftpException {
        if (directory != null && !"".equals(directory) && !"/".equals(directory)) {
            sftp.cd(directory);
        }

    }

    /**
     * 下载文件。
     *
     * @param directory    下载目录
     * @param downloadFile 下载的文件
     * @param saveFile     存在本地的路径
     */
    public void download(String directory, String downloadFile, String saveFile) {
        System.out.println("download:" + directory + " downloadFile:" + downloadFile + " saveFile:" + saveFile);

        File file = null;
        try {
            if (directory != null && !"".equals(directory)) {
                sftp.cd(directory);
            }
            file = new File(saveFile);
            sftp.get(downloadFile, new FileOutputStream(file));
        } catch (SftpException | FileNotFoundException e) {
            e.printStackTrace();
            if (file != null) {
                if(file.delete())
                    System.out.println("缓存文件删除成功！");
//                    logger.info("缓存文件删除成功！");
                else
                    System.out.println("缓存文件删除失败！");
//                    logger.info("缓存文件删除失败！");
            }
        }

    }

    /**
     * 下载文件
     *
     * @param directory    下载目录
     * @param downloadFile 下载的文件名
     * @return 字节数组
     */
    public byte[] download(String directory, String downloadFile) throws SftpException, IOException {
        if (directory != null && !"".equals(directory)) {
            sftp.cd(directory);
        }
        InputStream is = sftp.get(downloadFile);
        byte[] fileData = IOUtils.toByteArray(is);
        return fileData;
    }


    /**
     * 删除文件
     *
     * @param directory  要删除文件所在目录
     * @param deleteFile 要删除的文件
     */
    public void delete(String directory, String deleteFile) throws SftpException {
        if (directory != null && !"".equals(directory)) {
            sftp.cd(directory);
        }
        sftp.rm(deleteFile);
    }


    /**
     * 列出目录下的文件
     *
     * @param directory 要列出的目录
     */
    public Vector<?> listFiles(String directory) throws SftpException {
        return sftp.ls(directory);
    }

    public boolean isExistsFile(String directory, String fileName) {

        List<String> findFilelist = new ArrayList();
        ChannelSftp.LsEntrySelector selector = new ChannelSftp.LsEntrySelector() {
            @Override
            public int select(ChannelSftp.LsEntry lsEntry) {
                if (lsEntry.getFilename().equals(fileName)) {
                    findFilelist.add(fileName);
                }
                return 0;
            }
        };

        try {
            sftp.ls(directory, selector);
        } catch (SftpException e) {
            e.printStackTrace();
        }

        if (findFilelist.size() > 0) {
            return true;
        } else {
            return false;
        }
    }

    //上传文件测试
    public static void main(String[] args) throws SftpException, IOException {
        SftpClient sftp = new SftpClient("ftpuser", "ftpuser", "10.20.125.128", 22);
        sftp.login();
        File file = new File("C:\\Users\\GW00165883\\Pictures\\409167387-167a422670ccd6e9.jpg");
        InputStream is = new FileInputStream(file);

        sftp.uploadToFtp("/home/ftpuser/images", "2.png", is);
        sftp.logout();
    }
}

