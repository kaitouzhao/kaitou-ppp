package kaitou.ppp.service.rmi;

import kaitou.ppp.domain.card.CardApplicationRecord;
import kaitou.ppp.manager.card.CardApplicationRecordManager;
import kaitou.ppp.rmi.service.RemoteCardService;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.List;

/**
 * 远程保修卡服务实现.
 * User: 赵立伟
 * Date: 2015/4/16
 * Time: 14:52
 */
public class RemoteCardServiceImpl extends UnicastRemoteObject implements RemoteCardService {

    private CardApplicationRecordManager cardApplicationRecordManager;

    public void setCardApplicationRecordManager(CardApplicationRecordManager cardApplicationRecordManager) {
        this.cardApplicationRecordManager = cardApplicationRecordManager;
    }

    public RemoteCardServiceImpl() throws RemoteException {
        super();
    }

    @Override
    public void saveCardApplicationRecord(List<CardApplicationRecord> records) throws RemoteException {
        cardApplicationRecordManager.save(records);
    }

    @Override
    public void deleteCardApplicationRecord(Object... record) throws RemoteException {
        cardApplicationRecordManager.delete(record);
    }
}
