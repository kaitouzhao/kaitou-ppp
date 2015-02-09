package kaitou.ppp.app.ui.op;

import kaitou.ppp.app.SpringContextManager;
import kaitou.ppp.domain.engineer.Engineer;
import kaitou.ppp.domain.shop.Shop;

/**
 * 编辑操作.
 * User: 赵立伟
 * Date: 2015/2/7
 * Time: 23:37
 */
public abstract class EditOp extends SpringContextManager {
    /**
     * 公共编辑接口
     *
     * @param domainType 领域类型
     * @param edited     待编辑对象
     */
    public static void edit(String domainType, Object edited) {
        if (Shop.class.getSimpleName().equals(domainType)) {
            getShopService().editShop((Shop) edited);
            return;
        }
        if (Engineer.class.getSimpleName().equals(domainType)) {
            getEngineerService().editEngineer((Engineer) edited);
            return;
        }
        throw new RuntimeException("尚未支持此类型编辑：" + domainType);
    }
}
