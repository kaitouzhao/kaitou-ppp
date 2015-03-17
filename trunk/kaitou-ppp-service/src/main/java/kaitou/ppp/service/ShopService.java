package kaitou.ppp.service;

import kaitou.ppp.domain.shop.Shop;
import kaitou.ppp.domain.shop.ShopDetail;
import kaitou.ppp.domain.shop.ShopPay;
import kaitou.ppp.domain.shop.ShopRTS;

import java.io.File;
import java.util.List;

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
    @Deprecated
    public void deleteShop(String saleRegion, String id);

    /**
     * 删除认定店基本信息
     *
     * @param shops 认定店集合
     */
    public void deleteShops(Object... shops);

    /**
     * 删除认定店认定信息
     *
     * @param id           指定编码
     * @param productLine  产品线
     * @param numberOfYear 认定年份
     */
    @Deprecated
    public void deleteShopDetail(String id, String productLine, String numberOfYear);

    /**
     * 删除认定店认定级别
     *
     * @param details 认定级别集合
     */
    public void deleteShopDetails(Object... details);

    /**
     * 删除认定店账户
     *
     * @param id 指定编码
     */
    @Deprecated
    public void deleteShopPay(String id);

    /**
     * 删除认定店帐号信息
     *
     * @param pays 帐号信息集合
     */
    public void deleteShopPays(Object... pays);

    /**
     * 删除认定店RTS
     *
     * @param id          指定编码
     * @param productLine 产品线
     */
    @Deprecated
    public void deleteShopRTS(String id, String productLine);

    /**
     * 删除认定店RTS
     *
     * @param rts RTS集合
     */
    public void deleteShopRTSs(Object... rts);

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

    /**
     * 查询全部认定店基本信息
     *
     * @return 基本信息列表
     */
    public List<Shop> queryAllShops();

    /**
     * 查询全部认定店认定级别
     *
     * @return 认定级别列表
     */
    public List<ShopDetail> queryAllDetails();

    /**
     * 查询全部认定店RTS
     *
     * @return RTS列表
     */
    public List<ShopRTS> queryAllRTSs();

    /**
     * 查询全部认定店帐号信息
     *
     * @return 帐号信息列表
     */
    public List<ShopPay> queryAllPays();

    /**
     * 保存/更新认定店基本信息
     *
     * @param shop 认定店
     */
    public void saveOrUpdateShop(Shop shop);

    /**
     * 保存/更新认定店认定级别
     *
     * @param detail 认定级别
     */
    public void saveOrUpdateShopDetail(ShopDetail detail);

    /**
     * 保存/更新认定店RTS
     *
     * @param rts RTS
     */
    public void saveOrUpdateShopRTS(ShopRTS rts);

    /**
     * 保存/更新认定店帐号信息
     *
     * @param pay 帐号信息
     */
    public void saveOrUpdateShopPay(ShopPay pay);

    /**
     * 统计认定店设备
     *
     * @param targetFile 目标文件
     */
    public void countShopEquipment(File targetFile);
}
