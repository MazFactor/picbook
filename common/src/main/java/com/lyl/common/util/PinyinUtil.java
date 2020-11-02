package com.lyl.common.util;

import net.sourceforge.pinyin4j.PinyinHelper;
import net.sourceforge.pinyin4j.format.HanyuPinyinCaseType;
import net.sourceforge.pinyin4j.format.HanyuPinyinOutputFormat;
import net.sourceforge.pinyin4j.format.HanyuPinyinToneType;
import net.sourceforge.pinyin4j.format.exception.BadHanyuPinyinOutputFormatCombination;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 汉字转换为拼音字头的工具类
 *
 * @author WRQ
 * @date 2019/6/26
 * @since 1.0.0
 */
public class PinyinUtil {

    private final static Logger logger = LoggerFactory.getLogger(PinyinUtil.class);

    /**
     * 获取汉字串拼音首字母，英文字符不变
     *
     * @param chinese 汉字串
     * @return 汉语拼音首字母
     */
    public static String getFirstSpell(String chinese) {
        StringBuilder sb = new StringBuilder();
        char[] arr = chinese.toCharArray();
        HanyuPinyinOutputFormat defaultFormat = new HanyuPinyinOutputFormat();
        defaultFormat.setCaseType(HanyuPinyinCaseType.LOWERCASE);
        defaultFormat.setToneType(HanyuPinyinToneType.WITHOUT_TONE);
        for (int i = 0; i < arr.length; i++) {
            if (arr[i] > 128) {
                try {
                    String[] pinyinStringArray = PinyinHelper.toHanyuPinyinStringArray(arr[i], defaultFormat);
                    if (pinyinStringArray != null) {
                        sb.append(pinyinStringArray[0].charAt(0));
                    }
                } catch (BadHanyuPinyinOutputFormatCombination e) {
                    logger.error(e.getMessage());
                }
            } else {
                sb.append(arr[i]);
            }
        }
        return sb.toString().replaceAll("\\W", "").trim();
    }

    /**
     * 获取汉字串拼音，英文字符不变
     *
     * @param chinese 汉字串
     * @return 汉语拼音
     */
    public static String getSpell(String chinese) {
        StringBuilder sb = new StringBuilder();
        char[] arr = chinese.toCharArray();
        HanyuPinyinOutputFormat defaultFormat = new HanyuPinyinOutputFormat();
        defaultFormat.setCaseType(HanyuPinyinCaseType.LOWERCASE);
        defaultFormat.setToneType(HanyuPinyinToneType.WITHOUT_TONE);
        for (int i = 0; i < arr.length; i++) {
            if (arr[i] > 128) {
                try {
                    sb.append(PinyinHelper.toHanyuPinyinStringArray(arr[i], defaultFormat)[0]);
                } catch (BadHanyuPinyinOutputFormatCombination e) {
                    logger.error(e.getMessage());
                }
            } else {
                sb.append(arr[i]);
            }
        }
        return sb.toString();
    }
}
