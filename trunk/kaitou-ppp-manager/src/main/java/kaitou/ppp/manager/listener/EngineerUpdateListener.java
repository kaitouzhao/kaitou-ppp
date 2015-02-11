package kaitou.ppp.manager.listener;

import kaitou.ppp.domain.engineer.Engineer;

/**
 * 工程师更新监听对象接口.
 * User: 赵立伟
 * Date: 2015/2/9
 * Time: 10:20
 */
public interface EngineerUpdateListener {
    /**
     * 更新工程师基本信息事件
     *
     * @param engineers 工程师集合
     */
    public void updateEngineerEvent(Engineer... engineers);
}
