package kaitou.ppp.service;

import java.io.File;

/**
 * 工程师业务处理层.
 * User: 赵立伟
 * Date: 2015/1/17
 * Time: 22:12
 */
public interface EngineerService {
    /**
     * 导入工程师
     *
     * @param srcFile 源文件
     */
    public void importEngineers(File srcFile);

    /**
     * 导出全部工程师
     *
     * @param targetFile 目标文件
     */
    public void exportEngineers(File targetFile);

    /**
     * 导入工程师培训信息
     *
     * @param srcFile 源文件
     */
    public void importEngineerTrainings(File srcFile);
}
