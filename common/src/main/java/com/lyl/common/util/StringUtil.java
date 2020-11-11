package com.lyl.common.util;

import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

/**
 * 字符串工具类
 *
 * @author WRQ
 * @date 2019/6/25
 * @since 1.0.0
 */
public class StringUtil {

    /**
     * 私有构造函数，不允许实例化
     */
    private StringUtil() {
    }

    /**
     * 空字符
     */
    public static final String STR_EMPTY = "";
    /**
     * null字符
     */
    public static final String STR_NULL = "null";
    /**
     * 空格
     */
    public static final String STR_SPACE = " ";
    /**
     * 下划线
     */
    public static final String STR_UNDERLINE = "_";
    /**
     * 连字号
     */
    public static final String STR_HYPHEN = "-";
    /**
     * 竖线
     */
    public static final String STR_VERTICAL_LINE = "|";
    /**
     * 斜杠
     */
    public static final String STR_SLASH = "/";
    /**
     * 星号
     */
    public static final String STR_STAR = "*";
    /**
     * 井号
     */
    public static final String STR_SHARP = "#";
    /**
     * 顿号
     */
    public static final String STR_DAWN = "、";
    /**
     * 冒号
     */
    public static final String STR_COLON = ":";
    /**
     * 等号
     */
    public static final String STR_EQUAL = "=";
    /**
     * 百分号
     */
    public static final String STR_PERCENT = "%";
    /**
     * 与字符
     */
    public static final String STR_AMPERSAND = "&";
    /**
     * 英文逗号
     */
    public final static String STR_COMMA = ",";
    /**
     * 英文句号
     */
    public static final String STR_PERIOD = ".";
    /**
     * 英文分号
     */
    public static final String STR_SEMICOLON = ";";
    /**
     * 英文单引号
     */
    public static final String STR_SINGLE_QUOTE = "'";
    /**
     * 制表符
     */
    public static final String STR_TAB = "\t";
    /**
     * 通用换行符
     */
    public static final String STR_LINE_SEPARATOR = System.getProperty("line.separator", "\n");
    /**
     * 文件分隔符
     */
    public static final String STR_FILE_SEPARATOR = System.getProperty("file.separator", "\\");
    /**
     * 路径分隔符
     */
    public static final String STR_PATH_SEPARATOR = System.getProperty("path.separator", ";");
    /**
     * 正则表达式：匹配数字和英文字母
     */
    public static final String EXPRESSION_NUMBER_AND_LETTER = "[0-9a-zA-Z]+";

    /**
     * 判断字符串是否为空或仅由空白字符构成
     *
     * @param str 输入的字符串
     * @return 判断结果
     */
    public static boolean isEmptyOrWhiteSpace(String str) {
        if (str == null) {
            return false;
        }
        return STR_EMPTY.equals(str.trim());
    }

    /**
     * 判断字符串是否为null或空
     *
     * @param str 输入的字符串
     * @return 判断结果
     */
    public static boolean isNullOrEmpty(String str) {
        return str == null || STR_EMPTY.equals(str) || STR_NULL.equals(str);
    }

    /**
     * 判断字符串是否为null、空或仅由空白字符构成
     *
     * @param str 输入的字符串
     * @return 判断结果
     */
    public static boolean isNullOrWhiteSpace(String str) {
        return str == null || STR_EMPTY.equals(str.trim()) || STR_NULL.equals(str.trim());
    }

    /**
     * 判断字符串数组是否全部不为null、空或仅由空白字符构成
     *
     * @param params 输入的字符串数组
     * @return 判断结果
     */
    public static boolean isAllNotNullOrWhiteSpace(String... params) {
        boolean flag = true;
        Iterator<String> iterator = Arrays.asList(params).iterator();
        while (iterator.hasNext()) {
            String str = iterator.next();
            if (isNullOrWhiteSpace(str)) {
                flag = false;
                break;
            }
        }
        return flag;
    }

    /**
     * 判断数组是否包含为null、空或仅由空白字符构成的元素
     *
     * @param objs 输入的数组
     * @return 判断结果
     */
    public static boolean isContainNullOrWhiteSpace(Object... objs) {
        for (Object obj : objs) {
            if (isNullOrWhiteSpace(String.valueOf(obj))) {
                return true;
            }
        }
        return false;
    }

    /**
     * 为指定字符串增加前缀和后缀
     *
     * @param str       输入的字符串
     * @param prefix    前缀
     * @param suffix    后缀
     * @param separator 分隔符
     * @return 转换结果
     */
    public static String addPrefixAndSuffix(String str, String prefix, String suffix, String separator) {
        if (str == null) {
            return null;
        }
        StringBuilder sb = new StringBuilder();
        if (prefix != null) {
            sb.append(prefix);
            if (separator != null) {
                sb.append(separator);
            }
        }
        sb.append(str);
        if (suffix != null) {
            if (separator != null) {
                sb.append(separator);
            }
            sb.append(suffix);
        }
        return sb.toString();
    }

    /**
     * 为指定字符串增加前缀
     *
     * @param str       输入的字符串
     * @param prefix    前缀
     * @param separator 分隔符
     * @return 转换结果
     */
    public static String addPrefix(String str, String prefix, String separator) {
        return addPrefixAndSuffix(str, prefix, null, separator);
    }

    /**
     * 为指定字符串增加前缀
     *
     * @param str    输入的字符串
     * @param prefix 前缀
     * @return 转换结果
     */
    public static String addPrefix(String str, String prefix) {
        return addPrefixAndSuffix(str, prefix, null, null);
    }

    /**
     * 为指定字符串增加后缀
     *
     * @param str       输入的字符串
     * @param suffix    后缀
     * @param separator 分隔符
     * @return 转换结果
     */
    public static String addSuffix(String str, String suffix, String separator) {
        return addPrefixAndSuffix(str, null, suffix, separator);
    }

    /**
     * 为指定字符串增加后缀
     *
     * @param str    输入的字符串
     * @param suffix 后缀
     * @return 转换结果
     */
    public static String addSuffix(String str, String suffix) {
        return addPrefixAndSuffix(str, null, suffix, null);
    }

    /**
     * 把数组转换为用逗号分隔的字符串
     *
     * @param array 输入数组
     * @return 用逗号分隔的字符串
     */
    public static String arrayToString(String[] array) {
        String str = STR_EMPTY;
        if (array != null && array.length > 0) {
            for (int i = 0; i < array.length; i++) {
                str += array[i] + STR_COMMA;
            }
        }
        str = str.substring(0, str.length() - 1);
        return str;
    }

    /**
     * 把List转换为用逗号分隔的字符串
     *
     * @param list 输入List
     * @return 用逗号分隔的字符串
     */
    public static String listToString(List<String> list) {
        StringBuilder sb = new StringBuilder();
        if (list != null && list.size() > 0) {
            for (int i = 0; i < list.size(); i++) {
                if (i < list.size() - 1) {
                    sb.append(list.get(i)).append(STR_COMMA);
                } else {
                    sb.append(list.get(i));
                }
            }
        }
        return sb.toString();
    }

    /**
     * 判断字符串是否全部由数字和字母组成
     *
     * @param str 输入的字符串
     * @return 判断结果
     */
    public static boolean isAllLetters(String str) {
        return str.matches(EXPRESSION_NUMBER_AND_LETTER);
    }

    /**
     * 下划线命名转换为驼峰命名
     *
     * @param param            输入的下划线命名字符串
     * @param isFirstUpperCase 首字母是否大写（大驼峰）
     * @return 驼峰命名字符串
     */
    public static String underlineToHump(String param, boolean isFirstUpperCase) {
        // 以下划线分隔字符串
        String[] params = param.split(STR_UNDERLINE);
        // 初始化返回对象
        StringBuilder result = new StringBuilder();
        // 遍历
        for (String str : params) {
            if (isAllLetters(str)) {
                if (isFirstUpperCase) {
                    result.append(str.substring(0, 1).toUpperCase());
                    result.append(str.substring(1).toLowerCase());
                } else {
                    if (result.length() == 0) {
                        result.append(str.toLowerCase());
                    } else {
                        result.append(str.substring(0, 1).toUpperCase());
                        result.append(str.substring(1).toLowerCase());
                    }
                }
            }
        }
        return result.toString();
    }

    /**
     * @Description //TODO 不够位数的在前面补0，保留num的长度位数字
     * @Date 17:26 2019/4/17
     * @Param [code, num]
     * @return java.lang.String
     **/
    public static String autoGenericCode(String code, int num) {
        String result = "";
        // 保留num的位数
        // 0 代表前面补充0
        // num 代表长度为4
        // d 代表参数为正数型
        result = String.format("%0" + num + "d", Integer.parseInt(code));
        return result;
    }

    /**
     * @Description //TODO 不够位数的在后面补0，保留num的长度位数字
     * @Date 17:26 2019/4/17
     * @Param [code, num]
     * @return java.lang.String
     **/
    public static String autoBehindGenericCode(String code, int num) {
        String result = "";
        result =code+String.format("%1$0"+(num-code.length())+"d",0);
        return result;
    }

    /**
     * MD5加密
     * @param str 待加密字符串
     * @return 加密结果
     */
    public static String StringToMD5(String str) {
        return  str;
    }
}
