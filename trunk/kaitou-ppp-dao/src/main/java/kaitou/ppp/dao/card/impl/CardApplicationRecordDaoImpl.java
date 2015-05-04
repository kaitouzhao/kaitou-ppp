package kaitou.ppp.dao.card.impl;

import kaitou.ppp.dao.BaseDao4InDoubt;
import kaitou.ppp.dao.card.CardApplicationRecordDao;
import kaitou.ppp.domain.card.CardApplicationRecord;

import java.util.List;

/**
 * 保修卡生成记录DAO实现.
 * User: 赵立伟
 * Date: 2015/3/7
 * Time: 21:36
 */
public class CardApplicationRecordDaoImpl extends BaseDao4InDoubt<CardApplicationRecord> implements CardApplicationRecordDao {
    @Override
    public Class<CardApplicationRecord> getDomainClass() {
        return CardApplicationRecord.class;
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<CardApplicationRecord> query() {
        return super.query();
    }
}
