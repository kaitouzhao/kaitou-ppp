package kaitou.ppp.manager.engineer.impl;

import com.womai.bsp.tool.utils.CollectionUtil;
import kaitou.ppp.dao.shop.CachedShopDao;
import kaitou.ppp.domain.engineer.Engineer;
import kaitou.ppp.domain.engineer.EngineerTraining;
import kaitou.ppp.domain.shop.CachedShopDetail;
import kaitou.ppp.domain.shop.Shop;
import kaitou.ppp.domain.shop.ShopDetail;
import kaitou.ppp.domain.system.SysCode;
import kaitou.ppp.manager.BaseFileDaoManager;
import kaitou.ppp.manager.engineer.EngineerTrainingManager;
import kaitou.ppp.manager.listener.EngineerUpdateListener;
import kaitou.ppp.manager.listener.ShopUpdateListener;

import java.util.ArrayList;
import java.util.List;

import static com.womai.bsp.tool.utils.BeanCopyUtil.copyBean;

/**
 * 工程师培训信息DAO事务管理层实现.
 * User: 赵立伟
 * Date: 2015/1/24
 * Time: 22:55
 */
public class EngineerTrainingManagerImpl extends BaseFileDaoManager<EngineerTraining> implements EngineerTrainingManager, EngineerUpdateListener, ShopUpdateListener {

    private CachedShopDao cachedShopDao;

    public void setCachedShopDao(CachedShopDao cachedShopDao) {
        this.cachedShopDao = cachedShopDao;
    }

    @Override
    public Class<EngineerTraining> domainClass() {
        return EngineerTraining.class;
    }

    @Override
    public String getEntityName() {
        return domainClass().getSimpleName();
    }

    @Override
    public void updateEngineerEvent(Engineer... engineers) {
        if (CollectionUtil.isEmpty(engineers)) {
            return;
        }
        List<EngineerTraining> trainings = new ArrayList<EngineerTraining>();
        for (Engineer engineer : engineers) {
            List<EngineerTraining> list = query(engineer.getShopId());
            if (CollectionUtil.isEmpty(list)) {
                continue;
            }
            for (EngineerTraining training : list) {
                if (!training.getId().equals(engineer.getId())) {
                    continue;
                }
                copyBean(engineer, training);
            }
            trainings.addAll(list);
        }
        save(trainings);
    }

    @Override
    public void updateShopEvent(Shop... shops) {
        if (CollectionUtil.isEmpty(shops)) {
            return;
        }
        List<EngineerTraining> trainings = new ArrayList<EngineerTraining>();
        for (Shop shop : shops) {
            List<EngineerTraining> list = query(shop.getId());
            if (CollectionUtil.isEmpty(list)) {
                continue;
            }
            String shopName = shop.getName();
            String saleRegion = shop.getSaleRegion();
            for (EngineerTraining training : list) {
                training.setSaleRegion(saleRegion);
                training.setShopName(shopName);
                if (SysCode.ShopStatus.IN_THE_USE.getValue().equals(shop.getStatus())) {
                    continue;
                }
                training.setStatus(SysCode.EngineerStatus.OFF.getValue());
            }
            trainings.addAll(list);
        }
        save(trainings);
    }

    @Override
    public void updateShopDetailEvent(ShopDetail... shopDetails) {
        if (CollectionUtil.isEmpty(shopDetails)) {
            return;
        }
        List<EngineerTraining> trainings = new ArrayList<EngineerTraining>();
        for (ShopDetail shopDetail : shopDetails) {
            List<EngineerTraining> list = query(shopDetail.getId());
            if (CollectionUtil.isEmpty(list)) {
                continue;
            }
            CachedShopDetail cachedShopDetail;
            for (EngineerTraining training : list) {
                cachedShopDetail = cachedShopDao.getCachedShopDetail(training.getShopId(), training.getProductLine());
                training.setShopLevel(cachedShopDetail.getLevel());
                training.setNumberOfYear(cachedShopDetail.getNumberOfYear());
            }
            trainings.addAll(list);
        }
        save(trainings);
    }
}
