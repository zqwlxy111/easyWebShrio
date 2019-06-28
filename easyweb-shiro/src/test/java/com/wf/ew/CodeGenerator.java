package com.wf.ew;

import com.baomidou.mybatisplus.generator.AutoGenerator;
import com.baomidou.mybatisplus.generator.config.*;
import com.baomidou.mybatisplus.generator.config.rules.NamingStrategy;
import com.baomidou.mybatisplus.generator.engine.BeetlTemplateEngine;

/**
 * MyBatis-plus 代码生成器
 * Created by wangfan on 2019-02-11 上午 9:50.
 */
public class CodeGenerator {

    public static void main(String[] args) {
        String packageName = "com.wf.ew.system";
        String[] tableNames = {"sys_dictionary"};
        boolean serviceNameStartWithI = false; // user -> UserService, 设置成true: user -> IUserService
        generateByTables(serviceNameStartWithI, packageName, tableNames);
    }

    public static void generateByTables(boolean serviceNameStartWithI, String packageName, String... tableNames) {
        AutoGenerator mpg = new AutoGenerator();  // 代码生成器

        // 全局配置
        GlobalConfig gc = new GlobalConfig();
        gc.setOutputDir("d:\\codeGen");  // 输出位置
        gc.setFileOverride(true);  // 覆盖文件
        gc.setAuthor("wangfan");
        gc.setOpen(true);
        gc.setActiveRecord(false);  // ActiveRecord 模式
        gc.setEnableCache(false);  // 二级缓存
        if (!serviceNameStartWithI) {
            gc.setServiceName("%sService");  // service命名方式
        }
        mpg.setGlobalConfig(gc);

        // 数据源配置
        DataSourceConfig dsc = new DataSourceConfig();
        dsc.setUrl("jdbc:mysql://localhost:3306/easyweb-shiro");
        dsc.setDriverName("com.mysql.jdbc.Driver");
        dsc.setUsername("root");
        dsc.setPassword("123456");
        mpg.setDataSource(dsc);

        // 包配置
        PackageConfig pc = new PackageConfig();
        pc.setParent(packageName);
        pc.setMapper("dao");
        pc.setXml("xml");
        pc.setController("controller");
        pc.setEntity("model");
        mpg.setPackageInfo(pc);


        // 策略配置
        StrategyConfig strategy = new StrategyConfig();
        strategy.setCapitalMode(true);  // 大写命名
        strategy.setEntityLombokModel(false); // lombok模型
        strategy.setColumnNaming(NamingStrategy.underline_to_camel); // 表名映射策略
        strategy.setNaming(NamingStrategy.underline_to_camel);  // 字段映射策略
        strategy.setTablePrefix("sys_", "tb_");  // 表前缀
        strategy.setInclude(tableNames);
        mpg.setStrategy(strategy);

        // 模板引擎
        mpg.setTemplateEngine(new BeetlTemplateEngine());

        mpg.execute();
    }
}

