package kaitou.ppp.service.impl;

import kaitou.ppp.domain.shop.Shop;
import kaitou.ppp.domain.shop.ShopDetail;
import kaitou.ppp.manager.shop.ShopDetailManager;
import kaitou.ppp.manager.shop.ShopManager;
import kaitou.ppp.service.BaseExcelService;
import kaitou.ppp.service.ShopService;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.io.File;
import java.util.List;

/**
 * 认定店业务处理层实现.
 * User: 赵立伟
 * Date: 2015/1/25
 * Time: 13:24
 */
public class ShopServiceImpl extends BaseExcelService implements ShopService {

    private final Log log = LogFactory.getLog(getClass());

    private static final String[] EXCEL_SHOP_HEADER = {"区域", "认定店编号", "认定店名称", "合同联系人", "联系电话", "邮寄地址", "联系邮箱"};
    private static final String[] SHOP_COLUMN = {"saleRegion", "id", "name", "linkMan", "phone", "address", "email"};

    private static final String[] EXCEL_SHOP_DETAIL_HEADER = {"区域", "认定店编号", "认定店名称", "认定年份", "产品线", "认定级别", "认定机型"};
    private static final String[] SHOP_DETAIL_COLUMN = {"saleRegion", "id", "name", "numberOfYear", "productLine", "level", "model"};

    private ShopManager shopManager;
    private ShopDetailManager shopDetailManager;

    public void setShopManager(ShopManager shopManager) {
        this.shopManager = shopManager;
    }

    public void setShopDetailManager(ShopDetailManager shopDetailManager) {
        this.shopDetailManager = shopDetailManager;
    }

    @Override
    public void importShops(File srcFile) {
        List<Shop> shops = readFromExcel(srcFile, "基础", SHOP_COLUMN, Shop.class);
        log.info("成功导入认定店数：" + shopManager.importShops(shops));
    }

    @Override
    public void exportShops(File targetFile) {
        export2Excel(shopManager.query(), "基础", EXCEL_SHOP_HEADER, SHOP_COLUMN, targetFile);
    }

    @Override
    public void importShopDetails(File srcFile) {
        List<ShopDetail> shopDetails = readFromExcel(srcFile, "发展", SHOP_DETAIL_COLUMN, ShopDetail.class);
        log.info("成功导入认定店明细数：" + shopDetailManager.importShopDetails(shopDetails));
    }

    @Override
    public void exportShopDetails(File targetFile, String... numberOfYear) {
        export2Excel(shopDetailManager.query(numberOfYear), "发展", EXCEL_SHOP_DETAIL_HEADER, SHOP_DETAIL_COLUMN, targetFile);
    }
}
