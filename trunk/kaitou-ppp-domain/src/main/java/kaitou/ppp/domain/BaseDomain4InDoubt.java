package kaitou.ppp.domain;

import org.apache.commons.lang.math.RandomUtils;
import org.joda.time.DateTime;

/**
 * 允许数据存疑的domain父类.
 * User: 赵立伟
 * Date: 2015/4/17
 * Time: 9:13
 */
public abstract class BaseDomain4InDoubt extends BaseDomain {

    private static final String NO_DOUBT = "false";
    private static final String IN_DOUBT = "true";
    /**
     * 流水号
     */
    protected long serialNo = -1;
    /**
     * 存疑。默认为false，即不存疑
     */
    protected String inDoubt = NO_DOUBT;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        BaseDomain4InDoubt that = (BaseDomain4InDoubt) o;

        if (serialNo != that.serialNo) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return (int) (serialNo ^ (serialNo >>> 32));
    }

    /**
     * 生成流水号
     * <p>时间戳+随机数</p>
     */
    public void generateSerialNo() {
        serialNo = new DateTime().getMillis() + RandomUtils.nextInt(1000);
    }

    public long getSerialNo() {
        return serialNo;
    }

    public void setSerialNo(long serialNo) {
        this.serialNo = serialNo;
    }

    public String getInDoubt() {
        return inDoubt;
    }

    /**
     * 是否存疑
     *
     * @return 是为真
     */
    public boolean isInDoubt() {
        return IN_DOUBT.equals(inDoubt);
    }

    public void setInDoubt(String inDoubt) {
        this.inDoubt = inDoubt;
    }

    /**
     * 确实存疑
     */
    public void doInDoubt() {
        inDoubt = IN_DOUBT;
    }

    /**
     * 不存疑
     */
    public void noDoubt() {
        inDoubt = NO_DOUBT;
    }
}
