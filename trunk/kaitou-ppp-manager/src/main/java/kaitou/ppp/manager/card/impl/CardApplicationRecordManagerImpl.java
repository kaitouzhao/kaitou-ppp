package kaitou.ppp.manager.card.impl;

import kaitou.ppp.domain.card.CardApplicationRecord;
import kaitou.ppp.manager.BaseFileDaoManager;
import kaitou.ppp.manager.card.CardApplicationRecordManager;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 * 保修卡生成记录DAO事务管理层实现.
 * User: 赵立伟
 * Date: 2015/3/7
 * Time: 21:41
 */
public class CardApplicationRecordManagerImpl extends BaseFileDaoManager<CardApplicationRecord> implements CardApplicationRecordManager {
    @Override
    public Class<CardApplicationRecord> domainClass() {
        return CardApplicationRecord.class;
    }

    @Override
    public List<CardApplicationRecord> query() {
        List<CardApplicationRecord> cardApplicationRecords = super.query();
        Collections.sort(cardApplicationRecords, new Comparator<CardApplicationRecord>() {
            @Override
            public int compare(CardApplicationRecord o1, CardApplicationRecord o2) {
                try {
                    return Long.valueOf(o1.getWarrantyCard().substring(5)).compareTo(Long.valueOf(o2.getWarrantyCard().substring(5)));
                } catch (Exception e) {
                    return 0;
                }
            }
        });
        return cardApplicationRecords;
    }

    @Override
    public String getEntityName() {
        return domainClass().getSimpleName();
    }
}
