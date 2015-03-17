package kaitou.ppp.app.ui.op;

import kaitou.ppp.app.SpringContextManager;
import kaitou.ppp.domain.card.CardApplicationRecord;
import kaitou.ppp.domain.engineer.Engineer;
import kaitou.ppp.domain.engineer.EngineerTraining;
import kaitou.ppp.domain.shop.Shop;
import kaitou.ppp.domain.shop.ShopDetail;
import kaitou.ppp.domain.shop.ShopPay;
import kaitou.ppp.domain.shop.ShopRTS;

/**
 * 保存/更新操作.
 * User: 赵立伟
 * Date: 2015/2/7
 * Time: 23:37
 */
public abstract class SaveOrUpdateOp extends SpringContextManager {
    /**
     * 公共保存/更新接口
     *
     * @param domainType 领域类型
     * @param toDoObj    待操作对象
     */
    public static void saveOrUpdate(String domainType, Object toDoObj) {
        if (Shop.class.getSimpleName().equals(domainType)) {
            getShopService().saveOrUpdateShop((Shop) toDoObj);
            return;
        }
        if (ShopDetail.class.getSimpleName().equals(domainType)) {
            getShopService().saveOrUpdateShopDetail((ShopDetail) toDoObj);
            return;
        }
        if (ShopPay.class.getSimpleName().equals(domainType)) {
            getShopService().saveOrUpdateShopPay((ShopPay) toDoObj);
            return;
        }
        if (ShopRTS.class.getSimpleName().equals(domainType)) {
            getShopService().saveOrUpdateShopRTS((ShopRTS) toDoObj);
            return;
        }
        if (Engineer.class.getSimpleName().equals(domainType)) {
            getEngineerService().saveOrUpdateEngineer((Engineer) toDoObj);
            return;
        }
        if (EngineerTraining.class.getSimpleName().equals(domainType)) {
            getEngineerService().saveOrUpdateEngineerTraining((EngineerTraining) toDoObj);
            return;
        }
        if (CardApplicationRecord.class.getSimpleName().equals(domainType)) {
            getCardService().saveOrUpdateCardApplicationRecord((CardApplicationRecord) toDoObj);
            return;
        }
        throw new RuntimeException("尚未支持此类型保存/更新：" + domainType);
    }
}
