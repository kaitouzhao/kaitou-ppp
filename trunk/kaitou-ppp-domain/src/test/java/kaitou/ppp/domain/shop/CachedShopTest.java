package kaitou.ppp.domain.shop;

import com.womai.bsp.tool.utils.CollectionUtil;
import org.junit.Before;
import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

import static com.womai.bsp.tool.utils.JsonUtil.json2Object;
import static com.womai.bsp.tool.utils.JsonUtil.object2Json;
import static org.junit.Assert.*;

/**
 * CachedShop单元测试.
 * User: 赵立伟
 * Date: 2015/1/28
 * Time: 13:11
 */
public class CachedShopTest {

    private static Map<String, String> cachedMap = new HashMap<String, String>();

    @Before
    public void test2Json() {
        CachedShop shop1 = new CachedShop();
        String shop0001 = "SHOP0001";
        shop1.setId(shop0001);
        shop1.setName("测试店1");
        CachedShopDetail detail1 = new CachedShopDetail();
        String productLine1 = "M1";
        detail1.setProductLine(productLine1);
        detail1.setNumberOfYear("2013");
        detail1.setLevel("1");
        shop1.addDetail(detail1);
        CachedShopDetail detail2 = new CachedShopDetail();
        String productLine2 = "M2";
        detail2.setProductLine(productLine2);
        detail2.setNumberOfYear("2014");
        detail2.setLevel("2");
        shop1.addDetail(detail2);
        cachedMap.put(shop0001, object2Json(shop1));

        CachedShop shop2 = new CachedShop();
        String shop0002 = "SHOP0002";
        shop2.setId(shop0002);
        shop2.setName("测试店2");
        cachedMap.put(shop0002, object2Json(shop2));
    }

    @Test
    public void test2Object() {
        CachedShop shop1 = json2Object(cachedMap.get("SHOP0001"), CachedShop.class);
        assertEquals("测试店1", shop1.getName());
        CachedShopDetail detail1 = shop1.getDetail("M1");
        assertEquals("2013", detail1.getNumberOfYear());
        assertEquals("1", detail1.getLevel());

        CachedShop shop2 = json2Object(cachedMap.get("SHOP0002"), CachedShop.class);
        assertEquals("测试店2", shop2.getName());
        assertTrue(CollectionUtil.isEmpty(shop2.getDetails()));

        shop2.updateDetail(detail1);
        assertFalse(CollectionUtil.isEmpty(shop2.getDetails()));

        shop2.updateDetail(new CachedShopDetail().setProductLine("M1").setNumberOfYear("2014").setLevel("2"));
        assertEquals(1, shop2.getDetails().size());
        assertEquals("2014", shop2.getDetails().get(0).getNumberOfYear());
        assertEquals("2", shop2.getDetails().get(0).getLevel());

        shop2.updateDetail(new CachedShopDetail().setProductLine("M1").setNumberOfYear("2012").setLevel("1"));
        assertEquals(1, shop2.getDetails().size());
        assertEquals("2014", shop2.getDetails().get(0).getNumberOfYear());
        assertEquals("2", shop2.getDetails().get(0).getLevel());
    }
}
