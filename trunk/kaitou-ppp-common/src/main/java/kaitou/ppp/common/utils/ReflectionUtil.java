package kaitou.ppp.common.utils;

import java.lang.reflect.Method;

/**
 * 反射工具类.
 * User: 赵立伟
 * Date: 2015/2/6
 * Time: 10:57
 */
public abstract class ReflectionUtil {
    /**
     * 获取实体实例属性值
     *
     * @param fieldName 属性名
     * @param obj       实例
     * @return 属性值
     */
    public static Object getFieldValue(String fieldName, Object obj) {
        StringBuilder getterMethod = new StringBuilder("get")
                .append(fieldName.substring(0, 1).toUpperCase())
                .append(fieldName.substring(1));
        try {
            Method method = obj.getClass().getMethod(getterMethod.toString());
            return method.invoke(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 设置实体实例属性值
     *
     * @param fieldName  属性名
     * @param obj        实例
     * @param fieldValue 属性值
     */
    public static void setFieldValue(String fieldName, Object obj, String fieldValue) {
        StringBuilder setterMethod = new StringBuilder("set")
                .append(fieldName.substring(0, 1).toUpperCase())
                .append(fieldName.substring(1));
        try {
            Method method = obj.getClass().getMethod(setterMethod.toString(), String.class);
            method.invoke(obj, fieldValue);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 创建对象实例
     *
     * @param tClass 对象类型
     * @param <T>    对象类型
     * @return 对象实例
     */
    @SuppressWarnings("unchecked")
    public static <T> T newInstance(Class<T> tClass) {
        try {
            return tClass.newInstance();
        } catch (InstantiationException e) {
            throw new RuntimeException(e);
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        }
    }
}
