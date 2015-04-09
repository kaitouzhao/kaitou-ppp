package kaitou.ppp.rmi;

import com.womai.bsp.tool.utils.CollectionUtil;

import java.rmi.ConnectException;
import java.rmi.Naming;
import java.rmi.Remote;
import java.rmi.registry.LocateRegistry;
import java.util.Map;

/**
 * 远程服务提供端.
 * User: 赵立伟
 * Date: 2015/3/31
 * Time: 15:23
 */
public class ServiceProvider {
    /**
     * 远程服务集合
     * <ul>
     * <li>key：服务名</li>
     * <li>value：服务类</li>
     * </ul>
     */
    private Map<String, Remote> remoteServices;

    public void setRemoteServices(Map<String, Remote> remoteServices) {
        this.remoteServices = remoteServices;
    }

    /**
     * 启动本机的远程服务监听
     *
     * @param localIp 本机ip
     * @return 启动成功为真，否则为假
     */
    public boolean start(String localIp) {
        if (CollectionUtil.isEmpty(remoteServices)) {
            return false;
        }
        try {
            LocateRegistry.createRegistry(Constants.REMOTE_PORT);
            for (Map.Entry<String, Remote> remoteService : remoteServices.entrySet()) {
                Naming.bind("rmi://" + localIp + ':' + Constants.REMOTE_PORT + '/' + remoteService.getKey(), remoteService.getValue());
            }
            return true;
        } catch (Exception e) {
            if (!(e instanceof ConnectException)) {
                throw new RuntimeException("启动本机的远程服务监听异常", e);
            }
            return false;
        }
    }
}
