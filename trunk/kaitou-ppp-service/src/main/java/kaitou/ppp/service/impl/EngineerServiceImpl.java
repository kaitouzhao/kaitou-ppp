package kaitou.ppp.service.impl;

import com.womai.bsp.tool.utils.CollectionUtil;
import kaitou.ppp.domain.count.CountByProductLine;
import kaitou.ppp.domain.count.CountByShop;
import kaitou.ppp.domain.engineer.Engineer;
import kaitou.ppp.domain.engineer.EngineerTraining;
import kaitou.ppp.domain.shop.CachedShop;
import kaitou.ppp.domain.shop.CachedShopDetail;
import kaitou.ppp.domain.system.SysCode;
import kaitou.ppp.manager.engineer.EngineerManager;
import kaitou.ppp.manager.engineer.EngineerTrainingManager;
import kaitou.ppp.manager.listener.EngineerUpdateListener;
import kaitou.ppp.manager.shop.ShopManager;
import kaitou.ppp.manager.system.RemoteRegistryManager;
import kaitou.ppp.manager.system.SystemSettingsManager;
import kaitou.ppp.rmi.ServiceClient;
import kaitou.ppp.rmi.service.RemoteEngineerService;
import kaitou.ppp.service.BaseExcelService;
import kaitou.ppp.service.EngineerService;
import org.apache.commons.lang.StringUtils;

import java.io.File;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.List;

import static com.womai.bsp.tool.utils.BeanCopyUtil.copyBean;

/**
 * 工程师业务处理层实现.
 * User: 赵立伟
 * Date: 2015/1/17
 * Time: 23:16
 */
public class EngineerServiceImpl extends BaseExcelService implements EngineerService {

    private ShopManager shopManager;
    private EngineerManager engineerManager;
    private SystemSettingsManager systemSettingsManager;
    private RemoteRegistryManager remoteRegistryManager;
    private EngineerTrainingManager engineerTrainingManager;
    private List<EngineerUpdateListener> engineerUpdateListeners;

    public void setSystemSettingsManager(SystemSettingsManager systemSettingsManager) {
        this.systemSettingsManager = systemSettingsManager;
    }

    public void setRemoteRegistryManager(RemoteRegistryManager remoteRegistryManager) {
        this.remoteRegistryManager = remoteRegistryManager;
    }

    public void setEngineerUpdateListeners(List<EngineerUpdateListener> engineerUpdateListeners) {
        this.engineerUpdateListeners = engineerUpdateListeners;
    }

    public void setShopManager(ShopManager shopManager) {
        this.shopManager = shopManager;
    }

    public void setEngineerTrainingManager(EngineerTrainingManager engineerTrainingManager) {
        this.engineerTrainingManager = engineerTrainingManager;
    }

    public void setEngineerManager(EngineerManager engineerManager) {
        this.engineerManager = engineerManager;
    }

    @Override
    public void importEngineers(File srcFile) {
        List<Engineer> engineers = readFromExcel(srcFile, Engineer.class);
        importEngineers(engineers);
    }

    /**
     * 导入/更新工程师
     *
     * @param engineers 工程师列表
     */
    private void importEngineers(final List<Engineer> engineers) {
        int successCount = 0;
        if (CollectionUtil.isEmpty(engineers)) {
            logOperation("成功导入/更新工程师数：" + successCount);
            return;
        }
        for (Engineer engineer : engineers) {
            CachedShop cachedShop = shopManager.getCachedShop(engineer.getShopId());
            engineer.setSaleRegion(cachedShop.getSaleRegion());
            CachedShopDetail shopDetail = shopManager.getCachedShopDetail(engineer.getShopId(), engineer.getProductLine());
            engineer.setNumberOfYear(shopDetail.getNumberOfYear());
            engineer.setShopLevel(shopDetail.getLevel());
        }
        successCount = engineerManager.save(engineers);
        logOperation("成功导入/更新工程师数：" + successCount);
        if (successCount <= 0) {
            return;
        }
        if (CollectionUtil.isEmpty(engineerUpdateListeners)) {
            return;
        }
        new Thread(new Runnable() {
            @Override
            public void run() {
                for (EngineerUpdateListener listener : engineerUpdateListeners) {
                    listener.updateEngineerEvent(CollectionUtil.toArray(engineers, Engineer.class));
                }
            }
        }).start();
        new Thread(new Runnable() {
            @Override
            public void run() {
                List<RemoteEngineerService> remoteEngineerServices = ServiceClient.queryServicesOfListener(RemoteEngineerService.class, remoteRegistryManager.queryRegistryIps(), systemSettingsManager.getLocalIp());
                if (CollectionUtil.isEmpty(remoteEngineerServices)) {
                    return;
                }
                logOperation("通知已注册的远程服务更新工程师基本信息");
                for (RemoteEngineerService remoteEngineerService : remoteEngineerServices) {
                    try {
                        remoteEngineerService.saveEngineers(engineers);
                    } catch (RemoteException e) {
                        logSystemEx(e);
                    }
                }
            }
        }).start();
    }

    @Override
    public void exportEngineers(File targetFile) {
        export2Excel(engineerManager.query(), targetFile, Engineer.class);
    }

    @Override
    public void importEngineerTrainings(File srcFile) {
        List<EngineerTraining> trainings = readFromExcel(srcFile, EngineerTraining.class);
        importEngineerTrainings(trainings);
    }

    /**
     * 导入/更新工程师发展信息
     *
     * @param trainings 发展信息列表
     */
    private void importEngineerTrainings(final List<EngineerTraining> trainings) {
        int successCount = 0;
        if (CollectionUtil.isEmpty(trainings)) {
            logOperation("成功导入/更新培训信息数：" + successCount);
            return;
        }
        List<Engineer> allEngineers = engineerManager.query();
        for (EngineerTraining training : trainings) {
            for (Engineer engineer : allEngineers) {
                if (!training.getId().equals(engineer.getId())) {
                    continue;
                }
                copyBean(engineer, training);
            }
        }
        successCount = engineerTrainingManager.save(trainings);
        logOperation("成功导入/更新工程师培训信息数：" + successCount);
        new Thread(new Runnable() {
            @Override
            public void run() {
                List<RemoteEngineerService> remoteEngineerServices = ServiceClient.queryServicesOfListener(RemoteEngineerService.class, remoteRegistryManager.queryRegistryIps(), systemSettingsManager.getLocalIp());
                if (CollectionUtil.isEmpty(remoteEngineerServices)) {
                    return;
                }
                logOperation("通知已注册的远程服务更新工程师发展信息");
                for (RemoteEngineerService remoteEngineerService : remoteEngineerServices) {
                    try {
                        remoteEngineerService.saveEngineerTrainings(trainings);
                    } catch (RemoteException e) {
                        logSystemEx(e);
                    }
                }
            }
        }).start();
    }

    @Override
    public void exportTrainings(File targetFile) {
        export2Excel(engineerTrainingManager.query(), targetFile, EngineerTraining.class);
    }

    @Override
    public void deleteEngineers(final Object... engineers) {
        logOperation("已删除工程师个数：" + engineerManager.delete(engineers));
        new Thread(new Runnable() {
            @Override
            public void run() {
                List<RemoteEngineerService> remoteEngineerServices = ServiceClient.queryServicesOfListener(RemoteEngineerService.class, remoteRegistryManager.queryRegistryIps(), systemSettingsManager.getLocalIp());
                if (CollectionUtil.isEmpty(remoteEngineerServices)) {
                    return;
                }
                logOperation("通知已注册的远程服务删除工程师基本信息");
                for (RemoteEngineerService remoteEngineerService : remoteEngineerServices) {
                    try {
                        remoteEngineerService.deleteEngineer(engineers);
                    } catch (RemoteException e) {
                        logSystemEx(e);
                    }
                }
            }
        }).start();
    }

    @Override
    public void deleteEngineerTrainings(final Object... trainings) {
        logOperation("已删除工程师发展信息个数：" + engineerTrainingManager.delete(trainings));
        new Thread(new Runnable() {
            @Override
            public void run() {
                List<RemoteEngineerService> remoteEngineerServices = ServiceClient.queryServicesOfListener(RemoteEngineerService.class, remoteRegistryManager.queryRegistryIps(), systemSettingsManager.getLocalIp());
                if (CollectionUtil.isEmpty(remoteEngineerServices)) {
                    return;
                }
                logOperation("通知已注册的远程服务删除工程师发展信息");
                for (RemoteEngineerService remoteEngineerService : remoteEngineerServices) {
                    try {
                        remoteEngineerService.deleteEngineerTrainings(trainings);
                    } catch (RemoteException e) {
                        logSystemEx(e);
                    }
                }
            }
        }).start();
    }

    @Override
    public void countEngineersByProductLine(File targetFile) {
        List<Engineer> allEngineers = engineerManager.query();
        List<CountByProductLine> result = new ArrayList<CountByProductLine>();
        CountByProductLine countByProductLine;
        String productLine;
        int index;
        for (Engineer engineer : allEngineers) {
            if (!SysCode.EngineerStatus.ON.getValue().equals(engineer.getStatus())) {
                continue;
            }
            productLine = engineer.getProductLine();
            index = result.indexOf(new CountByProductLine().setProductLine(productLine));
            if (index < 0) {
                countByProductLine = new CountByProductLine().setProductLine(productLine);
                result.add(countByProductLine);
            } else {
                countByProductLine = result.get(index);
            }
            countByProductLine.setCount(countByProductLine.getCount() + 1);
        }
        export2Excel(result, "产品线在职工程师统计", new String[]{"产品线", "人数"}, new String[]{"productLine", "count"}, targetFile);
    }

    @Override
    public void countEngineersByShop(String productLine, File targetFile) {
        List<Engineer> allEngineers = engineerManager.query();
        List<CountByShop> result = new ArrayList<CountByShop>();
        CountByShop countByShop;
        String shopId;
        String engineerProductLine;
        int index;
        for (Engineer engineer : allEngineers) {
            if (!SysCode.EngineerStatus.ON.getValue().equals(engineer.getStatus())) {
                continue;
            }
            if (!StringUtils.isEmpty(productLine)) {
                if (!productLine.equals(engineer.getProductLine())) {
                    continue;
                }
            }
            shopId = engineer.getShopId();
            engineerProductLine = engineer.getProductLine();
            index = result.indexOf(new CountByShop().setShopId(shopId).setProductLine(engineerProductLine));
            if (index < 0) {
                countByShop = new CountByShop().setShopId(shopId).setShopName(engineer.getShopName()).setProductLine(engineerProductLine);
                result.add(countByShop);
            } else {
                countByShop = result.get(index);
            }
            countByShop.setCount(countByShop.getCount() + 1);
        }
        export2Excel(result, "认定店在职工程师统计", new String[]{"认定店编码", "认定店名", "产品线", "人数"}, new String[]{"shopId", "shopName", "productLine", "count"}, targetFile);
    }

    @Override
    @SuppressWarnings("unchecked")
    public List<Engineer> queryAllEngineers() {
        return engineerManager.query();
    }

    @Override
    @SuppressWarnings("unchecked")
    public List<EngineerTraining> queryAllTrainings() {
        return engineerTrainingManager.query();
    }

    @Override
    @SuppressWarnings("unchecked")
    public void saveOrUpdateEngineer(Engineer engineer) {
        importEngineers(CollectionUtil.newList(engineer));
    }

    @Override
    @SuppressWarnings("unchecked")
    public void saveOrUpdateEngineerTraining(EngineerTraining training) {
        importEngineerTrainings(CollectionUtil.newList(training));
    }
}
