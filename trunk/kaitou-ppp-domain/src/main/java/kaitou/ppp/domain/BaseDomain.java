package kaitou.ppp.domain;

import com.womai.bsp.tool.utils.CollectionUtil;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

/**
 * domain父类.
 * User: 赵立伟
 * Date: 2015/1/17
 * Time: 10:36
 */
public abstract class BaseDomain {
    /**
     * 输出对象数据
     *
     * @return 数据
     */
    public abstract String toString();

    /**
     * 数据校验
     */
    public abstract void check();


    /**
     * 从数组中导入数据
     *
     * @param row         数组
     * @param arrayOrders 数组的对应的属性顺序
     */
    public void importFromArray(Object[] row, String[] arrayOrders) {
        for (int i = 0; i < row.length; i++) {
            String value = String.valueOf(row[i]);
            String fieldName = arrayOrders[i];
            StringBuilder setterMethod = new StringBuilder("set")
                    .append(fieldName.substring(0, 1).toUpperCase())
                    .append(fieldName.substring(1));
            try {
                Method method = getClass().getMethod(setterMethod.toString(), String.class);
                method.invoke(this, value);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }
    }

    /**
     * 导出到数组
     *
     * @param arrayOrders 数组的对应的属性顺序
     * @return 数组
     */
    public Object[] export2Array(String[] arrayOrders) {
        List<Object> result = new ArrayList<Object>();
        for (int i = 0; i < arrayOrders.length; i++) {
            String fieldName = arrayOrders[i];
            StringBuilder getterMethod = new StringBuilder("get")
                    .append(fieldName.substring(0, 1).toUpperCase())
                    .append(fieldName.substring(1));
            try {
                Method method = getClass().getMethod(getterMethod.toString());
                result.add(method.invoke(this));
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }
        return CollectionUtil.toArray(result, Object.class);
    }
}
