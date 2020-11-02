package com.lyl.common.util;


import com.lyl.common.exception.ApplicationException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESKeySpec;
import javax.crypto.spec.IvParameterSpec;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * MD5工具类
 *
 * @author WRQ
 * @date 2019/4/5
 * @since 1.0.0
 */
public class Md5Util {

    private final static Logger logger = LoggerFactory.getLogger(Md5Util.class);

    /**
     * 字符集：GB2312
     */
    private static final String CHARSET_TYPE_GB2312 = "GB2312";

    /**
     * 解密KEY
     */
    private static final String DECRYPT_KEY = "1q2a3z4w";



    /***
     * 生成32位md5码
     * @param password
     * @return
     */
    public static String md5Password(String password) {

        try {
            //得到一个信息摘要器
            MessageDigest digest = MessageDigest.getInstance("md5");
            byte[] result = digest.digest(password.getBytes());
            StringBuffer buffer = new StringBuffer();
            // 把每一个byte 做一个与运算 0xff;
            for (byte b : result) {
                // 与运算
                int number = b & 0xff;// 加盐
                String str = Integer.toHexString(number);
                if (str.length() == 1) {
                    buffer.append("0");
                }
                buffer.append(str);
            }

            // 标准的md5加密后的结果
            return buffer.toString();
        } catch (NoSuchAlgorithmException e) {
            logger.error(e.getMessage());
            return "";
        }

    }



    /**
     * 解密
     *
     * @param data 密文字符串
     * @return 明文字符串
     */
    public static String decrypt(String data) {
        try {
            byte[] bytesrc = convertHexString(data);
            Cipher cipher = Cipher.getInstance("AES/GCM/NoPadding");
            DESKeySpec desKeySpec = new DESKeySpec(DECRYPT_KEY.getBytes(CHARSET_TYPE_GB2312));
            SecretKeyFactory keyFactory = SecretKeyFactory.getInstance("DES");
            SecretKey secretKey = keyFactory.generateSecret(desKeySpec);
            IvParameterSpec iv = new IvParameterSpec(DECRYPT_KEY.getBytes(CHARSET_TYPE_GB2312));
            cipher.init(Cipher.DECRYPT_MODE, secretKey, iv);
            byte[] retByte = cipher.doFinal(bytesrc);
            // 当数据库连接字符串中包含“%”时，java.net.URLDecoder.decode转换时会报错
            String result = new String(retByte);
            result = result.replace("%", "%25");
            return result;
        } catch (Exception e) {
            throw new ApplicationException(e.getMessage());
        }
    }

    /**
     * 加密
     *
     * @param data 明文字符串
     * @return 密文字符串
     */
    public static String encrypt(String data) {
        try {
            Cipher cipher = Cipher.getInstance("AES/GCM/NoPadding");
            DESKeySpec desKeySpec = new DESKeySpec(DECRYPT_KEY.getBytes(CHARSET_TYPE_GB2312));
            SecretKeyFactory keyFactory= SecretKeyFactory.getInstance("DES");
            SecretKey secreKey = keyFactory.generateSecret(desKeySpec);
            IvParameterSpec iv = new IvParameterSpec(DECRYPT_KEY.getBytes(CHARSET_TYPE_GB2312));
            cipher.init(Cipher.ENCRYPT_MODE, secreKey, iv);
            return new String(cipher.doFinal(data.getBytes(CHARSET_TYPE_GB2312)));
        } catch (Exception e) {
            throw new ApplicationException(e.getMessage());
        }
    }

    private static byte[] convertHexString(String ss) {
        byte digest[] = new byte[ss.length() / 2];
        for (int i = 0; i < digest.length; i++) {
            String byteString = ss.substring(2 * i, 2 * i + 2);
            int byteValue = Integer.parseInt(byteString, 16);
            digest[i] = (byte) byteValue;
        }
        return digest;
    }
}
