package kaitou.ppp.tool;

import com.womai.bsp.tool.utils.CollectionUtil;
import com.womai.bsp.tool.utils.ExcelUtil;
import kaitou.ppp.domain.shop.Shop;
import kaitou.ppp.domain.shop.ShopDetail;
import kaitou.ppp.service.BaseExcelService;
import kaitou.ppp.service.ShopService;
import org.apache.commons.lang.StringUtils;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * 筛选认定店.
 * User: 赵立伟
 * Date: 2015/3/31
 * Time: 10:50
 */
public class ScreeningShops {

    private static ShopService shopService;

    private static final String DIR = "d://ppp//";

    /**
     * 主程序入口
     *
     * @param args 参数
     */
    public static void main(String[] args) {
        shopService = new ClassPathXmlApplicationContext(
                new String[]{
                        "applicationContext-service.xml"
                }
        ).getBean(ShopService.class);

//        screenShops();
        screenShopDetails();
    }

    private static void screenShops() {
        File srcFile = new File(DIR + "认定店原始基础数据.xlsx");
        if (!srcFile.exists()) {
            return;
        }
        List<Shop> screenShops = ((BaseExcelService) shopService).readFromExcel(srcFile, Shop.class);
        if (CollectionUtil.isEmpty(screenShops)) {
            return;
        }
        List<Shop> shops = shopService.queryAllShops();
        List<Shop> notExistsList = new ArrayList<Shop>();
        List<Shop> existsList = new ArrayList<Shop>();
        for (Shop screenShop : screenShops) {
            boolean isExists = false;
            for (Shop shop : shops) {
                if (shop.getName().equals(screenShop.getName())) {
                    isExists = true;
                    screenShop.setId(shop.getId());
                    break;
                }
            }
            if (isExists) {
                existsList.add(screenShop);
            } else {
                notExistsList.add(screenShop);
            }
        }
        File notExistsFile = new File(DIR + "认定店基础数据（新）.xlsx");
        File existsFile = new File(DIR + "认定店基础数据（旧）.xlsx");
        ((BaseExcelService) shopService).export2Excel(notExistsList, notExistsFile, Shop.class);
        ((BaseExcelService) shopService).export2Excel(existsList, existsFile, Shop.class);
    }

    private static void screenShopDetails() {
        File srcFile = new File(DIR + "认定店原始认定级别数据.xlsx");
        if (!srcFile.exists()) {
            return;
        }
        List<String[]> dataList = ExcelUtil.readExcel(srcFile, "Sheet1", 18, 2);
        List<Shop> shops = shopService.queryAllShops();
        List<ShopDetail> shopDetails = new ArrayList<ShopDetail>();
        for (String[] oneRow : dataList) {
            String shopId = "";
            String shopName = oneRow[0];
            String saleRegion = "";
            for (Shop shop : shops) {
                if (shop.getName().equals(shopName)) {
                    shopId = shop.getId();
                    saleRegion = shop.getSaleRegion();
                    break;
                }
            }
            for (int i = 1; i < 14; i++) {
                ShopDetail shopDetail = getShopDetail(oneRow, shopId, shopName, saleRegion, i);
                if (shopDetail == null) {
                    continue;
                }
                shopDetails.add(shopDetail);
            }
        }
        File targetFile = new File(DIR + "认定店认定级别数据.xlsx");
        ((BaseExcelService) shopService).export2Excel(shopDetails, targetFile, ShopDetail.class);
    }

    private static ShopDetail getShopDetail(String[] oneRow, String shopId, String shopName, String saleRegion, int i) {
        if (StringUtils.isEmpty(oneRow[i])) {
            return null;
        }
        ShopDetail shopDetail = new ShopDetail();
        shopDetail.setId(shopId);
        shopDetail.setName(shopName);
        shopDetail.setSaleRegion(saleRegion);
        if (i == 1 || i == 4 || i == 11) {
            shopDetail.setProductLine("CPP");
            if (i == 1) {
                shopDetail.setNumberOfYear("2013");
            } else if (i == 4) {
                shopDetail.setNumberOfYear("2014");
            } else {
                shopDetail.setNumberOfYear("2015");
            }
            shopDetail.setModel(oneRow[14]);
        } else if (i == 2 || i == 5 || i == 12) {
            shopDetail.setProductLine("WFP");
            if (i == 2) {
                shopDetail.setNumberOfYear("2013");
            } else if (i == 5) {
                shopDetail.setNumberOfYear("2014");
            } else {
                shopDetail.setNumberOfYear("2015");
            }
            shopDetail.setModel(oneRow[15]);
        } else if (i == 3 || i == 6 || i == 13) {
            shopDetail.setProductLine("IPF");
            if (i == 3) {
                shopDetail.setNumberOfYear("2013");
            } else if (i == 6) {
                shopDetail.setNumberOfYear("2014");
            } else {
                shopDetail.setNumberOfYear("2015");
            }
            shopDetail.setModel(oneRow[17]);
        } else if (i == 7) {
            shopDetail.setProductLine("PGA");
            shopDetail.setNumberOfYear("2015");
            shopDetail.setModel("");
        } else if (i == 8) {
            shopDetail.setProductLine("DP");
            shopDetail.setNumberOfYear("2015");
            shopDetail.setModel("");
        } else if (i == 9) {
            shopDetail.setProductLine("TDS");
            shopDetail.setNumberOfYear("2015");
            shopDetail.setModel("");
        } else if (i == 10) {
            shopDetail.setProductLine("DGS");
            shopDetail.setNumberOfYear("2015");
            shopDetail.setModel(oneRow[16]);
        }
        shopDetail.setLevel(oneRow[i]);
        return shopDetail;
    }
}
