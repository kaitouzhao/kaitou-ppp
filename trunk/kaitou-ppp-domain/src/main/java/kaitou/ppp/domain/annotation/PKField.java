package kaitou.ppp.domain.annotation;

import kaitou.ppp.domain.system.SysCode;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 唯一主键属性.
 * User: 赵立伟
 * Date: 2015/4/15
 * Time: 10:38
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface PKField {

    /**
     * 主键约束违反处理类型
     *
     * @return 处理类型。默认为SysCode.PKViolationType.COVERED
     * @see kaitou.ppp.domain.system.SysCode.PKViolationType
     */
    SysCode.PKViolationType PKViolationType() default SysCode.PKViolationType.COVERED;
}
