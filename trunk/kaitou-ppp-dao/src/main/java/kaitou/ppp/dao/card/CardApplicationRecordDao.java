package kaitou.ppp.dao.card;

import kaitou.ppp.domain.card.CardApplicationRecord;

import java.util.List;

/**
 * 保修卡生成记录DAO.
 * User: 赵立伟
 * Date: 2015/3/7
 * Time: 21:33
 */
public interface CardApplicationRecordDao {

    /**
     * 添加/更新
     *
     * @param cardApplicationRecords 保修卡生成记录集合，可以是单个，也可是多个
     * @return 成功记录数
     */
    public int save(Object... cardApplicationRecords);

    /**
     * 查询保修卡生成记录
     *
     * @return 生成记录
     */
    public List<CardApplicationRecord> query();

    /**
     * 删除
     *
     * @param cardApplicationRecords 待删除集合。支持一个或多个
     * @return 成功执行记录数
     */
    public int delete(Object... cardApplicationRecords);
}
