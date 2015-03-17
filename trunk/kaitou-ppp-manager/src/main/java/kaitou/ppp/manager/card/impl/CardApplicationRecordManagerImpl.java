package kaitou.ppp.manager.card.impl;

import kaitou.ppp.domain.card.CardApplicationRecord;
import kaitou.ppp.manager.BaseFileDaoManager;
import kaitou.ppp.manager.card.CardApplicationRecordManager;

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
        return super.query();
    }

    @Override
    public String getEntityName() {
        return domainClass().getSimpleName();
    }
}
