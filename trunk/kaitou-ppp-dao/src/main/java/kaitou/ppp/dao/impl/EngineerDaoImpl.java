package kaitou.ppp.dao.impl;

import com.womai.bsp.tool.utils.CollectionUtil;
import kaitou.ppp.dao.EngineerDao;
import kaitou.ppp.domain.Engineer;
import kaitou.ppp.domain.EngineerTraining;

import java.io.File;
import java.io.FilenameFilter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.womai.bsp.tool.utils.JsonUtil.json2Object;
import static com.womai.bsp.tool.utils.JsonUtil.object2Json;
import static kaitou.ppp.common.utils.FileUtil.*;

/**
 * 工程师DAO实现.
 * User: 赵立伟
 * Date: 2015/1/17
 * Time: 11:12
 */
public class EngineerDaoImpl implements EngineerDao<Engineer, String> {
    /**
     * 数据文件目录
     */
    private String dbDir;

    public void setDbDir(String dbDir) {
        this.dbDir = dbDir;
    }

    @Override
    public int save(Object... engineers) {
        if (CollectionUtil.isEmpty(engineers)) {
            return 0;
        }
        Map<String, List<Engineer>> engineerMap = new HashMap<String, List<Engineer>>();
        int size = engineers.length;
        int updateIndex = -1;
        for (int i = 0; i < size; i++) {
            Engineer engineer = (Engineer) engineers[i];
            engineer.check();
            String backDbFileName = engineer.backDbFileName();
            List<Engineer> engineerList = engineerMap.get(backDbFileName);
            if (engineerList == null) {
                engineerList = query(engineer.getCompanyName());
                engineerMap.put(backDbFileName, engineerList);
            }
            if (CollectionUtil.isEmpty(engineerList)) {
                engineerList.add(engineer);
                continue;
            }
            for (int j = 0; j < engineerList.size(); j++) {
                if (engineer.getId().equals(engineerList.get(j).getId())) {
                    updateIndex = j;
                }
            }
            if (updateIndex > -1) {
                engineerList.remove(updateIndex);
                engineerList.add(updateIndex, engineer);
                updateIndex = -1;
            } else {
                engineerList.add(engineer);
            }
        }
        int successCount = 0;
        for (Map.Entry<String, List<Engineer>> item : engineerMap.entrySet()) {
            StringBuilder dbFilePath = new StringBuilder(dbDir).append('/').append(item.getKey());
            List<Engineer> engineerList = item.getValue();
            List<String> eJsonList = new ArrayList<String>();
            for (Engineer engineer : engineerList) {
                eJsonList.add(object2Json(engineer));
            }
            successCount += writeLines(dbFilePath.toString(), eJsonList);
        }
        return successCount;
    }

    @Override
    public List<Engineer> query(final String companyName) {
        File file = new File(dbDir);
        File[] dbFiles = file.listFiles(new FilenameFilter() {
            @Override
            public boolean accept(File dir, String name) {
                return name.endsWith('_' + companyName + '_' + Engineer.class.getSimpleName() + ".kdb");
            }
        });
        List<Engineer> engineers = new ArrayList<Engineer>();
        if (!CollectionUtil.isEmpty(dbFiles)) {
            List<String> lines = readLines(dbFiles[0].getPath());
            if (!CollectionUtil.isEmpty(lines)) {
                for (String line : lines) {
                    engineers.add(json2Object(line, Engineer.class));
                }
            }
        }
        return engineers;
    }

    @Override
    public int updateTrainings(List<EngineerTraining> trainings) {
        if (CollectionUtil.isEmpty(trainings)) {
            return 0;
        }
        List<Engineer> engineers = new ArrayList<Engineer>();
        int updateIndex = -1;
        for (EngineerTraining training : trainings) {
            training.check();
            for (int i = 0; i < engineers.size(); i++) {
                if (training.getId().equals(engineers.get(i).getId())) {
                    updateIndex = i;
                    break;
                }
            }
            if (updateIndex > -1) {
                engineers.remove(updateIndex);
                engineers.add(updateIndex, training.build());
                updateIndex = -1;
                continue;
            }
            engineers.add(training.build());
        }
        return save(CollectionUtil.toArray(engineers, Engineer.class));
    }

    @Override
    public void commit() {
        File[] backFiles = getBackFile();
        if (CollectionUtil.isEmpty(backFiles)) {
            return;
        }
        for (int i = 0; i < backFiles.length; i++) {
            File backFile = backFiles[i];
            String backFileName = backFile.getName();
            String dbFileName = backFileName.substring(0, backFileName.indexOf('.')) + ".kdb";
            String backFilePath = backFile.getPath();
            copy(backFilePath, dbDir + '\\' + dbFileName);
            delete(backFilePath);
        }
    }

    /**
     * 获取备份文件集合
     *
     * @return 备份文件集合
     */
    private File[] getBackFile() {
        File dirFile = new File(dbDir);
        return dirFile.listFiles(new FilenameFilter() {
            @Override
            public boolean accept(File dir, String name) {
                return name.endsWith("_" + Engineer.class.getSimpleName() + ".kdb.back");
            }
        });
    }

    @Override
    public void rollback() {
        File[] backFiles = getBackFile();
        if (CollectionUtil.isEmpty(backFiles)) {
            return;
        }
        for (int i = 0; i < backFiles.length; i++) {
            delete(backFiles[i].getPath());
        }
    }
}
