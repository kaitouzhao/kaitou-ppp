package kaitou.ppp.app.ui.op;

import kaitou.ppp.app.SpringContextManager;
import kaitou.ppp.domain.card.CardApplicationRecord;
import kaitou.ppp.domain.engineer.Engineer;
import kaitou.ppp.domain.engineer.EngineerTraining;
import kaitou.ppp.domain.shop.Shop;
import kaitou.ppp.domain.shop.ShopDetail;
import kaitou.ppp.domain.shop.ShopPay;
import kaitou.ppp.domain.shop.ShopRTS;
import kaitou.ppp.service.BaseExcelService;

import java.io.File;
import java.util.List;

/**
 * 导出操作.
 * User: 赵立伟
 * Date: 2015/4/7
 * Time: 11:55
 */
public abstract class ExportOp extends SpringContextManager {
    /**
     * 公共导出接口
     *
     * @param domainType 领域类型
     * @param dataList   数据对象列表
     * @param targetFile 目标文件
     */
    public static void export(String domainType, List dataList, File targetFile) {
        if (Shop.class.getSimpleName().equals(domainType)) {
            ((BaseExcelService) getShopService()).export2Excel(dataList, targetFile, Shop.class);
            return;
        }
        if (ShopDetail.class.getSimpleName().equals(domainType)) {
            ((BaseExcelService) getShopService()).export2Excel(dataList, targetFile, ShopDetail.class);
            return;
        }
        if (ShopRTS.class.getSimpleName().equals(domainType)) {
            ((BaseExcelService) getShopService()).export2Excel(dataList, targetFile, ShopRTS.class);
            return;
        }
        if (ShopPay.class.getSimpleName().equals(domainType)) {
            ((BaseExcelService) getShopService()).export2Excel(dataList, targetFile, ShopPay.class);
            return;
        }
        if (Engineer.class.getSimpleName().equals(domainType)) {
            ((BaseExcelService) getEngineerService()).export2Excel(dataList, targetFile, Engineer.class);
            return;
        }
        if (EngineerTraining.class.getSimpleName().equals(domainType)) {
            ((BaseExcelService) getEngineerService()).export2Excel(dataList, targetFile, EngineerTraining.class);
            return;
        }
        if (CardApplicationRecord.class.getSimpleName().equals(domainType)) {
            ((BaseExcelService) getEngineerService()).export2Excel(dataList, targetFile, CardApplicationRecord.class);
            return;
        }
        throw new RuntimeException("尚未支持此类型导出：" + domainType);
    }
}
