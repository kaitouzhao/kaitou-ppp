package kaitou.ppp.domain.shop;

import kaitou.ppp.domain.BaseDomain;
import org.apache.commons.lang.StringUtils;

/**
 * 认定店RTS.
 * User: 赵立伟
 * Date: 2015/1/29
 * Time: 21:03
 */
public class ShopRTS extends BaseDomain {
    /**
     * 认定店编码
     */
    protected String id;
    /**
     * 认定店名
     */
    protected String name;
    /**
     * 产品线
     */
    protected String productLine;
    /**
     * RTS
     */
    protected String rts;

    @Override
    public void check() {
        if (StringUtils.isEmpty(id)) {
            throw new RuntimeException("认定店编码为空");
        }
        if (StringUtils.isEmpty(productLine)) {
            throw new RuntimeException("产品线为空");
        }
    }

    @Override
    public String dbFileName() {
        return dbFileSuffix();
    }

    @Override
    public String dbFileSuffix() {
        return getClass().getSimpleName() + DB_SUFFIX;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ShopRTS shopRTS = (ShopRTS) o;

        if (id != null ? !id.equals(shopRTS.id) : shopRTS.id != null) return false;
        return !(productLine != null ? !productLine.equals(shopRTS.productLine) : shopRTS.productLine != null);

    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (productLine != null ? productLine.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "ShopRTS{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", productLine='" + productLine + '\'' +
                ", rts='" + rts + '\'' +
                '}';
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getProductLine() {
        return productLine;
    }

    public void setProductLine(String productLine) {
        this.productLine = productLine;
    }

    public String getRts() {
        return rts;
    }

    public void setRts(String rts) {
        this.rts = rts;
    }
}
