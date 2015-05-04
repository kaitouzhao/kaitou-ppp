package kaitou.ppp.manager;

import kaitou.ppp.dao.system.LocalDBVersionDao;

/**
 * 文件DAO事务管理父类.
 * User: 赵立伟
 * Date: 2015/1/17
 * Time: 19:48
 */
public abstract class FileDaoManager {
    private LocalDBVersionDao localDBVersionDao;

    public LocalDBVersionDao getLocalDBVersionDao() {
        return localDBVersionDao;
    }

    public void setLocalDBVersionDao(LocalDBVersionDao localDBVersionDao) {
        this.localDBVersionDao = localDBVersionDao;
    }

    /**
     * DB文件目录
     */
    protected String dbDir;
    /**
     * 是否新事务
     */
    protected static ThreadLocal<Boolean> newTransaction = new ThreadLocal<Boolean>();

    /**
     * 获取实体名
     *
     * @return 实体名
     */
    public abstract String getEntityName();

    /**
     * 开启新事务
     * <p>
     * 如果已有新事务，则不开启
     * </p>
     *
     * @return 是否已开启新事务。是为真
     */
    public boolean openTransaction() {
        if (isTransactionOpen()) {
            return isTransactionOpen();
        }
        newTransaction.set(true);
        return isTransactionOpen();
    }

    /**
     * 事务是否开启
     *
     * @return 开启为真
     */
    public boolean isTransactionOpen() {
        return newTransaction.get() != null && newTransaction.get();
    }

    /**
     * 关闭原事务
     * <p>
     * 如果没有新事务，则不关闭
     * </p>
     *
     * @return 是否有新事务开启。无为假
     */
    public boolean closeTransaction() {
        if (!isTransactionOpen()) {
            return isTransactionOpen();
        }
        newTransaction.set(false);
        return isTransactionOpen();
    }

    public void setDbDir(String dbDir) {
        this.dbDir = dbDir;
    }

    public String getDbDir() {
        return dbDir;
    }
}
