package kaitou.ppp.domain;

/**
 * 系统变量.
 * User: 赵立伟
 * Date: 2015/1/17
 * Time: 10:45
 */
public class SysCode {
    /**
     * 认定点等级
     */
    public static enum CompanyLevel {
        GOLD("金牌"), SILVER("银牌"), NORMAL("普通"), BACKUP("候补");
        private String value;

        CompanyLevel(String value) {
            this.value = value;
        }

        public String getValue() {
            return value;
        }
    }

    /**
     * 工程师状态
     */
    public static enum EngineerStatus {
        ON("在职"), OFF("离职");
        private String value;

        EngineerStatus(String value) {
            this.value = value;
        }

        public String getValue() {
            return value;
        }
    }

    /**
     * 销售区域
     */
    public static enum SaleRegion {
        NORTH_CHINA("华北"), WEST_CHINA("华西");
        private String value;

        SaleRegion(String value) {
            this.value = value;
        }

        public String getValue() {
            return value;
        }
    }

    /**
     * ACE等级
     */
    public static enum ACELevel {
        FIRST("一级"), SECOND("二级"), THIRD("三级");
        private String value;

        ACELevel(String value) {
            this.value = value;
        }

        public String getValue() {
            return value;
        }
    }
}
