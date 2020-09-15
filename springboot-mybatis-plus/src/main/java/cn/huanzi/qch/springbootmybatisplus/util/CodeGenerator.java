package cn.huanzi.qch.springbootmybatisplus.util;

import com.baomidou.mybatisplus.annotation.DbType;
import com.baomidou.mybatisplus.core.toolkit.StringPool;
import com.baomidou.mybatisplus.generator.AutoGenerator;
import com.baomidou.mybatisplus.generator.InjectionConfig;
import com.baomidou.mybatisplus.generator.config.*;
import com.baomidou.mybatisplus.generator.config.builder.ConfigBuilder;
import com.baomidou.mybatisplus.generator.config.po.TableField;
import com.baomidou.mybatisplus.generator.config.po.TableInfo;
import com.baomidou.mybatisplus.generator.config.rules.DbColumnType;
import com.baomidou.mybatisplus.generator.config.rules.NamingStrategy;

import java.util.*;

/**
 * 自动生成pojo，mapper, service, controller
 * 工具封装部分代码参考：https://blog.csdn.net/qq_38796327/article/details/99736134
 */
public class CodeGenerator {

	/**
	 * 数据库连接url
	 */
	private static final String DB_URL = "jdbc:mysql://localhost:3306/test?serverTimezone=GMT%2B8&characterEncoding=utf-8";

	/**
	 * 用户名
	 */
	private static final String USERNAME = "root";

	/**
	 * 密码
	 */
	private static final String PASSWORD = "123456";

	/**
	 * 数据库驱动
	 */
	private static final String DRIVER_NAME = "com.mysql.cj.jdbc.Driver";

	/**
	 * 数据库类型
	 */
	private static final DbType DB_TYPE = DbType.MYSQL;

	/**
	 * 命名空间，不指定就默认 public
	 */
	private static final String NAME_SPACE = "public";

	/**
	 * 请自定义自己的包名，后续的代码生成会在这个包下
	 */
	private static final String PACKAGE_NAME = "cn.huanzi.qch.springbootmybatisplus";

	/**
	 * 项目根路径
	 */
	private static final String BASE_PATH = System.getProperty("user.dir") + "\\springboot-mybatis-plus\\src\\main\\java\\";

	public static void main(String[] args) {
//		//注意，表名有些是区分大小写的
//		String[] tables = {"tb_user","tb_description"};
//		for (String table : tables) {
//			generateByTables(table);
//		}
//		System.out.println("代码已全部生成完毕！");
	}

	/**
	 * 通过表名生成相关类
	 */
	private static void generateByTables(String tableNames){

		// 全局配置
		GlobalConfig globalConfig = getGlobalConfig();

		// 数据源配置
		DataSourceConfig dataSourceConfig = getDataSourceConfig();

		// 包配置
		PackageConfig packageConfig = getPackageConfig(tableNames);

		// 策略配置
		StrategyConfig strategyConfig = getStrategyConfig(tableNames);

		// 模板配置
		TemplateConfig templateConfig = getTemplateConfig();

		// 自定义属性注入:abc
		// 在.ftl(或者是.vm)模板中，通过${cfg.abc}获取属性
        InjectionConfig injectionConfig = new InjectionConfig() {
            @Override
            public void initMap() {
                Map<String, Object> map = new HashMap<>();
                map.put("abc", camelCaseName(tableNames).toLowerCase());
                this.setMap(map);
            }
        };

		List<FileOutConfig> focList = new ArrayList<>();
		// 自定义配置会被优先输出
		focList.add(new FileOutConfig("/templates/entityVo.java.vm") {
			@Override
			public String outputFile(TableInfo tableInfo) {
				return BASE_PATH + PACKAGE_NAME.replaceAll("\\.","\\\\") + "\\" + tableInfo.getEntityPath().toLowerCase() +"\\entity\\"+ tableInfo.getEntityName() + "Vo" + StringPool.DOT_JAVA;
			}
		});
		injectionConfig.setFileOutConfigList(focList);

		new MyAutoGenerator()
				.setGlobalConfig(globalConfig)
				.setDataSource(dataSourceConfig)
				.setPackageInfo(packageConfig)
				.setStrategy(strategyConfig)
				.setTemplate(templateConfig)
                .setCfg(injectionConfig)
				.execute();

	}

	/**
	 * 指定模板，如果模版为空则不会生成，如果没有符合的，可在generateByTables方法，优先使用自定义模板
	 */
	private static TemplateConfig getTemplateConfig() {
		TemplateConfig templateConfig = new TemplateConfig();

		templateConfig.setEntity("templates/entity.java.vm") // entity模板采用自定义模板
				.setMapper("templates/mapper.java.vm")// mapper模板采用自定义模板
				.setXml(null) // 不生成xml文件
				.setService("templates/service.java.vm") // service模板采用自定义模板
				.setServiceImpl("templates/serviceImpl.java.vm") // serviceImpl模板采用自定义模板
				.setController("templates/controller.java.vm"); // controller模板采用自定义模板
		return templateConfig;
	}

	/**
	 * 定义策略
	 */
	private static StrategyConfig getStrategyConfig(String tableNames) {
		StrategyConfig strategyConfig = new StrategyConfig();
		strategyConfig
				.setCapitalMode(false)//驼峰命名
				.setEntityLombokModel(true)
				.setRestControllerStyle(true)
				.setNaming(NamingStrategy.underline_to_camel)
				.setColumnNaming(NamingStrategy.underline_to_camel)
				.setInclude(tableNames);
		return strategyConfig;
	}

	/**
	 * 配置生成包名
	 */
	private static PackageConfig getPackageConfig(String tableNames) {
		String tableNamez = camelCaseName(tableNames);
		PackageConfig packageConfig = new PackageConfig();
		packageConfig.setParent(PACKAGE_NAME + "." + tableNamez.toLowerCase())
				.setEntity("entity")
				.setMapper("mapper")
				.setServiceImpl("service")
				.setController("controller");
		return packageConfig;
	}

	/**
	 * 配置数据源
	 */
	private static DataSourceConfig getDataSourceConfig() {
		DataSourceConfig dataSourceConfig = new DataSourceConfig();
		dataSourceConfig.setDbType(DB_TYPE)
				.setDriverName(DRIVER_NAME)
				.setUsername(USERNAME)
				.setPassword(PASSWORD)
				.setSchemaName(NAME_SPACE)
				.setUrl(DB_URL);
		return dataSourceConfig;
	}

	/**
	 * 全局配置，配置生成文件的目录
	 */
	private static GlobalConfig getGlobalConfig() {
		GlobalConfig globalConfig = new GlobalConfig();
		globalConfig.setOpen(false)
				.setOutputDir(BASE_PATH)//生成文件的输出目录
				.setFileOverride(true)//是否覆盖已有文件
				.setBaseResultMap(true)
				.setBaseColumnList(true)
				.setActiveRecord(false)
				.setAuthor("huanzi-qch")
				.setServiceName("%sService");
		return globalConfig;
	}

	/**
	 * 下划线转换为驼峰
	 */
	private static String camelCaseName(String underscoreName) {
		StringBuilder result = new StringBuilder();
		if (underscoreName != null && underscoreName.length() > 0) {
			boolean flag = false;
			for (int i = 0; i < underscoreName.length(); i++) {
				char ch = underscoreName.charAt(i);
				if ("_".charAt(0) == ch) {
					flag = true;
				} else {
					if (flag) {
						result.append(Character.toUpperCase(ch));
						flag = false;
					} else {
						result.append(ch);
					}
				}
			}
		}
		return result.toString();
	}
}

/**
 * 自定义策略
 */
class MyAutoGenerator extends AutoGenerator {

	@Override
	protected List<TableInfo> getAllTableInfoList(ConfigBuilder config) {
		List<TableInfo> allTableInfoList = super.getAllTableInfoList(config);
		allTableInfoList.forEach(tableInfo -> {
			List<TableField> fields = tableInfo.getFields();
			Set<String> importPackages = tableInfo.getImportPackages();
			fields.forEach(field -> {
				// 如果存在LocalDateTime类型时，将其修改为Date类型
				if (field.getPropertyType().equals("LocalDateTime")) {
					field.setColumnType(DbColumnType.DATE);
					importPackages.remove("java.time.LocalDateTime");
					importPackages.add("java.util.Date");
				}
				if (field.getPropertyType().equals("LocalDate")) {
					field.setColumnType(DbColumnType.DATE);
					importPackages.remove("java.time.LocalDate");
					importPackages.add("java.util.Date");
				}
			});
		});
		return allTableInfoList;
	}
}