package com.lyl.common.enume;

import java.io.Serializable;

/**
 * 基本枚举接口
 *
 * @param <K> ID的类型
 *
 * @author WRQ
 * @date 2019/6/25
 * @since 1.0.0
 */
public interface BaseEnum<K> extends Serializable {

    K getId();

    String getName();
}
