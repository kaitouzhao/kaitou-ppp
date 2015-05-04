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
    @Deprecated
    public void upgradeTo1Dot4();

    /**
     * 升级至2.1操作
     * <ul>
     * <li>将系统设置、远程注册表移到conf目录</li>
     * <li>重新导入已有保修卡记录</li>
     * </ul>
     */
    public void upgradeTo2Dot1();
}
