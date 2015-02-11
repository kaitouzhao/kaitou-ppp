package kaitou.ppp.service.impl;

import com.womai.bsp.tool.utils.CollectionUtil;
import kaitou.ppp.dao.cache.CacheManager;
import kaitou.ppp.domain.shop.*;
import kaitou.ppp.manager.listener.ShopUpdateListener;
import kaitou.ppp.manager.shop.ShopDetailManager;
import kaitou.ppp.manager.shop.ShopManager;
import kaitou.ppp.manager.shop.ShopPayManager;
import kaitou.ppp.manager.shop.ShopRTSManager;
import kaitou.ppp.service.BaseExcelService;
import kaitou.ppp.service.ShopService;
import org.joda.time.DateTime;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import static com.womai.bsp.tool.utils.BeanCopyUtil.copyBean;

/**
 * 认定店业务处理层实现.
 * User: 赵立伟
 * Date: 2015/1/25
 * Time: 13:24
 */
public class ShopServiceImpl extends BaseExcelService implements ShopService {

    private static final String[] IMPORT_SHOP_HEADER = {"区域", "认定店编号", "认定店名称", "合同联系人", "联系电话", "邮寄地址", "联系邮箱"};
    private static final String[] IMPORT_SHOP_COLUMN = {"saleRegion", "id", "name", "linkMan", "phone", "address", "email"};

    private static final String[] EXPORT_SHOP_HEADER = {"状态", "区域", "认定店编号", "认定店名称", "合同联系人", "联系电话", "邮寄地址", "联系邮箱"};
    private static final String[] EXPORT_SHOP_COLUMN = {"status", "saleRegion", "id", "name", "linkMan", "phone", "address", "email"};

    private static final String[] IMPORT_SHOP_DETAIL_HEADER = {"认定店编号", "认定店名称", "认定年份", "产品线", "认定级别", "认定机型"};
    private static final String[] IMPORT_SHOP_DETAIL_COLUMN = {"id", "name", "numberOfYear", "productLine", "level", "model"};

    private static final String[] EXPORT_SHOP_DETAIL_HEADER = {"区域", "认定店编号", "认定店名称", "认定年份", "产品线", "认定级别", "认定机型"};
    private static final String[] EXPORT_SHOP_DETAIL_COLUMN = {"saleRegion", "id", "name", "numberOfYear", "productLine", "level", "model"};

    private static final String[] EXCEL_SHOP_RTS_HEADER = {"认定店编号", "认定店名称", "产品线", "RTS"};
    private static final String[] SHOP_RTS_COLUMN = {"id", "name", "productLine", "rts"};

    private static final String[] EXCEL_SHOP_PAY_HEADER = {"认定店编号", "认定店名称", "付款代码", "付款名称", "开户行", "帐号"};
    private static final String[] SHOP_PAY_COLUMN = {"id", "name", "payCode", "payName", "accountBank", "accountNo"};

    private static final String[] EXCEL_SHOP_ALL_HEADER = {"区域", "认定店编号", "认定店名称", "合同联系人", "联系电话", "邮寄地址", "联系邮箱", "认定年份", "产品线", "认定级别"};
    private static final String[] SHOP_ALL_COLUMN = {"saleRegion", "id", "name", "linkMan", "phone", "address", "email", "numberOfYear", "productLine", "level"};

    private ShopManager shopManager;
    private ShopRTSManager shopRTSManager;
    private ShopPayManager shopPayManager;
    private ShopDetailManager shopDetailManager;
    private List<ShopUpdateListener> shopUpdateListeners;

    public void setShopUpdateListeners(List<ShopUpdateListener> shopUpdateListeners) {
        this.shopUpdateListeners = shopUpdateListeners;
    }

    public void setShopRTSManager(ShopRTSManager shopRTSManager) {
        this.shopRTSManager = shopRTSManager;
    }

    public void setShopPayManager(ShopPayManager shopPayManager) {
        this.shopPayManager = shopPayManager;
    }

    public void setShopManager(ShopManager shopManager) {
        this.shopManager = shopManager;
    }

    public void setShopDetailManager(ShopDetailManager shopDetailManager) {
        this.shopDetailManager = shopDetailManager;
    }

    @Override
    public void importShops(File srcFile) {
        List<Shop> shops = readFromExcel(srcFile, "基础", IMPORT_SHOP_HEADER, IMPORT_SHOP_COLUMN, Shop.class);
        importShops(shops);
    }

    /**
     * 导入/更新认定店
     *
     * @param shops 认定店列表
     */
    private void importShops(List<Shop> shops) {
        int successCount = shopManager.importShops(shops);
        logOperation("成功导入/更新认定店数：" + successCount);
        if (successCount <= 0) {
            return;
        }
        if (CollectionUtil.isEmpty(shopUpdateListeners)) {
            return;
        }
        for (ShopUpdateListener listener : shopUpdateListeners) {
            listener.updateShopEvent(CollectionUtil.toArray(shops, Shop.class));
        }
    }

    @Override
    public void exportShops(File targetFile) {
        export2Excel(shopManager.query(), "基础", EXPORT_SHOP_HEADER, EXPORT_SHOP_COLUMN, targetFile);
    }

    @Override
    public void importShopDetails(File srcFile) {
        List<ShopDetail> shopDetails = readFromExcel(srcFile, "发展", IMPORT_SHOP_DETAIL_HEADER, IMPORT_SHOP_DETAIL_COLUMN, ShopDetail.class);
        importShopDetails(shopDetails);
    }

    /**
     * 导入/更新认定店认定级别
     *
     * @param shopDetails 认定级别列表
     */
    private void importShopDetails(List<ShopDetail> shopDetails) {
        int successCount = 0;
        if (CollectionUtil.isEmpty(shopDetails)) {
            logOperation("成功导入/更新认定店认定级别数：" + successCount);
            return;
        }
        for (ShopDetail shopDetail : shopDetails) {
            CachedShop cachedShop = shopManager.getCachedShop(shopDetail.getId());
            shopDetail.setSaleRegion(cachedShop.getSaleRegion());
        }
        successCount = shopDetailManager.importShopDetails(shopDetails);
        logOperation("成功导入/更新认定店认定级别数：" + successCount);
        if (successCount <= 0) {
            return;
        }
        if (CollectionUtil.isEmpty(shopUpdateListeners)) {
            return;
        }
        for (ShopUpdateListener listener : shopUpdateListeners) {
            listener.updateShopDetailEvent(CollectionUtil.toArray(shopDetails, ShopDetail.class));
        }
    }

    @Override
    public void exportShopDetails(File targetFile, String... numberOfYear) {
        export2Excel(shopDetailManager.query(numberOfYear), "发展", EXPORT_SHOP_DETAIL_HEADER, EXPORT_SHOP_DETAIL_COLUMN, targetFile);
    }

    @Override
    public void deleteShop(String saleRegion, String id) {
        Shop shop = new Shop();
        shop.setSaleRegion(saleRegion);
        shop.setId(id);
        logOperation("成功删除认定店个数：" + shopManager.delete(shop));
    }

    @Override
    public void deleteShops(Object... shops) {
        logOperation("成功删除认定店个数：" + shopManager.delete(shops));
    }

    @Override
    public void deleteShopDetail(String id, String productLine, String numberOfYear) {
        List<ShopDetail> details = shopDetailManager.query(numberOfYear);
        if (CollectionUtil.isEmpty(details)) {
            logOperation("成功删除认定店认定信息个数：0");
            return;
        }
        List<ShopDetail> deleted = new ArrayList<ShopDetail>();
        for (ShopDetail detail : details) {
            if (detail.getId().equals(id) && detail.getProductLine().equals(productLine)) {
                deleted.add(detail);
            }
        }
        logOperation("成功删除认定店认定信息个数：" + shopDetailManager.delete(CollectionUtil.toArray(deleted, ShopDetail.class)));
    }

    @Override
    public void deleteShopDetails(Object... details) {
        logOperation("成功删除认定店认定信息个数：" + shopDetailManager.delete(details));
    }

    @Override
    public void deleteShopPay(String id) {
        ShopPay pay = new ShopPay();
        pay.setId(id);
        logOperation("成功删除认定店账户个数：" + shopPayManager.delete(pay));
    }

    @Override
    public void deleteShopPays(Object... pays) {
        logOperation("成功删除认定店账户个数：" + shopPayManager.delete(pays));
    }

    @Override
    public void deleteShopRTS(String id, String productLine) {
        ShopRTS rts = new ShopRTS();
        rts.setId(id);
        rts.setProductLine(productLine);
        logOperation("成功删除认定店RTS个数：" + shopRTSManager.delete(rts));
    }

    @Override
    public void deleteShopRTSs(Object... rts) {
        logOperation("成功删除认定店RTS个数：" + shopRTSManager.delete(rts));
    }

    @Override
    public void cacheAllShops() {
        shopManager.cachedShop();
        shopDetailManager.cacheShop();
        logSystemInfo("cached shops:" + CacheManager.cachedShopMap.toString());
    }

    @Override
    public void importRTSs(File srcFile) {
        List<ShopRTS> shopRTSes = readFromExcel(srcFile, "RTS", EXCEL_SHOP_RTS_HEADER, SHOP_RTS_COLUMN, ShopRTS.class);
        importShopRTSs(shopRTSes);
    }

    /**
     * 导入/更新认定店RTS
     *
     * @param shopRTSes rts列表
     */
    private void importShopRTSs(List<ShopRTS> shopRTSes) {
        logOperation("成功导入/更新RTS数：" + shopRTSManager.importShops(shopRTSes));
    }

    @Override
    public void exportRTSs(File targetFile) {
        export2Excel(shopRTSManager.query(), "RTS", EXCEL_SHOP_RTS_HEADER, SHOP_RTS_COLUMN, targetFile);
    }

    @Override
    public void importPays(File srcFile) {
        List<ShopPay> shopPays = readFromExcel(srcFile, "付款信息", EXCEL_SHOP_PAY_HEADER, SHOP_PAY_COLUMN, ShopPay.class);
        importShopPays(shopPays);
    }

    /**
     * 导入/更新认定店帐号信息
     *
     * @param shopPays 帐号信息列表
     */
    private void importShopPays(List<ShopPay> shopPays) {
        logOperation("成功导入/更新认定店帐号信息数：" + shopPayManager.importShops(shopPays));
    }

    @Override
    public void exportPays(File targetFile) {
        export2Excel(shopPayManager.query(), "付款信息", EXCEL_SHOP_PAY_HEADER, SHOP_PAY_COLUMN, targetFile);
    }

    @Override
    public void exportAll(File targetFile) {
        List<Shop> allShops = shopManager.query();
        List<ShopAll> shopAlls = new ArrayList<ShopAll>();
        int currentYear = new DateTime().getYear();
        ShopAll shopAll;
        for (Shop shop : allShops) {
            shopAll = new ShopAll();
            copyBean(shop, shopAll);
            List<CachedShopDetail> details = shopManager.queryCachedShopDetails(shopAll.getId());
            for (CachedShopDetail detail : details) {
                try {
                    if (currentYear <= Integer.valueOf(detail.getNumberOfYear())) {
                        copyBean(detail, shopAll);
                    }
                } catch (NumberFormatException e) {
                    logSystemInfo("认定年份不正确。认定店编码：" + shopAll.getId());
                }
            }
            shopAlls.add(shopAll);
        }
        export2Excel(shopAlls, "基础信息全导出", EXCEL_SHOP_ALL_HEADER, SHOP_ALL_COLUMN, targetFile);
    }

    @Override
    public List<Shop> queryAllShops() {
        return shopManager.query();
    }

    @Override
    public List<ShopDetail> queryAllDetails() {
        return shopDetailManager.query();
    }

    @Override
    public List<ShopRTS> queryAllRTSs() {
        return shopRTSManager.query();
    }

    @Override
    public List<ShopPay> queryAllPays() {
        return shopPayManager.query();
    }

    @Override
    public void saveOrUpdateShop(Shop shop) {
        importShops(CollectionUtil.newList(shop));
    }

    @Override
    public void saveOrUpdateShopDetail(ShopDetail detail) {
        importShopDetails(CollectionUtil.newList(detail));
    }

    @Override
    public void saveOrUpdateShopRTS(ShopRTS rts) {
        importShopRTSs(CollectionUtil.newList(rts));
    }

    @Override
    public void saveOrUpdateShopPay(ShopPay pay) {
        importShopPays(CollectionUtil.newList(pay));
    }
}
