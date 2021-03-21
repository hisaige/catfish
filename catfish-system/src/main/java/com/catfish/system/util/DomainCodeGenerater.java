package com.catfish.system.util;

import com.baomidou.mybatisplus.generator.config.DataSourceConfig;
import com.hisaige.dbcore.support.plugin.CodeGenerator;

/**
 * 代码生成工具
 * @author chenyj
 * @version 1.0
 * @date 2020/12/29$ - 23:42$
 */
public class DomainCodeGenerater {
    public static void main(String[] args) {
// 数据源配置
        DataSourceConfig dsc = new DataSourceConfig();
        dsc.setUrl("jdbc:mysql://localhost:3336/catfish_cloud?useUnicode=true&characterEncoding=UTF-8&rewriteBatchedStatements=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC");
        // dsc.setSchemaName("public");
//        dsc.setDriverName("com.mysql.jdbc.Driver");
        dsc.setDriverName("com.mysql.cj.jdbc.Driver");
        dsc.setUsername("root");
        dsc.setPassword("root");

        CodeGenerator.generator(dsc, "catfish-gateway", "com.catfish", "ums", "hisaige", "", "ums_organization");
    }
}
