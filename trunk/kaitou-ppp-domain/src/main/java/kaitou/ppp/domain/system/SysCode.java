package kaitou.ppp.domain.system;

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

    /**
     * 单元格数据类型
     */
    public static enum CellType {
        STRING(1), DATE(2), NUMERIC(3);

        private int value;

        CellType(int value) {
            this.value = value;
        }

        public int getValue() {
            return value;
        }
    }

    public static final int CELL_TYPE_STRING = 1;
    public static final int CELL_TYPE_DATE = 2;
    public static final int CELL_TYPE_NUMERIC = 3;

    /**
     * 机型代码
     */
    public enum Code {
        P("WFP", "TDS", "M"), T("WFP", "TDS", "M"), F("WFP", "TDS", "M"), A("WFP", "DGS", "M"), V("CPP", "DP", ""), C("WFP", "TDS", ""), I("CPP", "PGA", "");
        private String cardNoPref;
        private String models;
        private String readUnit;

        Code(String cardNoPref, String models, String readUnit) {
            this.cardNoPref = cardNoPref;
            this.models = models;
            this.readUnit = readUnit;
        }

        public String getCardNoPref() {
            return cardNoPref;
        }

        public String getModels() {
            return models;
        }

        public String getReadUnit() {
            return readUnit;
        }

        public static Code getCode(String value) {
            for (Code code : Code.values()) {
                if (code.name().equals(value)) {
                    return code;
                }
            }
            return P;
        }
    }
}
