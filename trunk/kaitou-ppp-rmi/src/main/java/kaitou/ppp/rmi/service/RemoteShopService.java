package kaitou.ppp.rmi.service;

import kaitou.ppp.domain.shop.Shop;
import kaitou.ppp.domain.shop.ShopDetail;
import kaitou.ppp.domain.shop.ShopPay;
import kaitou.ppp.domain.shop.ShopRTS;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;

/**
 * 远程认定店服务.
 * User: 赵立伟
 * Date: 2015/4/5
 * Time: 15:21
 */
public interface RemoteShopService extends Remote {
    /**
     * 保存认定店基本信息
     *
     * @param shops 认定店列表
     */
    public void saveShops(List<Shop> shops) throws RemoteException;

    /**
     * 保存认定店认定级别
     *
     * @param shopDetails 认定店认定级别列表
     */
    public void saveShopDetails(List<ShopDetail> shopDetails) throws RemoteException;

    /**
     * 保存认定店帐号信息
     *
     * @param shopPays 认定店帐号列表
     */
    public void saveShopPays(List<ShopPay> shopPays) throws RemoteException;

    /**
     * 保存认定店RTS
     *
     * @param shopRTSes 认定店RTS列表
     */
    public void saveShopRTSes(List<ShopRTS> shopRTSes) throws RemoteException;
}
