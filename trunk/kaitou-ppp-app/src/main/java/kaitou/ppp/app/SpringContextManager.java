package kaitou.ppp.app;

import kaitou.ppp.service.*;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * spring上下文管理器.
 * User: 赵立伟
 * Date: 2015/2/5
 * Time: 16:00
 */
public abstract class SpringContextManager {
    /**
     * spring上下文
     */
    private static ApplicationContext ctx;

    private static DbService dbService;
    private static CardService cardService;
    private static ShopService shopService;
    private static UpgradeService upgradeService;
    private static EngineerService engineerService;
    private static SystemSettingsService systemSettingsService;

    static {
        ctx = new ClassPathXmlApplicationContext(
                new String[]{
                        "applicationContext-service.xml"
                }
        );
    }

    public static DbService getDbService() {
        if (dbService == null) {
            dbService = ctx.getBean(DbService.class);
        }
        return dbService;
    }

    public static CardService getCardService() {
        if (cardService == null) {
            cardService = ctx.getBean(CardService.class);
        }
        return cardService;
    }

    public static ShopService getShopService() {
        if (shopService == null) {
            shopService = ctx.getBean(ShopService.class);
        }
        return shopService;
    }

    public static UpgradeService getUpgradeService() {
        if (upgradeService == null) {
            upgradeService = ctx.getBean(UpgradeService.class);
        }
        return upgradeService;
    }

    public static EngineerService getEngineerService() {
        if (engineerService == null) {
            engineerService = ctx.getBean(EngineerService.class);
        }
        return engineerService;
    }

    public static SystemSettingsService getSystemSettingsService() {
        if (systemSettingsService == null) {
            systemSettingsService = ctx.getBean(SystemSettingsService.class);
        }
        return systemSettingsService;
    }
}
