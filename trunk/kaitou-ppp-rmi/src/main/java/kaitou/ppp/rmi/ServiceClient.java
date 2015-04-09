package kaitou.ppp.rmi;

import com.womai.bsp.tool.utils.CollectionUtil;
import org.apache.commons.lang.StringUtils;

import java.net.MalformedURLException;
import java.rmi.*;
import java.util.List;

/**
 * 远程服务调用客户端.
 * User: 赵立伟
 * Date: 2015/3/31
 * Time: 15:37
 */
public abstract class ServiceClient {
    /**
     * 获取远程服务
     *
     * @param serviceClass 服务类型
     * @param remoteIp     远程服务IP
     * @return 具体远程服务
     */
    @SuppressWarnings("unchecked")
    public static <T extends Remote> T getRemoteService(Class<T> serviceClass, String remoteIp) {
        if (serviceClass == null || StringUtils.isEmpty(remoteIp)) {
            return null;
        }
        try {
            return (T) Naming.lookup("rmi://" + remoteIp + ':' + Constants.REMOTE_PORT + '/' + serviceClass.getSimpleName());
        } catch (NotBoundException e) {
            return null;
        } catch (MalformedURLException e) {
            throw new RuntimeException("调用该服务出错", e);
        } catch (RemoteException e) {
            if (e instanceof ConnectException) {
                return null;
            }
            throw new RuntimeException("调用该服务出错", e);
        }
    }

    /**
     * 获取已注册的监听服务
     * <p>
     * 排除本机IP
     * </p>
     *
     * @param serviceClass 监听服务类型
     * @param registryIps  已注册的监听IP
     * @param localIp      本机IP
     * @return 已注册的监听服务
     */
    @SuppressWarnings("unchecked")
    public static <T extends Remote> List<T> queryServicesOfListener(Class<T> serviceClass, List<String> registryIps, String localIp) {
        List<T> services = CollectionUtil.newList();
        if (serviceClass == null || CollectionUtil.isEmpty(registryIps)) {
            return services;
        }
        for (String registryIp : registryIps) {
            if (registryIp.equals(localIp)) {
                continue;
            }
            T service = getRemoteService(serviceClass, registryIp);
            if (service == null) {
                continue;
            }
            services.add(service);
        }
        return services;
    }
}
