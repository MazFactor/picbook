package com.lyl.common.listener;

import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.event.AnalysisEventListener;
import com.lyl.common.constant.BaseMsgConstant;
import com.lyl.common.exception.BusinessException;
import com.lyl.common.util.ReflectUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;

/**
 * Excel解析监听器
 *
 * @author WRQ
 * @date 2019/7/15
 * @since 1.0.0
 */
public class ExcelAnalysisEventListener extends AnalysisEventListener {

    /**
     * 日志
     */
    private static final Logger LOGGER = LoggerFactory.getLogger(ExcelAnalysisEventListener.class);
    /**
     * 表头暂存区
     */
    private List<Object> headList = new ArrayList<>();
    /**
     * 数据暂存区
     */
    private List<Object> dataList = new ArrayList<>();

    @Override
    public void invoke(Object object, AnalysisContext context) {
        // 打印日志
        LOGGER.info("CurrentSheet: {}; CurrentRowNum: {}.", context.getCurrentSheet(), context.getCurrentRowNum());
        if (headList.isEmpty()) {
            // 校验导入模板与模型的表头是否一致
            List<List<String>> headModelList = context.getExcelHeadProperty().getHead();
            List<Object> headImportList = ReflectUtil.getFields(object);
            if (headModelList.size() != headImportList.size()) {
                 throw new BusinessException(BaseMsgConstant.BASE_MSG_ERROR_FORMAT_TEMPLATE);
            } else {
                for (int i = 0; i < headModelList.size(); i++) {
                    if (!headModelList.get(i).get(0).equals(headImportList.get(i).toString())) {
                        throw new BusinessException(BaseMsgConstant.BASE_MSG_ERROR_FORMAT_TEMPLATE);
                    }
                }
            }
            // 添加到表头暂存区
            headList.add(object);
        } else {
            // 添加到数据暂存区供后续批量业务逻辑处理
            dataList.add(object);
        }
    }

    @Override
    public void doAfterAllAnalysed(AnalysisContext context) {
        // 解析结束销毁不用的资源
        // dataList.clear();
    }

    public List<Object> getDataList() {
        return dataList;
    }
}
