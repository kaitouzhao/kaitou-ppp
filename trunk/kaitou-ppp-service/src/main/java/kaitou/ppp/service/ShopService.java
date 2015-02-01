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

    /**
     * 删除认定店基本信息
     *
     * @param saleRegion 指定区域
     * @param id         指定编码
     */
    public void deleteShop(String saleRegion, String id);

    /**
     * 删除认定店认定信息
     *
     * @param id           指定编码
     * @param productLine  产品线
     * @param numberOfYear 认定年份
     */
    public void deleteShopDetail(String id, String productLine, String numberOfYear);

    /**
     * 删除认定店账户
     *
     * @param id 指定编码
     */
    public void deleteShopPay(String id);

    /**
     * 删除认定店RTS
     *
     * @param id          指定编码
     * @param productLine 产品线
     */
    public void deleteShopRTS(String id, String productLine);

    /**
     * 缓存全部认定店
     */
    public void cacheAllShops();

    /**
     * 导入RTS
     *
     * @param srcFile 源文件
     */
    public void importRTSs(File srcFile);

    /**
     * 导出RTS
     *
     * @param targetFile 目标文件
     */
    public void exportRTSs(File targetFile);

    /**
     * 导入付款信息
     *
     * @param srcFile 源文件
     */
    public void importPays(File srcFile);

    /**
     * 导出付款信息
     *
     * @param targetFile 目标文件
     */
    public void exportPays(File targetFile);

    /**
     * 基础信息全导出（今年的认定级别）
     *
     * @param targetFile 目标文件
     */
    public void exportAll(File targetFile);
}
