package kaitou.ppp.dao.card.impl;

import kaitou.ppp.dao.BaseDao;
import kaitou.ppp.domain.card.CardApplicationRecord;

/**
 * 保修卡生成记录DAO实现.
 * User: 赵立伟
 * Date: 2015/3/7
 * Time: 21:36
 */
public class CardApplicationRecordDaoImpl extends BaseDao<CardApplicationRecord> {
    @Override
    public Class<CardApplicationRecord> getDomainClass() {
        return CardApplicationRecord.class;
    }
}
