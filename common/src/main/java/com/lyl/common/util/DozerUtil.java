package com.lyl.common.util;

import org.dozer.DozerBeanMapper;
import org.dozer.Mapper;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Dozer对象转换工具类
 *
 * @author WRQ
 * @date 2019/6/27
 * @since 1.0.0
 */
public class DozerUtil {

    /**
     * 通过常量的形式实现单例模式
     */
    private static final Mapper DOZER_MAPPER = new DozerBeanMapper();

    /**
     * 转换对象
     *
     * @param from 源对象
     * @param toClass 目标对象类
     * @param <F> 源对象泛型
     * @param <T> 目标对象泛型
     * @return 目标对象
     */
    public static <F, T> T convert(F from, final Class<T> toClass) {
        if (from == null) {
            return null;
        }
        return DOZER_MAPPER.map(from, toClass);
    }

    /**
     * 转换对象集合
     *
     * @param fromList 源对象集合
     * @param toClass 目标对象类
     * @param <F> 源对象泛型
     * @param <T> 目标对象泛型
     * @return 目标对象集合
     */
    public static <F, T> List<T> convert(List<F> fromList, final Class<T> toClass) {
        if (fromList == null) {
            return null;
        }
        return fromList.stream().map(from -> convert(from, toClass)).collect(Collectors.toList());
    }
}
