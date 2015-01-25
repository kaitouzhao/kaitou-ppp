package kaitou.ppp.service;

import java.io.File;

/**
 * 认定店业务处理层.
 * User: 赵立伟
 * Date: 2015/1/25
 * Time: 13:22
 */
public interface ShopService {
    /**
     * 导入认定店
     *
     * @param srcFile 源文件
     */
    public void importShops(File srcFile);

    /**
     * 导出认定店
     *
     * @param targetFile 目标文件
     */
    public void exportShops(File targetFile);

    /**
     * 导入认定店明细
     *
     * @param srcFile 源文件
     */
    public void importShopDetails(File srcFile);

    /**
     * 导出认定店明细
     *
     * @param targetFile   目标文件
     * @param numberOfYear 查询年份，可以是单年，也可以是多年
     */
    public void exportShopDetails(File targetFile, String... numberOfYear);
}
