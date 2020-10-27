package com.lyl.web.util;

import com.lyl.web.base.Util.HtmlUtilsJ;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * HTML标签过滤工具
 *
 * @author jim
 * @date 2017/11/27
 */
public final class HtmlUtils {

    private static final Logger logger = LoggerFactory.getLogger(HtmlUtilsJ.class);

    /**
     * 禁止实例化
     */
    private HtmlUtils() {
        throw new IllegalStateException("禁止实例化");
    }

    /**
     * 过滤HTML标签输出文本
     *
     * @param inputString 原字符串
     * @return 过滤后字符串
     */
    public static String Html2Text(String inputString) {
        if (StringUtils.isEmpty(inputString)) {
            return "";
        }
        // 含html标签的字符串
        String htmlStr = inputString.trim();
        String textStr = "";
        Pattern p_script;
        Matcher m_script;
        Pattern p_style;
        Matcher m_style;
        Pattern p_html;
        Matcher m_html;
        Pattern p_space;
        Matcher m_space;
        Pattern p_escape;
        Matcher m_escape;

        try {
            // 定义script的正则表达式{或<script[^>]*?>[\\s\\S]*?<\\/script>
            String regEx_script = "<[\\s]*?script[^>]*?>[\\s\\S]*?<[\\s]*?\\/[\\s]*?script[\\s]*?>";
            // 定义style的正则表达式{或<style[^>]*?>[\\s\\S]*?<\\/style>
            String regEx_style = "<[\\s]*?style[^>]*?>[\\s\\S]*?<[\\s]*?\\/[\\s]*?style[\\s]*?>";
            // 定义HTML标签的正则表达式
            String regEx_html = "<[^>]+>";
            // 定义空格回车换行符
            String regEx_space = "\\s*|\t|\r|\n";
            // 定义转义字符
            String regEx_escape = "&.{2,6}?;";
            // 过滤script标签
            p_script = Pattern.compile(regEx_script, Pattern.CASE_INSENSITIVE);
            m_script = p_script.matcher(htmlStr);
            htmlStr = m_script.replaceAll("");
            // 过滤style标签
            p_style = Pattern.compile(regEx_style, Pattern.CASE_INSENSITIVE);
            m_style = p_style.matcher(htmlStr);
            htmlStr = m_style.replaceAll("");
            // 过滤html标签
            p_html = Pattern.compile(regEx_html, Pattern.CASE_INSENSITIVE);
            m_html = p_html.matcher(htmlStr);
            htmlStr = m_html.replaceAll("");
            // 过滤空格回车标签
            p_space = Pattern.compile(regEx_space, Pattern.CASE_INSENSITIVE);
            m_space = p_space.matcher(htmlStr);
            htmlStr = m_space.replaceAll("");
            // 过滤转义字符
            p_escape = Pattern.compile(regEx_escape, Pattern.CASE_INSENSITIVE);
            m_escape = p_escape.matcher(htmlStr);
            htmlStr = m_escape.replaceAll("");
            textStr = htmlStr;
        } catch (Exception e) {
            logger.info("Html2Text:{}", e.getMessage());
        }
        // 返回文本字符串
        return textStr;
    }
    /**
     * 替换指定标签的属性和值
     * @param sourceString 需要处理的字符串
     * @param tag 标签名称
     * @param tagAttrib 要替换的标签属性值
     * @param startTag 新标签开始标记
     * @param endTag 新标签结束标记
     * @return
     * @author huweijun
     * @date 2016年7月13日 下午7:15:32
     * e.g. String newStr = replaceHtmlTag(content.toString(), "img", "src", "src=\"http://junlenet.com/", "\"");
     */
    public static String replaceHtmlTag(String sourceString, String tag, String tagAttrib, String startTag, String endTag) {
        String regxpForTag = "<\\s*" + tag + "\\s+([^>]*)\\s*" ;
        String regxpForTagAttrib = tagAttrib + "=\\s*\"([^\"]+)\"" ;
        Pattern patternForTag = Pattern.compile (regxpForTag,Pattern. CASE_INSENSITIVE );
        Pattern patternForAttrib = Pattern.compile (regxpForTagAttrib,Pattern. CASE_INSENSITIVE );
        Matcher matcherForTag = patternForTag.matcher(sourceString);
        StringBuffer sb = new StringBuffer();
        boolean result = matcherForTag.find();
        while (result) {
            StringBuffer sbreplace = new StringBuffer( "<"+tag+" ");
            Matcher matcherForAttrib = patternForAttrib.matcher(matcherForTag.group(1));
            if (matcherForAttrib.find()) {
                String attributeStr = matcherForAttrib.group(1);
                matcherForAttrib.appendReplacement(sbreplace, startTag + attributeStr + endTag);
            }
            matcherForAttrib.appendTail(sbreplace);
            matcherForTag.appendReplacement(sb, sbreplace.toString());
            result = matcherForTag.find();
        }
        matcherForTag.appendTail(sb);
        return sb.toString();
    }

    /**
     * 获取html图片路径
     * @param htmlCode html
     * @return 路径集
     */
    public static List<String> getImageSrc(String htmlCode) {
        List<String> imageSrcList = new ArrayList<String>();
        Pattern p = Pattern.compile("<img\\b[^>]*\\bsrc\\b\\s*=\\s*('|\")?([^'\"\n\r\f>]+(\\.jpg|\\.bmp|\\.eps|\\.gif|\\.mif|\\.miff|\\.png|\\.tif|\\.tiff|\\.svg|\\.wmf|\\.jpe|\\.jpeg|\\.dib|\\.ico|\\.tga|\\.cut|\\.pic)\\b)[^>]*>", Pattern.CASE_INSENSITIVE);
        Matcher m = p.matcher(htmlCode);
        String quote = null;
        String src = null;
        while (m.find()) {
            quote = m.group(1);
            src = (quote == null || quote.trim().length() == 0) ? m.group(2).split("\\s+")[0] : m.group(2);
            imageSrcList.add(src);

        }
        return imageSrcList;
    }

    /**
     * 获取摘要
     * @param text 源内容
     * @return 摘要结果
     */
    public static String getExcerpt(String text) {
        String excerpt = null;
        Document doc = Jsoup.parse(text);
        Elements ps = doc.select("p");
        Element firstP;
        if(ps.hasText())
            firstP = ps.first();
        else
        {
            ps = doc.select("pre");
            firstP = ps.first();
        }
        if(firstP == null)
            excerpt = doc.html();
        else
            excerpt = firstP.toString();
        return excerpt;
    }
}
