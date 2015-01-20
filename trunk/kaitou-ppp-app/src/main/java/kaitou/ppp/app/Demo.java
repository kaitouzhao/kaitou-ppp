package kaitou.ppp.app;

import kaitou.ppp.service.EngineerService;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.io.File;

/**
 * 测试demo.
 * User: 赵立伟
 * Date: 2015/1/17
 * Time: 23:29
 */
public class Demo {

    private static final String EXPORT_XLSX = "D:\\temp\\ppp\\export.xlsx";
    private static ApplicationContext ctx;
    private static EngineerService engineerService;
    private static final String IMPORT_FILE_PATH = System.getProperty("user.dir") + File.separatorChar + "test.xlsx";

    public static void main(String[] args) {
        ctx = new ClassPathXmlApplicationContext(
                new String[]{
                        "applicationContext-service.xml"
                }
        );
        engineerService = ctx.getBean(EngineerService.class);
        importEngineers();
        exportEngineers();
    }

    private static void importEngineers() {
        File srcFile = new File(IMPORT_FILE_PATH);
        engineerService.importEngineers(srcFile);
        engineerService.importEngineerTrainings(srcFile);
    }

    private static void exportEngineers() {
        File targetFile = new File(EXPORT_XLSX);
        engineerService.exportEngineers(targetFile);
    }
}
