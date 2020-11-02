package com.lyl.common.util;

import com.alibaba.excel.EasyExcelFactory;
import com.alibaba.excel.ExcelWriter;
import com.alibaba.excel.metadata.BaseRowModel;
import com.alibaba.excel.metadata.Font;
import com.alibaba.excel.metadata.Sheet;
import com.alibaba.excel.metadata.TableStyle;

import com.lyl.common.exception.ApplicationException;
import com.lyl.common.listener.ExcelAnalysisEventListener;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.util.List;
import java.util.UUID;

/**
 * Excel工具类
 *
 * @author WRQ
 * @date 2019/6/27
 * @since 1.0.0
 */
public class ExcelUtil {

    private final static Logger logger = LoggerFactory.getLogger(ExcelUtil.class);

    /**
     * 私有构造函数，不允许实例化
     */
    private ExcelUtil() {
    }

    /**
     * 导入指定模板的Excel
     *
     * @param file  导入文件
     * @param clazz 模板实体类
     * @return 解析后的模板实体列表
     */
    public static List<Object> importByTemplate(MultipartFile file, Class<? extends BaseRowModel> clazz) {
        InputStream in = null;
        String filePath = UUID.randomUUID().toString().replaceAll(StringUtil.STR_HYPHEN, StringUtil.STR_EMPTY);
        try {
            File fileCache = FileUtil.saveFile(filePath, file);
            in = new BufferedInputStream(new FileInputStream(fileCache));
            return readExcelByModel(in, clazz);
        } catch (IOException e) {
            throw new ApplicationException("Failed to import excel by template.");
        } finally {
            try {
                if (null != in){
                    in.close();
                }
                FileUtil.delete(filePath);
            } catch (IOException e) {
                logger.error(e.getMessage());
            }
        }
    }

    /**
     * 通过模板导出Excel
     *
     * @param response  HttpServletResponse
     * @param clazz     模板实体类
     * @param modelList 数据列表
     * @param fileName  文件名称
     */
    public static void exportByTemplate(HttpServletRequest requset, HttpServletResponse response, Class<? extends BaseRowModel> clazz, List<? extends BaseRowModel> modelList, String fileName) {
        ServletOutputStream out = null;
        try {
            out = response.getOutputStream();
            fileName = encodeChineseDownloadFileName(requset, fileName);
            response.setContentType("application/vnd.ms-excel;charset=utf-8");
            response.setCharacterEncoding("UTF-8");
            response.setHeader("Content-disposition", "attachment;filename=" + (StringUtil.isNullOrWhiteSpace(fileName) ? System.currentTimeMillis() : fileName) + ".xlsx");
            writeExcelByModel(out, clazz, modelList);
        } catch (IOException e) {
            throw new ApplicationException("Failed to export excel by template.");
        } finally {
            try {
                if (null != out){
                    out.flush();
                }
            } catch (IOException e) {
                logger.error(e.getMessage());
            }
        }
    }

    /**
     * 读取Excel并转换为模板实体（单Sheet）
     *
     * @param in    输入流
     * @param clazz 模板实体类
     */
    private static List<Object> readExcelByModel(InputStream in, Class<? extends BaseRowModel> clazz) {
        ExcelAnalysisEventListener listener = new ExcelAnalysisEventListener();
        EasyExcelFactory.readBySax(in, new Sheet(1, 0, clazz), listener);
        return listener.getDataList();
    }

    /**
     * 通过模板实体创建Excel（单Sheet）
     *
     * @param out       输出流
     * @param clazz     模板实体类
     * @param modelList 数据列表
     */
    private static void writeExcelByModel(OutputStream out, Class<? extends BaseRowModel> clazz, List<? extends BaseRowModel> modelList) {

        // 创建Sheet
        Sheet sheet1 = new Sheet(1, 0, clazz);
        // Sheet名
        sheet1.setSheetName("sheet1");
        // 是否自适应宽度
        sheet1.setAutoWidth(Boolean.TRUE);
        // 设置表格样式
        sheet1.setTableStyle(createTableStyleDefault());

        // 创建写入对象
        ExcelWriter writer = EasyExcelFactory.getWriter(out);
        // 将数据写入目标Sheet
        writer.write(modelList, sheet1);
        // 写入文件
        writer.finish();
    }

    /**
     * 创建默认表格样式
     *
     * @return 默认表格样式
     */
    private static TableStyle createTableStyleDefault() {

        // 表头样式
        Font headFont = new Font();
        // 字体是否加粗
        headFont.setBold(true);
        // 字体大小
        headFont.setFontHeightInPoints((short) 12);
        // 字体
        headFont.setFontName("微软雅黑");

        // 内容样式
        Font contextFont = new Font();
        contextFont.setBold(false);
        contextFont.setFontHeightInPoints((short) 12);
        contextFont.setFontName("微软雅黑");

        // 初始化表格样式
        TableStyle tableStyle = new TableStyle();
        // 设置表头样式
        tableStyle.setTableHeadFont(headFont);
        tableStyle.setTableHeadBackGroundColor(IndexedColors.GREY_40_PERCENT);
        // 设置内容样式
        tableStyle.setTableContentFont(contextFont);
        tableStyle.setTableContentBackGroundColor(IndexedColors.WHITE);
        return tableStyle;
    }


    /**
     * 对文件流输出下载的中文文件名进行编码，屏蔽各种浏览器版本的差异性
     *
     * @param request   HttpServletRequest
     * @param pFileName 文件名
     * @return 编码格式处理后的文件名
     */
    private static String encodeChineseDownloadFileName(HttpServletRequest request, String pFileName) {
        try {
            String agent = request.getHeader("USER-AGENT");
            boolean isMSIE = ((agent != null && agent.indexOf("MSIE") != -1) || (null != agent && -1 != agent.indexOf("like Gecko")));
            if (isMSIE) {
                pFileName = new String(pFileName.getBytes("GBK"), "ISO8859-1");
            } else {
                pFileName = new String(pFileName.getBytes("UTF8"), "ISO8859-1");
            }
        } catch (UnsupportedEncodingException e) {
            logger.error(e.getMessage());
        }
        return pFileName;
    }
}
