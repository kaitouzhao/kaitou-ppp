package kaitou.ppp.service;

import kaitou.ppp.domain.shop.Shop;
import kaitou.ppp.rmi.ServiceProvider;
import kaitou.ppp.rmi.service.RemoteRegistryService;
import kaitou.ppp.rmi.service.RemoteShopService;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.List;

import static kaitou.ppp.common.utils.NetworkUtil.getLocalIp;
import static kaitou.ppp.rmi.ServiceClient.getRemoteService;

/**
 * 远程调用demo.
 * User: 赵立伟
 * Date: 2015/3/31
 * Time: 16:11
 */
public class RemoteCallDemo {

    static {
        ApplicationContext ctx = new ClassPathXmlApplicationContext(
                new String[]{
                        "applicationContext-service.xml"
                }
        );
        ServiceProvider provider = ctx.getBean(ServiceProvider.class);
//        provider.start("10.0.24.133");
//        provider.start("127.0.0.1");
    }

    public static void main(String[] args) throws RemoteException {
//        testRegister();
        testSaveShop();
    }

    private static void testSaveShop() throws RemoteException {
        List<Shop> shops = new ArrayList<Shop>();
        Shop shop = new Shop();
        shop.setId("TEST001");
        shop.setSaleRegion("华北");
        shop.setName("测试认定店1");
        shop.setPhone("13810001000");
        shops.add(shop);
        RemoteShopService remoteShopService = getRemoteService(RemoteShopService.class, "192.168.0.104");
//        remoteShopService.saveShops(shops);
        System.out.println("更新完毕");
    }

    private static void testRegister() throws RemoteException {
        RemoteRegistryService remoteRegistryService = getRemoteService(RemoteRegistryService.class, getLocalIp());
        List<String> registryIps = remoteRegistryService.register("127.0.0.1");
        System.out.println("注册ip：" + registryIps.get(0));
    }
}
