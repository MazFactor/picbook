package com.lyl.common.constant;


import com.lyl.common.util.StringUtil;

/**
 * @ClassName ErpMsgConstant
 * @DeScription TODO
 * @Author GW00163274
 * @Date 2019/12/11 17:41
 * @Version 1.0
 */
public class ErpMsgConstant {

    public static final String ERP_MSG_INFO_FORMAT_DEFAULT = "FTP文件读取成功：{0}_{1}！";

    public static final String ERP_MSG_ERROR_FORMAT_DEFAULT = "FTP文件读取失败：{0}_{1}！" + StringUtil.STR_LINE_SEPARATOR + "{2}";

    public static final String ERP_MSG_ERROR_FORMAT_FTP_FILE = "在路径{0}下未检测到FTP文件！";

    public static final String ERP_MSG_ERROR_FORMAT_NO_DOWNLOAD_FILE = "没有要下载的文件！";

    public static final String ERP_MSG_ERROR_FORMAT_NO_UPLOAD_DATA = "没有要上传的数据！";
}
