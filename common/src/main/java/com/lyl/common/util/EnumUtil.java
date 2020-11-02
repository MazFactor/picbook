package com.lyl.common.util;

import com.google.common.collect.Lists;
import com.lyl.common.exception.ApplicationException;
import com.lyl.common.enume.BaseEnum;


import java.lang.reflect.Method;
import java.util.List;

/**
 * 枚举工具类
 *
 * @author WRQ
 * @date 2019/6/25
 * @since 1.0.0
 */
public class EnumUtil {

    /**
     * 私有构造函数，不允许实例化
     */
    private EnumUtil() {
    }

    /**
     * 获取指定枚举类型的值列表
     *
     * @param enumType 枚举类
     * @param <K> 枚举ID的类型
     * @param <E> 枚举类型
     * @return 枚举值列表
     */
    public static <K, E extends BaseEnum<K>> List<E> getEnums(Class<E> enumType) {
        try {
            Method m = enumType.getMethod("values");
            E[] enumsArray = (E[]) m.invoke(null, null);
            return Lists.newArrayList(enumsArray);
        } catch (Exception e) {
            throw new ApplicationException("get enum by v", e);
        }
    }

    /**
     * 获取指定枚举类型的指定索引位置的值
     *
     * @param enumType 枚举类
     * @param index 索引
     * @param <K> 枚举ID的类型
     * @param <E> 枚举类型
     * @return 枚举值
     */
    public static <K, E extends BaseEnum<K>> E indexOf(Class<E> enumType, K index) {
        Method m;
        try {
            m = enumType.getMethod("values");
            E[] list = (E[]) m.invoke(null, null);
            for (E e : list) {
                if (e.getId().equals(index)) {
                    return e;
                }
            }
        } catch (Exception e) {
            throw new ApplicationException("get enum by v", e);
        }
        return null;
    }
}
