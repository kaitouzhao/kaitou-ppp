package kaitou.ppp.dao;

import com.womai.bsp.tool.utils.CollectionUtil;
import kaitou.ppp.domain.BaseDomain4InDoubt;
import kaitou.ppp.domain.annotation.PKField;
import kaitou.ppp.domain.system.SysCode;

import java.lang.reflect.Field;
import java.util.List;

import static com.womai.bsp.tool.utils.ReflectionUtil.getAnnotationClassField;
import static com.womai.bsp.tool.utils.ReflectionUtil.getFieldValue;

/**
 * 存疑记录的DAO父类.
 * User: 赵立伟
 * Date: 2015/4/16
 * Time: 9:13
 */
public abstract class BaseDao4InDoubt<T extends BaseDomain4InDoubt> extends BaseDao {

    @SuppressWarnings("unchecked")
    @Override
    public void preSave(Object... domains) {
        if (CollectionUtil.isEmpty(domains)) {
            return;
        }
        for (Object obj : domains) {
            T domain = (T) obj;
            if (domain.getSerialNo() < 0) {
                domain.generateSerialNo();
            }
        }
        List<T> existsList = query();
        List<Field> pkFields = getAnnotationClassField(getDomainClass(), PKField.class);
        if (CollectionUtil.isEmpty(pkFields) || pkFields.size() > 1) {
            return;
        }
        Field pkField = pkFields.get(0);
        PKField fieldLogo = pkField.getAnnotation(PKField.class);
        if (fieldLogo.PKViolationType() == SysCode.PKViolationType.COVERED) {
            return;
        }
        for (Object obj : domains) {
            T domain = (T) obj;
            for (T existsDomain : existsList) {
                if (domain.getSerialNo() < 0 && domain.equals(existsDomain)) {
                    domain.setSerialNo(existsDomain.getSerialNo());
                }
            }
            if (domain.getSerialNo() < 0) {
                domain.generateSerialNo();
            }
            Object newPKValue = getFieldValue(getDomainClass(), pkField.getName(), domain);
            if (newPKValue == null || "".equals(String.valueOf(newPKValue))) {
                domain.doInDoubt();
                continue;
            }
            for (T existsDomain : existsList) {
                Object pkValue = getFieldValue(getDomainClass(), pkField.getName(), existsDomain);
                if (newPKValue.equals(pkValue) && domain.getSerialNo() != existsDomain.getSerialNo()) {
                    domain.doInDoubt();
                    break;
                }
            }
        }
    }
}
