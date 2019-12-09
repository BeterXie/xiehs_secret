package com.secret.bussiness.mybatis;

import com.baomidou.mybatisplus.annotation.DbType;
import com.baomidou.mybatisplus.generator.AutoGenerator;
import com.baomidou.mybatisplus.generator.InjectionConfig;
import com.baomidou.mybatisplus.generator.config.*;
import com.baomidou.mybatisplus.generator.config.converts.MySqlTypeConvert;
import com.baomidou.mybatisplus.generator.config.po.TableFill;
import com.baomidou.mybatisplus.generator.config.po.TableInfo;
import com.baomidou.mybatisplus.generator.config.rules.DbColumnType;
import com.baomidou.mybatisplus.generator.config.rules.NamingStrategy;

import java.util.*;

public class MysqlGenerator {

	private static final String PACKAGE_NAME = "com.secret.bussiness";
	private static final String MODULE_NAME = "biz";
	private static final String OUT_PATH = "D:/jiahua_project/xiehs";
	private static final String JAVACLASS_PATH = "/src/main/java";
	private static final String MAPPER_PATH = "/src/main/resources/mapper/";
	private static final String AUTHOR = "xiehs";

	private static final String DRIVER = "com.mysql.jdbc.Driver";
	private static final String URL = "jdbc:mysql://101.37.77.34:3306/test?useUnicode=true&characterEncoding=UTF-8";
	private static final String USER_NAME = "root";
	private static final String PASSWORD = "XIEhs2019";

	/**
	 * <p>
	 * MySQL 生成演示
	 * </p>
	 */
	public static void main(String[] args) {
		// 自定义需要填充的字段
		List<TableFill> tableFillList = new ArrayList<TableFill>();
		// 全局配置
		GlobalConfig globalConfig = new GlobalConfig().setOutputDir(OUT_PATH + JAVACLASS_PATH)// 输出目录
				.setFileOverride(false)// 是否覆盖文件
				.setActiveRecord(true)// 开启 activeRecord 模式
				.setEnableCache(false)// XML 二级缓存
				.setBaseResultMap(false)// XML ResultMap
				.setBaseColumnList(true)// XML columList
				.setAuthor(AUTHOR)
				// 自定义文件命名，注意 %s 会自动填充表实体属性！
				// .setServiceName("MP%sService")
				// .setServiceImplName("%sServiceDiy")
				// .setControllerName("%sAction")
				.setXmlName("%sMapper").setMapperName("%sDao");
		// 代码生成器
		AutoGenerator mpg = new AutoGenerator().setGlobalConfig(globalConfig).setDataSource(
				// 数据源配置
				new DataSourceConfig().setDbType(DbType.MYSQL)// 数据库类型
						.setTypeConvert(new MySqlTypeConvert() {
							// 自定义数据库表字段类型转换【可选】
							public DbColumnType  processTypeConvert(GlobalConfig globalConfig,String fieldType) {
								System.out.println("转换类型：" + fieldType);
								// if ( fieldType.toLowerCase().contains( "tinyint" ) ) {
								// return DbColumnType.BOOLEAN;
								// }
								if(fieldType.toLowerCase().contains( "datetime" )){
									return DbColumnType.DATE;
								}
								return (DbColumnType) super.processTypeConvert(globalConfig,fieldType);
							}
						}).setDriverName(DRIVER).setUsername(USER_NAME).setPassword(PASSWORD).setUrl(URL))
				.setStrategy(
						// 策略配置
						new StrategyConfig()
								// .setCapitalMode(true)// 全局大写命名
								//.setDbColumnUnderline(true)// 全局下划线命名
								// .setTablePrefix(new String[]{"unionpay_"})// 此处可以修改为您的表前缀
								.setNaming(NamingStrategy.underline_to_camel)// 表名生成策略
								// .setInclude(new String[] {"citycode_org"}) // 需要生成的表
								// .setExclude(new String[]{"test"}) // 排除生成的表
								// 自定义实体，公共字段
								// .setSuperEntityColumns(new String[]{"test_id"})
								.setTableFillList(tableFillList)
								// 自定义实体父类
								// .setSuperEntityClass("com.baomidou.demo.base.BsBaseEntity")
								// // 自定义 mapper 父类
								// .setSuperMapperClass("com.baomidou.demo.base.BsBaseMapper")
								// // 自定义 service 父类
								// .setSuperServiceClass("com.baomidou.demo.base.BsBaseService")
								// // 自定义 service 实现类父类
								// .setSuperServiceImplClass("com.baomidou.demo.base.BsBaseServiceImpl")
								// 自定义 controller 父类
								.setSuperControllerClass("com.secret.bussiness.base.BaseController")
								// 【实体】是否生成字段常量（默认 false）
								// public static final String ID = "test_id";
								//.setEntityColumnConstant(true)
								// 【实体】是否为构建者模型（默认 false）
								// public User setName(String name) {this.name = name; return this;}
								//.setEntityBuilderModel(true)
								// 【实体】是否为lombok模型（默认 false）<a href="https://projectlombok.org/">document</a>
								//.setEntityLombokModel(true)
				// Boolean类型字段是否移除is前缀处理
				// .setEntityBooleanColumnRemoveIsPrefix(true)
				// .setRestControllerStyle(true)
				// .setControllerMappingHyphenStyle(true)
				).setPackageInfo(
						// 包配置
						new PackageConfig().setModuleName(MODULE_NAME).setParent(PACKAGE_NAME)// 自定义包路径
								.setController("controller")// 这里是控制器包名，默认 web
								.setXml("mapper").setMapper("dao")

				).setCfg(
						// 注入自定义配置，可以在 VM 中使用 cfg.abc 设置的值
						new InjectionConfig() {
							@Override
							public void initMap() {
								Map<String, Object> map = new HashMap<String, Object>();
								map.put("abc", this.getConfig().getGlobalConfig().getAuthor() + "-mp");
								this.setMap(map);
							}
						}.setFileOutConfigList(
								Collections.<FileOutConfig>singletonList(new FileOutConfig("/templates/mapper.xml.vm") {
									// 自定义输出文件目录
									@Override
									public String outputFile(TableInfo tableInfo) {
										return OUT_PATH + MAPPER_PATH + tableInfo.getEntityName() + "Mapper.xml";
									}
								})))
				.setTemplate(
						// 关闭默认 xml 生成，调整生成 至 根目录
						new TemplateConfig().setXml(null)
		// 自定义模板配置，模板可以参考源码 /mybatis-plus/src/main/resources/template 使用 copy
		// 至您项目 src/main/resources/template 目录下，模板名称也可自定义如下配置：
		// .setController("...");
		// .setEntity("...");
		// .setMapper("...");
		// .setXml("...");
		// .setService("...");
		// .setServiceImpl("...");
		);

		// 执行生成
		mpg.execute();
	}

}
