package kaitou.ppp.manager.impl;

import kaitou.ppp.domain.card.CardApplicationRecord;
import kaitou.ppp.manager.card.CardApplicationRecordManager;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;

/**
 * 保修卡生成记录DAO事务管理层实现单元测试.
 * User: 赵立伟
 * Date: 2015/4/15
 * Time: 16:20
 */
public class CardApplicationRecordManagerImplTest extends AbstractManagerTest {

    private CardApplicationRecordManager mockCardApplicationRecordManager;

    @Override
    public String getDbDir() {
        return "D:\\temp\\ppp\\test\\card_app_record";
    }

    @Override
    public void initManager() {
        mockCardApplicationRecordManager = ctx.getBean(CardApplicationRecordManager.class);
    }


    @Test
    public void test() {
        CardApplicationRecord record1 = new CardApplicationRecord();
        record1.setFuselage("001");
        record1.setWarrantyCard("001");
        record1.setModels("CPP");
        List<CardApplicationRecord> records = new ArrayList<CardApplicationRecord>();
        records.add(record1);
        mockCardApplicationRecordManager.save(records);
        List<CardApplicationRecord> queryData = mockCardApplicationRecordManager.query();
        assertEquals(1, queryData.size());
        assertEquals("false", queryData.get(0).getInDoubt());

        CardApplicationRecord record2 = new CardApplicationRecord();
        record2.setFuselage("001");
        record2.setWarrantyCard("");
        record2.setModels("CPP");
        records = new ArrayList<CardApplicationRecord>();
        records.add(record2);
        mockCardApplicationRecordManager.save(records);
        queryData = mockCardApplicationRecordManager.query();
        assertEquals(2, queryData.size());
        assertEquals("", queryData.get(1).getWarrantyCard());
        assertEquals("true", queryData.get(1).getInDoubt());

        CardApplicationRecord record3 = new CardApplicationRecord();
        record3.setFuselage("");
        record3.setWarrantyCard("001");
        record3.setModels("CPP");
        records = new ArrayList<CardApplicationRecord>();
        records.add(record3);
        mockCardApplicationRecordManager.save(records);
        queryData = mockCardApplicationRecordManager.query();
        assertEquals(3, queryData.size());
        assertEquals("", queryData.get(2).getFuselage());
        assertEquals("true", queryData.get(2).getInDoubt());
    }
}
