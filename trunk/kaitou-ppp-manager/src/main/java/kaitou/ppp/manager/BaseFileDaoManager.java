package kaitou.ppp.manager;

import com.womai.bsp.tool.utils.CollectionUtil;
import kaitou.ppp.dao.BaseDao;
import kaitou.ppp.domain.BaseDomain;

import java.util.List;

/**
 * 业务DAO事务控制层基类.
 * User: 赵立伟
 * Date: 2015/2/28
 * Time: 16:19
 *
 * @see kaitou.ppp.manager.FileDaoManager
 */
public abstract class BaseFileDaoManager<T extends BaseDomain> extends FileDaoManager {

    private BaseDao dao;

    public void setDao(BaseDao dao) {
        this.dao = dao;
    }

    public abstract Class<T> domainClass();

    /**
     * 保存
     *
     * @param domainList 实体列表
     * @return 成功保存个数
     */
    public int save(List<T> domainList) {
        return dao.save(CollectionUtil.toArray(domainList, domainClass()));
    }

    /**
     * 查询
     *
     * @param dbType DB文件类型。如果为空，则默认获取全部
     * @return 实体列表
     */
    @SuppressWarnings("unchecked")
    public List<T> query(String... dbType) {
        return dao.query(dbType);
    }

    /**
     * 删除
     *
     * @param domains 待删除集合
     * @return 成功删除个数
     */
    public int delete(Object... domains) {
        return dao.delete(domains);
    }
}
