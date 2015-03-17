package kaitou.ppp.app.ui.table.queryobject;

import kaitou.ppp.domain.card.CardApplicationRecord;

/**
 * 保修卡查询对象.
 * User: 赵立伟
 * Date: 2015/3/7
 * Time: 22:15
 */
public class CardApplicationRecordQueryObject extends BaseQueryObject {
    @Override
    public Class domainClass() {
        return CardApplicationRecord.class;
    }
}
