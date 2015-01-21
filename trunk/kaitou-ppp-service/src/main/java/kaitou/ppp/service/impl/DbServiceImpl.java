package kaitou.ppp.service.impl;

import kaitou.ppp.service.DbService;

import static kaitou.ppp.common.utils.ZipUtil.zip;

/**
 * 数据业务层实现.
 * User: 赵立伟
 * Date: 2015/1/21
 * Time: 10:37
 */
public class DbServiceImpl implements DbService {

    private String dbDir;
    private String backDbFile;

    public void setDbDir(String dbDir) {
        this.dbDir = dbDir;
    }

    public void setBackDbFile(String backDbFile) {
        this.backDbFile = backDbFile;
    }

    @Override
    public void backupDbFile() {
        zip(dbDir, backDbFile);
    }
}
