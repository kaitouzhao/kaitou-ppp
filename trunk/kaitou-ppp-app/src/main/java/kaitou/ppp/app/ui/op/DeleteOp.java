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
 * 删除操作.
 * User: 赵立伟
 * Date: 2015/2/7
 * Time: 9:07
 */
public abstract class DeleteOp extends SpringContextManager {
    /**
     * 公共删除接口
     *
     * @param domainType 领域类型
     * @param deleted    待删除对象
     */
    public static void delete(String domainType, Object... deleted) {
        if (Shop.class.getSimpleName().equals(domainType)) {
            getShopService().deleteShops(deleted);
            return;
        }
        if (ShopDetail.class.getSimpleName().equals(domainType)) {
            getShopService().deleteShopDetails(deleted);
            return;
        }
        if (ShopRTS.class.getSimpleName().equals(domainType)) {
            getShopService().deleteShopRTSs(deleted);
            return;
        }
        if (ShopPay.class.getSimpleName().equals(domainType)) {
            getShopService().deleteShopPays(deleted);
            return;
        }
        if (Engineer.class.getSimpleName().equals(domainType)) {
            getEngineerService().deleteEngineers(deleted);
            return;
        }
        if (EngineerTraining.class.getSimpleName().equals(domainType)) {
            getEngineerService().deleteEngineerTrainings(deleted);
            return;
        }
        if (CardApplicationRecord.class.getSimpleName().equals(domainType)) {
            getCardService().deleteCardApplicationRecords(deleted);
            return;
        }
        throw new RuntimeException("尚未支持此类型删除：" + domainType);
    }
}
