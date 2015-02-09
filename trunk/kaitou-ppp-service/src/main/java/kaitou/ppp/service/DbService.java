package kaitou.ppp.service;

/**
 * DB文件业务处理层.
 * User: 赵立伟
 * Date: 2015/1/22
 * Time: 15:45
 */
public interface DbService {

    /**
     * 备份DB文件
     */
    public void backupDB();

    /**
     * 备份DB文件
     *
     * @param targetFilePath 备份文件路径
     */
    public void backupDB(String targetFilePath);

    /**
     * 数据恢复
     *
     * @param srcFilePath 备份源文件
     */
    public void recovery(String srcFilePath);
}
