package kaitou.ppp.domain.card;

import kaitou.ppp.domain.system.SysCode;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 数据项坐标.
 * User: 赵立伟
 * Date: 2015/1/10
 * Time: 22:55
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface Coordinates {

    int[] x() default {};

    int[] y() default {};

    SysCode.CellType type() default SysCode.CellType.STRING;
}
