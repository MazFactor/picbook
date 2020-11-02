package com.lyl.common.factory;

import com.lyl.common.enume.ErpFtpTypeEnum;
import com.lyl.common.util.SftpClient;
//import com.jinghuan.zlog.ZLSimpleLogger;
//import com.jinghuan.zlog.ZLoggerFactory;
import org.apache.commons.net.ftp.FTPClient;
import java.io.IOException;
import java.text.MessageFormat;
import java.util.Properties;

/**
 * ERP的FTP客户端工厂
 *
 * @author WRQ
 * @date 2019/4/16
 * @since 1.0.0
 */
public class FtpClientFactory {

    /**
     * 日志
     */
//    private static final ZLSimpleLogger logger = ZLoggerFactory.getSimpleLogger(FtpClientFactory.class);
    /**
     * 配置信息
     */
    private static final Properties PROPERTIES = new Properties();
    /**
     * 本地缓存文件存放的路径
     */
    private static final String LOCAL_PATH = System.getProperty("java.io.tmpdir") + "/ERP/{0}_{1}/";

    static {
        try {
            PROPERTIES.load(FtpClientFactory.class.getClassLoader().getResourceAsStream("ftp-tron.properties"));
        } catch (Exception e) {
            System.out.println(e.getMessage());
//            logger.error(e.getMessage(), e);
        }
    }

    /**
     * 得到FTP客户端
     *
     * @return FTP客户端
     */
    public static FTPClient getFtpClient() {
        FTPClient ftpClient = new FTPClient();
        try {
            ftpClient.connect(PROPERTIES.getProperty("ftp.zuck.url"), Integer.valueOf(PROPERTIES.getProperty("ftp.zuck.port")));
            ftpClient.login(PROPERTIES.getProperty("ftp.zuck.username"), PROPERTIES.getProperty("ftp.zuck.password"));
        } catch (IOException e) {
            System.out.println(e.getMessage());
//            logger.error(e.getMessage(), e);
        }
        return ftpClient;
    }

    /**
     * 得到SFTP客户端
     *
     * @return SFTP客户端
     */
    public static SftpClient getSftpClient() {
        SftpClient sftpClient = new SftpClient(
                        PROPERTIES.getProperty("ftp.zuck.username"),
                        PROPERTIES.getProperty("ftp.zuck.password"),
                        PROPERTIES.getProperty("ftp.zuck.url"),
                        Integer.valueOf(PROPERTIES.getProperty("ftp.zuck.port"))
        );
        try {
            sftpClient.login();
        } catch (Exception e) {
            System.out.println(e.getMessage());
//            logger.error(e.getMessage(), e);
        }
        return sftpClient;
    }

    /**
     * 根据工厂编码和ERP的FTP文件类型得到FTP文件路径
     *
     * @param factoryCode 工厂编码
     * @param erpFtpType ERP的FTP文件类型
     * @return FTP文件路径
     */
    public static String getFtpPath(String factoryCode, ErpFtpTypeEnum erpFtpType) {
        return PROPERTIES.getProperty("ftp.zuck.root") + MessageFormat.format(PROPERTIES.getProperty("ftp.zuck.path."+ erpFtpType.getName()),factoryCode);
    }

    /**
     * 根据工厂编码和ERP的FTP文件类型得到归档文件路径
     *
     * @param factoryCode 工厂编码
     * @param erpFtpType ERP的FTP文件类型
     * @return 归档文件路径
     */
    public static String getBakPath(String factoryCode, ErpFtpTypeEnum erpFtpType) {
        return PROPERTIES.getProperty("ftp.zuck.root") + MessageFormat.format(PROPERTIES.getProperty("ftp.zuck.bakPath." + erpFtpType.getName()),factoryCode);
    }

    /**
     * 根据工厂编码和ERP的FTP文件类型得到日志文件路径
     *
     * @param factoryCode 工厂编码
     * @param erpFtpType ERP的FTP文件类型getFtpPath
     * @return 日志文件路径
     */
    public static String getLogPath(String factoryCode, ErpFtpTypeEnum erpFtpType) {
        return PROPERTIES.getProperty("ftp.zuck.root") + MessageFormat.format(PROPERTIES.getProperty("ftp.zuck.logPath."+ erpFtpType.getName()),factoryCode);
    }

    /**
     * 根据工厂编码和ERP的FTP文件类型得到本地缓存路径
     *
     * @param factoryCode 工厂编码
     * @param erpFtpType ERP的FTP文件类型
     * @return 本地缓存路径
     */
    public static String getLocalPath(String factoryCode, ErpFtpTypeEnum erpFtpType) {
        return MessageFormat.format(LOCAL_PATH, factoryCode, erpFtpType.getName());
    }

//    public static void main(String []args){
//        System.out.println(getBakPath("F200",ErpFtpTypeEnum.BOM));
//        System.out.println(getFtpPath("F200",ErpFtpTypeEnum.MATERIAL));
//        System.out.println(getLogPath("F200",ErpFtpTypeEnum.BOM));
//        System.out.println(getLocalPath("F200",ErpFtpTypeEnum.BOM));
//    }
}
