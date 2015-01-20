package kaitou.ppp.manager.mock;

import kaitou.ppp.domain.Engineer;

/**
 * 工程师桩.
 * User: 赵立伟
 * Date: 2015/1/17
 * Time: 13:32
 */
public class MockEngineer extends Engineer {

    public MockEngineer(String id, String name, String saleRegion, String companyName, String companyLevel, String numberOfYear, String ACELevel, String dateOfEntry, String dateOfDeparture, String status) {
        this.id = id;
        this.name = name;
        this.saleRegion = saleRegion;
        this.companyName = companyName;
        this.companyLevel = companyLevel;
        this.numberOfYear = numberOfYear;
        this.aceLevel = ACELevel;
        this.dateOfEntry = dateOfEntry;
        this.dateOfDeparture = dateOfDeparture;
        this.status = status;
    }

    @Override
    public String dbFileName() {
        StringBuilder dbFileName = new StringBuilder();
        dbFileName.append(saleRegion).append('_').append(companyName).append('_').append("Engineer.kdb");
        return dbFileName.toString();
    }
}
