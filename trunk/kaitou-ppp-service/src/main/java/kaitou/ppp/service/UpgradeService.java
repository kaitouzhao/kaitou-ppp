package kaitou.ppp.service;

/**
 * 版本升级操作.
 * User: 赵立伟
 * Date: 2015/2/8
 * Time: 0:13
 */
public interface UpgradeService {
    /**
     * 升级至1.4操作
     * <p>将区域名开头的db文件改名为区域编码开头</p>
     */
    public void upgradeTo1Dot4();
}
