package com.lyl.common.util;



import com.lyl.common.exception.ApplicationException;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

/**
 * 反射工具类
 *
 * @author WRQ
 * @date 2019/6/26
 * @since 1.0.0
 */
public class ReflectUtil {

    /**
     * 获得obj对象的所有Field的值的列表
     *
     * @param obj
     * @return
     */
    public static List<Object> getFields(Object obj) {
        List<Object> fieldValues = new ArrayList<>();
        try {
            Field[] fields = obj.getClass().getDeclaredFields();
            for (Field field : fields) {
                field.setAccessible(true);
                fieldValues.add(field.get(obj));
            }
        } catch (IllegalAccessException e) {
            throw new ApplicationException(e.getMessage());
        }
        return fieldValues;
    }

    /**
     * 获取obj对象fieldName的Field
     *
     * @param obj
     * @param fieldName
     * @return
     */
    public static Field getFieldByFieldName(Object obj, String fieldName) {
        for (Class<?> superClass = obj.getClass(); superClass != Object.class; superClass = superClass.getSuperclass()) {
            try {
                return superClass.getDeclaredField(fieldName);
            } catch (NoSuchFieldException e) {
                e.getStackTrace();
            }
        }
        return null;
    }

    /**
     * 获取obj对象fieldName的属性值
     *
     * @param obj
     * @param fieldName
     * @return value
     * @throws SecurityException
     * @throws NoSuchFieldException
     * @throws IllegalArgumentException
     * @throws IllegalAccessException
     */
    public static Object getValueByFieldName(Object obj, String fieldName) throws SecurityException,
            IllegalArgumentException, IllegalAccessException {
        Field field = getFieldByFieldName(obj, fieldName);
        Object value = null;
        if (field != null) {
            if (field.isAccessible()) {
                value = field.get(obj);
            } else {
                field.setAccessible(true);
                value = field.get(obj);
                field.setAccessible(false);
            }
        }
        return value;
    }

    /**
     * 设置obj对象fieldName的属性值
     *
     * @param obj
     * @param fieldName
     * @param value
     * @throws SecurityException
     * @throws NoSuchFieldException
     * @throws IllegalArgumentException
     * @throws IllegalAccessException
     */
    public static void setValueByFieldName(Object obj, String fieldName, Object value) throws SecurityException,
            NoSuchFieldException, IllegalArgumentException, IllegalAccessException {
        Field field = obj.getClass().getDeclaredField(fieldName);
        if (field.isAccessible()) {
            field.set(obj, value);
        } else {
            field.setAccessible(true);
            field.set(obj, value);
            field.setAccessible(false);
        }
    }
}
