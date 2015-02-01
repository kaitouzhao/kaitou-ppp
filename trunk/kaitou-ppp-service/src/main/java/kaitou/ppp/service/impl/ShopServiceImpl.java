package kaitou.ppp.service.impl;

import com.womai.bsp.tool.utils.CollectionUtil;
import kaitou.ppp.dao.cache.CacheManager;
import kaitou.ppp.domain.engineer.Engineer;
import kaitou.ppp.domain.engineer.EngineerTraining;
import kaitou.ppp.domain.shop.*;
import kaitou.ppp.manager.engineer.EngineerManager;
import kaitou.ppp.manager.engineer.EngineerTrainingManager;
import kaitou.ppp.manager.shop.ShopDetailManager;
import kaitou.ppp.manager.shop.ShopManager;
import kaitou.ppp.manager.shop.ShopPayManager;
import kaitou.ppp.manager.shop.ShopRTSManager;
import kaitou.ppp.service.BaseExcelService;
import kaitou.ppp.service.ShopService;
import org.joda.time.DateTime;

import java.io.File;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static com.womai.bsp.tool.utils.BeanCopyUtil.copyBean;

/**
 * 认定店业务处理层实现.
 * User: 赵立伟
 * Date: 2015/1/25
 * Time: 13:24
 */
public class ShopServiceImpl extends BaseExcelService implements ShopService {

    private static final String[] EXCEL_SHOP_HEADER = {"区域", "认定店编号", "认定店名称", "合同联系人", "联系电话", "邮寄地址", "联系邮箱"};
    private static final String[] SHOP_COLUMN = {"saleRegion", "id", "name", "linkMan", "phone", "address", "email"};

    private static final String[] EXCEL_SHOP_DETAIL_HEADER = {"区域", "认定店编号", "认定店名称", "认定年份", "产品线", "认定级别", "认定机型"};
    private static final String[] SHOP_DETAIL_COLUMN = {"saleRegion", "id", "name", "numberOfYear", "productLine", "level", "model"};

    private static final String[] EXCEL_SHOP_RTS_HEADER = {"认定店编号", "认定店名称", "产品线", "RTS"};
    private static final String[] SHOP_RTS_COLUMN = {"id", "name", "productLine", "rts"};

    private static final String[] EXCEL_SHOP_PAY_HEADER = {"认定店编号", "认定店名称", "付款代码", "付款名称", "开户行", "帐号"};
    private static final String[] SHOP_PAY_COLUMN = {"id", "name", "payCode", "payName", "accountBank", "accountNo"};

    private static final String[] EXCEL_SHOP_ALL_HEADER = {"区域", "认定店编号", "认定店名称", "合同联系人", "联系电话", "邮寄地址", "联系邮箱", "认定年份", "产品线", "认定级别"};
    private static final String[] SHOP_ALL_COLUMN = {"saleRegion", "id", "name", "linkMan", "phone", "address", "email", "numberOfYear", "productLine", "level"};

    private ShopManager shopManager;
    private ShopDetailManager shopDetailManager;
    private ShopRTSManager shopRTSManager;
    private ShopPayManager shopPayManager;

    private EngineerManager engineerManager;
    private EngineerTrainingManager engineerTrainingManager;

    public void setShopRTSManager(ShopRTSManager shopRTSManager) {
        this.shopRTSManager = shopRTSManager;
    }

    public void setShopPayManager(ShopPayManager shopPayManager) {
        this.shopPayManager = shopPayManager;
    }

    public void setEngineerManager(EngineerManager engineerManager) {
        this.engineerManager = engineerManager;
    }

    public void setEngineerTrainingManager(EngineerTrainingManager engineerTrainingManager) {
        this.engineerTrainingManager = engineerTrainingManager;
    }

    public void setShopManager(ShopManager shopManager) {
        this.shopManager = shopManager;
    }

    public void setShopDetailManager(ShopDetailManager shopDetailManager) {
        this.shopDetailManager = shopDetailManager;
    }

    @Override
    public void importShops(File srcFile) {
        List<Shop> shops = readFromExcel(srcFile, "基础", EXCEL_SHOP_HEADER, SHOP_COLUMN, Shop.class);
        logOperation("成功导入认定店数：" + shopManager.importShops(shops));
    }

    @Override
    public void exportShops(File targetFile) {
        export2Excel(shopManager.query(), "基础", EXCEL_SHOP_HEADER, SHOP_COLUMN, targetFile);
    }

    @Override
    public void importShopDetails(File srcFile) {
        List<ShopDetail> shopDetails = readFromExcel(srcFile, "发展", EXCEL_SHOP_DETAIL_HEADER, SHOP_DETAIL_COLUMN, ShopDetail.class);
        int successCount = shopDetailManager.importShopDetails(shopDetails);
        logOperation("成功导入认定店明细数：" + successCount);
        if (successCount <= 0) {
            return;
        }
        Set<String> shopIds = new HashSet<String>();
        for (ShopDetail detail : shopDetails) {
            shopIds.add(detail.getId());
        }
        String[] shopIdArray = CollectionUtil.toArray(shopIds, String.class);
        List<Engineer> shopEngineers = new ArrayList<Engineer>(engineerManager.query(shopIdArray));
        List<EngineerTraining> shopEngineerTrainings = new ArrayList<EngineerTraining>(engineerTrainingManager.query(shopIdArray));
        for (Engineer engineer : shopEngineers) {
            CachedShopDetail shopDetail = shopManager.getCachedShopDetail(engineer.getShopId(), engineer.getProductLine());
            engineer.setShopLevel(shopDetail.getLevel());
            engineer.setNumberOfYear(shopDetail.getNumberOfYear());
        }
        for (EngineerTraining training : shopEngineerTrainings) {
            CachedShopDetail shopDetail = shopManager.getCachedShopDetail(training.getShopId(), training.getProductLine());
            training.setShopLevel(shopDetail.getLevel());
            training.setNumberOfYear(shopDetail.getNumberOfYear());
        }
        logOperation("成功更新工程师数：" + engineerManager.importEngineers(shopEngineers));
        logOperation("成功更新工程师培训信息数：" + engineerTrainingManager.importEngineerTrainings(shopEngineerTrainings));
    }

    @Override
    public void exportShopDetails(File targetFile, String... numberOfYear) {
        export2Excel(shopDetailManager.query(numberOfYear), "发展", EXCEL_SHOP_DETAIL_HEADER, SHOP_DETAIL_COLUMN, targetFile);
    }

    @Override
    public void deleteShop(String saleRegion, String id) {
        Shop shop = new Shop();
        shop.setSaleRegion(saleRegion);
        shop.setId(id);
        logOperation("成功删除认定店个数：" + shopManager.delete(shop));
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
    public void deleteShopPay(String id) {
        ShopPay pay = new ShopPay();
        pay.setId(id);
        logOperation("成功删除认定店账户个数：" + shopPayManager.delete(pay));
    }

    @Override
    public void deleteShopRTS(String id, String productLine) {
        ShopRTS rts = new ShopRTS();
        rts.setId(id);
        rts.setProductLine(productLine);
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
        logOperation("成功导入RTS数：" + shopRTSManager.importShops(shopRTSes));
    }

    @Override
    public void exportRTSs(File targetFile) {
        export2Excel(shopRTSManager.query(), "RTS", EXCEL_SHOP_RTS_HEADER, SHOP_RTS_COLUMN, targetFile);
    }

    @Override
    public void importPays(File srcFile) {
        List<ShopPay> shopPays = readFromExcel(srcFile, "付款信息", EXCEL_SHOP_PAY_HEADER, SHOP_PAY_COLUMN, ShopPay.class);
        logOperation("成功导入付款信息数：" + shopPayManager.importShops(shopPays));
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
}
