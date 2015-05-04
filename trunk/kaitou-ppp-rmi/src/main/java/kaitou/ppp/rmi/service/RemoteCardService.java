package kaitou.ppp.rmi.service;

import kaitou.ppp.domain.card.CardApplicationRecord;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;

/**
 * 远程保修卡服务.
 * User: 赵立伟
 * Date: 2015/4/16
 * Time: 14:50
 */
public interface RemoteCardService extends Remote {

    /**
     * 保存保修卡生成记录
     *
     * @param records 记录列表
     */
    public void saveCardApplicationRecord(List<CardApplicationRecord> records) throws RemoteException;

    /**
     * 删除保修卡生成记录
     *
     * @param record 记录
     */
    public void deleteCardApplicationRecord(Object... record) throws RemoteException;
}
