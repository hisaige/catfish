package com.catfish.ums;

import com.baomidou.mybatisplus.generator.config.DataSourceConfig;
import com.hisaige.dbcore.support.plugin.CodeGenerator;
import org.junit.Test;

/**
 * @author chenyj
 * @version 1.0
 * @date 2021/1/25$ - 21:53$
 */
public class UmsCommonTest {

    private DataSourceConfig getDataSourceConfig(){

        DataSourceConfig dsc = new DataSourceConfig();
        dsc.setUrl("jdbc:mysql://127.0.0.1:3336/catfish_cloud?useUnicode=true&characterEncoding=UTF-8&rewriteBatchedStatements=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC");
        // dsc.setSchemaName("public");
//        dsc.setDriverName("com.mysql.jdbc.Driver");
        dsc.setDriverName("com.mysql.cj.jdbc.Driver");
        dsc.setUsername("root");
        dsc.setPassword("cyj515818");
        return dsc;
    }

    @Test
    public void test1() {
        CodeGenerator.generator(getDataSourceConfig(), "catfish-ums", "com.catfish", "ums", "chenyj", "ums_", "ums_organization");
    }
}
