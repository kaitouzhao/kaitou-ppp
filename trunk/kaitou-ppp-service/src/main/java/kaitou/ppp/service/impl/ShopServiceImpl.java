package kaitou.ppp.service.impl;

import com.womai.bsp.tool.utils.CollectionUtil;
import kaitou.ppp.dao.cache.CacheManager;
import kaitou.ppp.domain.card.CardApplicationRecord;
import kaitou.ppp.domain.shop.*;
import kaitou.ppp.manager.card.CardApplicationRecordManager;
import kaitou.ppp.manager.listener.ShopUpdateListener;
import kaitou.ppp.manager.shop.ShopDetailManager;
import kaitou.ppp.manager.shop.ShopManager;
import kaitou.ppp.manager.shop.ShopPayManager;
import kaitou.ppp.manager.shop.ShopRTSManager;
import kaitou.ppp.manager.system.RemoteRegistryManager;
import kaitou.ppp.manager.system.SystemSettingsManager;
import kaitou.ppp.rmi.service.RemoteShopService;
import kaitou.ppp.service.BaseExcelService;
import kaitou.ppp.service.ShopService;
import org.joda.time.DateTime;

import java.io.File;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.List;

import static com.womai.bsp.tool.utils.BeanCopyUtil.copyBean;
import static kaitou.ppp.rmi.ServiceClient.queryServicesOfListener;

/**
 * 认定店业务处理层实现.
 * User: 赵立伟
 * Date: 2015/1/25
 * Time: 13:24
 */
public class ShopServiceImpl extends BaseExcelService implements ShopService {

    private ShopManager shopManager;
    private ShopRTSManager shopRTSManager;
    private ShopPayManager shopPayManager;
    private ShopDetailManager shopDetailManager;
    private SystemSettingsManager systemSettingsManager;
    private RemoteRegistryManager remoteRegistryManager;
    private List<ShopUpdateListener> shopUpdateListeners;
    private CardApplicationRecordManager cardApplicationRecordManager;

    public void setSystemSettingsManager(SystemSettingsManager systemSettingsManager) {
        this.systemSettingsManager = systemSettingsManager;
    }

    public void setRemoteRegistryManager(RemoteRegistryManager remoteRegistryManager) {
        this.remoteRegistryManager = remoteRegistryManager;
    }

    public void setCardApplicationRecordManager(CardApplicationRecordManager cardApplicationRecordManager) {
        this.cardApplicationRecordManager = cardApplicationRecordManager;
    }

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
        List<Shop> shops = readFromExcel(srcFile, Shop.class);
        importShops(shops);
    }

    /**
     * 导入/更新认定店
     *
     * @param shops 认定店列表
     */
    private void importShops(final List<Shop> shops) {
        int successCount = shopManager.save(shops);
        logOperation("成功导入/更新认定店数：" + successCount);
        if (successCount <= 0) {
            return;
        }
        if (CollectionUtil.isEmpty(shopUpdateListeners)) {
            return;
        }
        new Thread(new Runnable() {
            @Override
            public void run() {
                logOperation("联动更新与认定店基本信息相关的数据");
                for (ShopUpdateListener listener : shopUpdateListeners) {
                    listener.updateShopEvent(CollectionUtil.toArray(shops, Shop.class));
                }
            }
        }).start();
        new Thread(new Runnable() {
            @Override
            public void run() {
                List<RemoteShopService> remoteShopServices = queryServicesOfListener(RemoteShopService.class, remoteRegistryManager.queryRegistryIps(), systemSettingsManager.getLocalIp());
                if (CollectionUtil.isEmpty(remoteShopServices)) {
                    return;
                }
                logOperation("通知已注册的远程服务更新认定店基本信息");
                for (RemoteShopService remoteShopService : remoteShopServices) {
                    try {
                        remoteShopService.saveShops(shops);
                    } catch (RemoteException e) {
                        logSystemEx(e);
                    }
                }
            }
        }).start();
    }

    @Override
    public void exportShops(File targetFile) {
        export2Excel(shopManager.query(), targetFile, Shop.class);
    }

    @Override
    public void importShopDetails(File srcFile) {
        List<ShopDetail> shopDetails = readFromExcel(srcFile, ShopDetail.class);
        importShopDetails(shopDetails);
    }

    /**
     * 导入/更新认定店认定级别
     *
     * @param shopDetails 认定级别列表
     */
    private void importShopDetails(final List<ShopDetail> shopDetails) {
        int successCount = 0;
        if (CollectionUtil.isEmpty(shopDetails)) {
            logOperation("成功导入/更新认定店认定级别数：" + successCount);
            return;
        }
        for (ShopDetail shopDetail : shopDetails) {
            CachedShop cachedShop = shopManager.getCachedShop(shopDetail.getId());
            shopDetail.setSaleRegion(cachedShop.getSaleRegion());
        }
        successCount = shopDetailManager.save(shopDetails);
        logOperation("成功导入/更新认定店认定级别数：" + successCount);
        if (successCount <= 0) {
            return;
        }
        if (CollectionUtil.isEmpty(shopUpdateListeners)) {
            return;
        }
        new Thread(new Runnable() {
            @Override
            public void run() {
                for (ShopUpdateListener listener : shopUpdateListeners) {
                    listener.updateShopDetailEvent(CollectionUtil.toArray(shopDetails, ShopDetail.class));
                }
            }
        }).start();
        new Thread(new Runnable() {
            @Override
            public void run() {
                List<RemoteShopService> remoteShopServices = queryServicesOfListener(RemoteShopService.class, remoteRegistryManager.queryRegistryIps(), systemSettingsManager.getLocalIp());
                if (CollectionUtil.isEmpty(remoteShopServices)) {
                    return;
                }
                logOperation("通知已注册的远程服务更新认定店认定级别");
                for (RemoteShopService remoteShopService : remoteShopServices) {
                    try {
                        remoteShopService.saveShopDetails(shopDetails);
                    } catch (RemoteException e) {
                        logSystemEx(e);
                    }
                }
            }
        }).start();
    }

    @Override
    public void exportShopDetails(File targetFile, String... numberOfYear) {
        export2Excel(shopDetailManager.query(numberOfYear), targetFile, ShopDetail.class);
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
        List<ShopRTS> shopRTSes = readFromExcel(srcFile, ShopRTS.class);
        importShopRTSs(shopRTSes);
    }

    /**
     * 导入/更新认定店RTS
     *
     * @param shopRTSes rts列表
     */
    private void importShopRTSs(final List<ShopRTS> shopRTSes) {
        logOperation("成功导入/更新RTS数：" + shopRTSManager.save(shopRTSes));
        new Thread(new Runnable() {
            @Override
            public void run() {
                List<RemoteShopService> remoteShopServices = queryServicesOfListener(RemoteShopService.class, remoteRegistryManager.queryRegistryIps(), systemSettingsManager.getLocalIp());
                if (CollectionUtil.isEmpty(remoteShopServices)) {
                    return;
                }
                logOperation("通知已注册的远程服务更新认定店RTS");
                for (RemoteShopService remoteShopService : remoteShopServices) {
                    try {
                        remoteShopService.saveShopRTSes(shopRTSes);
                    } catch (RemoteException e) {
                        logSystemEx(e);
                    }
                }
            }
        }).start();
    }

    @Override
    public void exportRTSs(File targetFile) {
        export2Excel(shopRTSManager.query(), targetFile, ShopRTS.class);
    }

    @Override
    public void importPays(File srcFile) {
        List<ShopPay> shopPays = readFromExcel(srcFile, ShopPay.class);
        importShopPays(shopPays);
    }

    /**
     * 导入/更新认定店帐号信息
     *
     * @param shopPays 帐号信息列表
     */
    private void importShopPays(final List<ShopPay> shopPays) {
        logOperation("成功导入/更新认定店帐号信息数：" + shopPayManager.save(shopPays));
        new Thread(new Runnable() {
            @Override
            public void run() {
                List<RemoteShopService> remoteShopServices = queryServicesOfListener(RemoteShopService.class, remoteRegistryManager.queryRegistryIps(), systemSettingsManager.getLocalIp());
                if (CollectionUtil.isEmpty(remoteShopServices)) {
                    return;
                }
                logOperation("通知已注册的远程服务更新认定店帐号信息");
                for (RemoteShopService remoteShopService : remoteShopServices) {
                    try {
                        remoteShopService.saveShopPays(shopPays);
                    } catch (RemoteException e) {
                        logSystemEx(e);
                    }
                }
            }
        }).start();
    }

    @Override
    public void exportPays(File targetFile) {
        export2Excel(shopPayManager.query(), targetFile, ShopPay.class);
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
        export2Excel(shopAlls, targetFile, ShopAll.class);
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
    @SuppressWarnings("unchecked")
    public void saveOrUpdateShop(Shop shop) {
        importShops(CollectionUtil.newList(shop));
    }

    @Override
    @SuppressWarnings("unchecked")
    public void saveOrUpdateShopDetail(ShopDetail detail) {
        importShopDetails(CollectionUtil.newList(detail));
    }

    @Override
    @SuppressWarnings("unchecked")
    public void saveOrUpdateShopRTS(ShopRTS rts) {
        importShopRTSs(CollectionUtil.newList(rts));
    }

    @Override
    @SuppressWarnings("unchecked")
    public void saveOrUpdateShopPay(ShopPay pay) {
        importShopPays(CollectionUtil.newList(pay));
    }

    @Override
    public void countShopEquipment(File targetFile) {
        List<CardApplicationRecord> cardApplicationRecords = cardApplicationRecordManager.query();
        List<ShopEquipmentCount> counts = new ArrayList<ShopEquipmentCount>();
        for (CardApplicationRecord cardApplicationRecord : cardApplicationRecords) {
            ShopEquipmentCount count = new ShopEquipmentCount();
            count.setShopId(cardApplicationRecord.getShopId());
            int index = counts.indexOf(count);
            if (index < 0) {
                count.setShopName(cardApplicationRecord.getShopName());
                counts.add(count);
            } else {
                count = counts.get(index);
            }
            if ("DGS".equals(cardApplicationRecord.getAllModels())) {
                count.setDgs(count.getDgs() + 1);
                continue;
            }
            if ("DP".equals(cardApplicationRecord.getAllModels())) {
                count.setDp(count.getDp() + 1);
                continue;
            }
            if ("PGA".equals(cardApplicationRecord.getAllModels())) {
                count.setPga(count.getPga() + 1);
                continue;
            }
            if ("TDS".equals(cardApplicationRecord.getAllModels())) {
                count.setTds(count.getTds() + 1);
                continue;
            }
            if ("IPF".equals(cardApplicationRecord.getAllModels())) {
                count.setIpf(count.getIpf() + 1);
            }
        }
        export2Excel(counts, targetFile, ShopEquipmentCount.class);
    }
}
