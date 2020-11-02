package com.lyl.common.util;

import com.alibaba.excel.util.CollectionUtils;
import io.swagger.annotations.ApiModelProperty;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.ss.util.CellRangeAddressList;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.lang.reflect.Field;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * POI  Excel工具类
 *
 * @author 李贵兴
 * @date 2019/8/23
 * @since 1.0.0
 */
public class POIExcelUtil {

    private final static Logger logger = LoggerFactory.getLogger(POIExcelUtil.class);

    /**
     * 私有构造函数，不允许实例化
     */
    private POIExcelUtil() {
    }


    /**
     * 生成下拉框列表 适用于下拉列表元素不多的情况(255以内的下拉)
     * @param sheet
     * @param textList  下拉框数组
     * @param firstRow   起始行
     * @param endRow  终止行
     * @param firstCol  起始列
     * @param endCol  终止列
     * @return
     */
    public static DataValidation setDataValidation(Sheet sheet, String[] textList, int firstRow, int endRow, int firstCol, int endCol) {
        DataValidationHelper helper = sheet.getDataValidationHelper();
        //加载下拉列表内容
        DataValidationConstraint constraint = helper.createExplicitListConstraint(textList);
        //DVConstraint constraint = new DVConstraint();
        constraint.setExplicitListValues(textList);
        //设置数据有效性加载在哪个单元格上。四个参数分别是：起始行、终止行、起始列、终止列
        CellRangeAddressList regions = new CellRangeAddressList((short) firstRow, (short) endRow, (short) firstCol, (short) endCol);
        //数据有效性对象
        DataValidation data_validation = helper.createValidation(constraint, regions);
        //DataValidation data_validation = new DataValidation(regions, constraint);
        return data_validation;
    }

    /**
     * https://blog.csdn.net/wild46cat/article/details/52387484
     *  表头默认样式
     */
    private static CellStyle createHeadTableStyleDefault( SXSSFWorkbook book) {
        CellStyle cellStyle = book.createCellStyle();// 表头样式
        Font fTitle = book.createFont();
        fTitle.setBold(true);// 字体是否加粗
        fTitle.setFontHeightInPoints((short) 12);// 字体大小
        fTitle.setFontName("微软雅黑");// 字体
        cellStyle.setFont(fTitle);
        cellStyle.setFillForegroundColor(IndexedColors.GREY_25_PERCENT.getIndex());// 设置背景色
        cellStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
        cellStyle.setAlignment(HorizontalAlignment.CENTER);// 水平居中
        cellStyle.setVerticalAlignment(VerticalAlignment.CENTER);//垂直居中
        return cellStyle;
    }

    /**
     *  表体默认样式
     */
    private static CellStyle createItemTableStyleDefault(SXSSFWorkbook book) {
        CellStyle cellStyle = book.createCellStyle();// 表头样式
        Font fTitle = book.createFont();
        fTitle.setBold(false);// 字体是否加粗
        fTitle.setFontHeightInPoints((short) 12);// 字体大小
        fTitle.setFontName("微软雅黑");// 字体
        cellStyle.setFont(fTitle);
//        cellStyle.setAlignment(HorizontalAlignment.CENTER);// 水平居中
        cellStyle.setVerticalAlignment(VerticalAlignment.CENTER);//垂直居中
        cellStyle.setWrapText(true);//换行
        return cellStyle;
    }

    /**
     * 导出Excel（动态表头）
     * @param requset
     * @param response
     * @param fileName 文件名
     * @param titles 表头List
     * @param lst 数据List
     */
    public static void exportExcel(HttpServletRequest requset, HttpServletResponse response,
                                   String fileName, List<String> titles, List<? extends Object> lst) {
        ServletOutputStream outputStream = null;
        try {
            // 创建一个工作薄
            SXSSFWorkbook book = new SXSSFWorkbook();
            // 生成一个表格
            Sheet sheet1 = book.createSheet("sheet1");
            // 产生表格标题行
            Row row1 = sheet1.createRow(0);
            Map<String, Integer> map = new HashMap<String, Integer>();

            //表头样式
            CellStyle headStyle=createHeadTableStyleDefault(book);
            //表体样式
            CellStyle itemStyle=createItemTableStyleDefault(book);

            //设置列名
            for (int i = 0; i < titles.size(); i++) {
                Cell cell = row1.createCell(i);
                cell.setCellValue(getHeader(getObjectClz(lst),titles.get(i)));
                cell.setCellStyle(headStyle); //样式
                String key = titles.get(i);
                //日期格式化时使用
                if("createTime,updateTime,effectTime,expireTime".contains(titles.get(i))){
                    key = key+"Str";
                }
                map.put(key, i);
                sheet1.setColumnWidth(i,1000*6); //固定宽度
            }
            Set<String> keySet = map.keySet();
            for (int i = 0; i < lst.size(); i++) {
                Row row = sheet1.createRow(i + 1);
                Object obj = lst.get(i);
                Field[] fields = obj.getClass().getDeclaredFields();
                for (Field field : fields) {
                    field.setAccessible(true);
                    if (keySet.contains(field.getName())) {
                        try {
                            Object fieldObject = field.get(obj);
                            String cellValue = "";
                            if (fieldObject != null) {
                                if("solutionType,solutionEffectType,sameTraPackFlag,deleteFlag,activeFlag,effectFlag".contains(field.getName())){
                                    cellValue = dealType(field.getName(), fieldObject);
                                }else{
                                    cellValue = fieldObject.toString();
                                }
                                Cell cell= row.createCell(map.get(field.getName()));
                                cell.setCellValue(cellValue);
                                cell.setCellStyle(itemStyle);
                            }
                        } catch (IllegalAccessException e) {
                            logger.error(e.getMessage());
                        }
                    }
                }
            }
            //返回信息设置
            response.setContentType("application/vnd.ms-excel");
            response.addHeader("Cache-Control", "no-cache, no-store, must-revalidate");
            response.addHeader("charset", "utf-8");
            response.addHeader("Pragma", "no-cache");
            String encodeName = URLEncoder.encode(fileName+".xlsx", StandardCharsets.UTF_8.toString());
            response.setHeader("Content-Disposition", "attachment; filename=\"" + encodeName + "\"; filename*=utf-8''" + encodeName);
            response.flushBuffer();
            outputStream = response.getOutputStream();
            book.write(outputStream);
            outputStream.flush();
        } catch (Exception e) {
            logger.error(e.getMessage());
        } finally {
            try {
                if (null != outputStream) {
                    outputStream.close();// 关闭流
                }
            } catch (IOException e) {
                logger.error(e.getMessage());
            }
        }
    }

    private static Class getObjectClz(List<? extends Object> lst){
        if(CollectionUtils.isEmpty(lst)){
            return null;
        }
        return lst.get(0).getClass();

    }
    private static String getHeader(Class clz,String title){
        Field[] fields = clz.getDeclaredFields();
        for (Field field : fields) {
            field.setAccessible(true);
            if (title.equals(field.getName())) {
                if(field.isAnnotationPresent(ApiModelProperty.class)){
                    String header = field.getAnnotation(ApiModelProperty.class).value();
                    return header;
                }
            }
        }
        return null;
    }

    private static String dealType(String fieldName,Object fieldObject){
        //包装方案类型  1:正式方案 2:临时方案
        if("solutionType".equals(fieldName)){
            if(Integer.valueOf(fieldObject.toString()) == 1){
                return "正式方案";
            }else{
                return "临时方案";
            }
        }else if("solutionEffectType".equals(fieldName)){
            //包装方案生效类型   1:立即生效 2:指定日期生效 3:随断点通知生效
            Integer solutionEffectType= Integer.valueOf(fieldObject.toString());
            if(solutionEffectType == 1){
                return "立即生效";
            }else if(solutionEffectType == 2){
                return "指定日期生效";
            }else{
                return "随断点通知生效";
            }
        }else if("sameTraPackFlag".equals(fieldName)){
            //同运输包装标识（0不相同，1相同，默认0）
            if((Boolean) fieldObject){
                return "相同";
            }else{
                return "不相同";
            }
        }else if("deleteFlag,activeFlag,effectFlag".contains(fieldName)){
            //是否删除（0未删除，1已删除，默认0）
            if((Boolean)fieldObject){
                return "是";
            }else{
                return "否";
            }
        }
        return null;
    }
}
