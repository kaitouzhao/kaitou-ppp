package kaitou.ppp.domain.shop;

import java.util.ArrayList;
import java.util.List;

/**
 * 缓存的认定店信息.
 * User: 赵立伟
 * Date: 2015/1/28
 * Time: 13:04
 */
public class CachedShop {
    /**
     * 销售区域
     *
     * @see kaitou.ppp.domain.system.SysCode.SaleRegion
     */
    private String saleRegion;
    /**
     * 认定店编码
     */
    private String id;
    /**
     * 认定店名
     */
    private String name;
    /**
     * 认定店发展信息列表
     */
    private List<CachedShopDetail> details = new ArrayList<CachedShopDetail>();

    /**
     * 根据产品线获取发展信息
     *
     * @param productLine 产品线
     * @return 发展信息。如果找不到，返回一个新对象
     */
    public CachedShopDetail getDetail(String productLine) {
        for (CachedShopDetail detail : details) {
            if (detail.getProductLine().equals(productLine)) {
                return detail;
            }
        }
        return new CachedShopDetail();
    }

    /**
     * 更新发展信息
     *
     * @param detail 待更新的信息
     * @return 认定店信息
     */
    public CachedShop updateDetail(CachedShopDetail detail) {
        int index = details.indexOf(detail);
        if (index >= 0) {
            CachedShopDetail shopDetail = details.get(index);
            if (!shopDetail.isBefore(detail)) {
                return this;
            }
            details.remove(index);
        }
        return addDetail(detail);
    }

    /**
     * 增加发展信息
     *
     * @param detail 发展信息
     * @return 认定店信息
     */
    public CachedShop addDetail(CachedShopDetail detail) {
        details.add(detail);
        return this;
    }

    @Override
    public String toString() {
        return "CachedShop{" +
                "saleRegion='" + saleRegion + '\'' +
                ", id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", details=" + details +
                '}';
    }

    public String getSaleRegion() {
        return saleRegion;
    }

    public void setSaleRegion(String saleRegion) {
        this.saleRegion = saleRegion;
    }

    public String getId() {
        return id;
    }

    public CachedShop setId(String id) {
        this.id = id;
        return this;
    }

    public String getName() {
        return name;
    }

    public CachedShop setName(String name) {
        this.name = name;
        return this;
    }

    public List<CachedShopDetail> getDetails() {
        return details;
    }

    public CachedShop setDetails(List<CachedShopDetail> details) {
        this.details = details;
        return this;
    }
}
