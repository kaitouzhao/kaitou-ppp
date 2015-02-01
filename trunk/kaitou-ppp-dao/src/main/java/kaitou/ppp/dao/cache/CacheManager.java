package kaitou.ppp.dao.cache;

import java.util.HashMap;
import java.util.Map;

/**
 * 缓存管理.
 * User: 赵立伟
 * Date: 2015/1/28
 * Time: 16:22
 */
public abstract class CacheManager {
    /**
     * 缓存的认定店
     */
    public static Map<String, String> cachedShopMap = new HashMap<String, String>();
}
