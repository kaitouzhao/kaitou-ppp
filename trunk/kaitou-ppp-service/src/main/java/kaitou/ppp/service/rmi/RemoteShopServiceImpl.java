package kaitou.ppp.service.rmi;

import com.womai.bsp.tool.utils.CollectionUtil;
import kaitou.ppp.domain.shop.Shop;
import kaitou.ppp.domain.shop.ShopDetail;
import kaitou.ppp.domain.shop.ShopPay;
import kaitou.ppp.domain.shop.ShopRTS;
import kaitou.ppp.manager.listener.ShopUpdateListener;
import kaitou.ppp.manager.shop.ShopDetailManager;
import kaitou.ppp.manager.shop.ShopManager;
import kaitou.ppp.manager.shop.ShopPayManager;
import kaitou.ppp.manager.shop.ShopRTSManager;
import kaitou.ppp.rmi.service.RemoteShopService;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.List;

/**
 * 远程认定店服务实现.
 * User: 赵立伟
 * Date: 2015/4/5
 * Time: 15:35
 */
public class RemoteShopServiceImpl extends UnicastRemoteObject implements RemoteShopService {

    private ShopManager shopManager;
    private ShopRTSManager shopRTSManager;
    private ShopPayManager shopPayManager;
    private ShopDetailManager shopDetailManager;
    private List<ShopUpdateListener> shopUpdateListeners;

    public void setShopUpdateListeners(List<ShopUpdateListener> shopUpdateListeners) {
        this.shopUpdateListeners = shopUpdateListeners;
    }

    public void setShopManager(ShopManager shopManager) {
        this.shopManager = shopManager;
    }

    public void setShopRTSManager(ShopRTSManager shopRTSManager) {
        this.shopRTSManager = shopRTSManager;
    }

    public void setShopPayManager(ShopPayManager shopPayManager) {
        this.shopPayManager = shopPayManager;
    }

    public void setShopDetailManager(ShopDetailManager shopDetailManager) {
        this.shopDetailManager = shopDetailManager;
    }

    public RemoteShopServiceImpl() throws RemoteException {
        super();
    }

    @Override
    public void saveShops(final List<Shop> shops) throws RemoteException {
        shopManager.save(shops);
        if (CollectionUtil.isEmpty(shopUpdateListeners)) {
            return;
        }
        new Thread(new Runnable() {
            @Override
            public void run() {
                for (ShopUpdateListener listener : shopUpdateListeners) {
                    listener.updateShopEvent(CollectionUtil.toArray(shops, Shop.class));
                }
            }
        }).start();
    }

    @Override
    public void saveShopDetails(final List<ShopDetail> shopDetails) throws RemoteException {
        shopDetailManager.save(shopDetails);
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
    }

    @Override
    public void saveShopPays(List<ShopPay> shopPays) throws RemoteException {
        shopPayManager.save(shopPays);
    }

    @Override
    public void saveShopRTSes(List<ShopRTS> shopRTSes) throws RemoteException {
        shopRTSManager.save(shopRTSes);
    }

    @Override
    public void deleteShop(Object... shop) throws RemoteException {
        shopManager.delete(shop);
    }

    @Override
    public void deleteShopDetail(Object... shopDetail) throws RemoteException {
        shopDetailManager.delete(shopDetail);
    }

    @Override
    public void deleteShopPay(Object... shopPay) throws RemoteException {
        shopPayManager.delete(shopPay);
    }

    @Override
    public void deleteShopRTS(Object... shopRTS) throws RemoteException {
        shopRTSManager.delete(shopRTS);
    }
}
