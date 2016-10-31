package com.shuogesha.cms.template;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;
import java.util.Set;

import org.apache.commons.io.FileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 模块生成器
 * 
 * <p>
 * 用于生成模块代码。
 * <p>
 * 包括JAVA类：action,dao,dao.impl,service,service.impl；
 * 配置文件：action配置,spring配置；ftl页面：v_list.html,v_add.html,v_edit.html。
 * 可设置的参数有：模块实体类名、java类包地址、配置文件地址
 */
public class ModuleGenerator {
	private static final Logger log = LoggerFactory
			.getLogger(ModuleGenerator.class);
	public static final String SPT = File.separator;

	public static final String ENCODING = "UTF-8";

	private Properties prop = new Properties();
	
	private String packName;
	private String fileName;
	private File beanFile;
	private File daoImplFile;
	private File daoFile;
	private File serviceFile;
	private File serviceImplFile;
	private File actionFile;
	private File pageListFile;
	private File pageEditFile;
	private File pageAddFile;

	private File beanTpl;
	private File daoImplTpl;
	private File daoTpl;
	private File serviceTpl;
	private File serviceImplTpl;
	private File actionTpl;
	private File pageListTpl;
	private File pageEditTpl;
	private File pageAddTpl;

	public ModuleGenerator(String packName, String fileName) {
		this.packName = packName;
		this.fileName = fileName;
	}

	@SuppressWarnings("unchecked")
	private void loadProperties() {
		try {
			log.debug("packName=" + packName);
			log.debug("fileName=" + fileName);
			FileInputStream fileInput = new FileInputStream(getFilePath(
					packName, fileName));
			prop.load(fileInput);
			String entityUp = prop.getProperty("Entity");
			log.debug("entityUp:" + entityUp);
			if (entityUp == null || entityUp.trim().equals("")) {
				log.warn("Entity not specified, exit!");
				return;
			}
			String entityLow = entityUp.substring(0, 1).toLowerCase()
					+ entityUp.substring(1);
			log.debug("entityLow:" + entityLow);
			prop.put("entity", entityLow);
			if (log.isDebugEnabled()) {
				Set ps = prop.keySet();
				for (Object o : ps) {
					log.debug(o + "=" + prop.get(o));
				}
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private void prepareFile() {
		String entityFilePath = getFilePath(prop.getProperty("entity_p"),
				prop.getProperty("Entity") + ".java");
		beanFile = new File(entityFilePath);
		log.debug("beanFile:" + beanFile.getAbsolutePath());
		
		String daoImplFilePath = getFilePath(prop.getProperty("dao_impl_p"),
				prop.getProperty("Entity") + "DaoImpl.java");
		daoImplFile = new File(daoImplFilePath);
		log.debug("daoImplFile:" + daoImplFile.getAbsolutePath());

		String daoFilePath = getFilePath(prop.getProperty("dao_p"), prop
				.getProperty("Entity")
				+ "Dao.java");
		daoFile = new File(daoFilePath);
		log.debug("daoFile:" + daoFile.getAbsolutePath());

		String serviceFilePath = getFilePath(prop.getProperty("service_p"),
				prop.getProperty("Entity") + "Service.java");
		serviceFile = new File(serviceFilePath);
		log.debug("serviceFile:" + serviceFile.getAbsolutePath());

		String serviceImplFilePath = getFilePath(prop
				.getProperty("service_impl_p"), prop.getProperty("Entity")
				+ "ServiceImpl.java");
		serviceImplFile = new File(serviceImplFilePath);
		log.debug("serviceImplFile:" + serviceImplFile.getAbsolutePath());
		String actionFilePath = getFilePath(prop.getProperty("action_p"), prop
				.getProperty("Entity")
				+ "Act.java");
		actionFile = new File(actionFilePath);
		log.debug("actionFile:" + actionFile.getAbsolutePath());

		String pagePath = "src/main/webapp/WEB-INF/"
				+ prop.getProperty("config_sys") + "/"
				+ prop.getProperty("config_entity") + "/";
		pageListFile = new File(pagePath + "v_list.jsp");
		log.debug("pageListFile:" + pageListFile.getAbsolutePath());
		pageEditFile = new File(pagePath + "v_edit.jsp");
		log.debug("pageEditFile:" + pageEditFile.getAbsolutePath());
		pageAddFile = new File(pagePath + "v_add.jsp");
		log.debug("pageAddFile:" + pageAddFile.getAbsolutePath());
	}

	private void prepareTemplate() {
		String tplPack = prop.getProperty("template_dir");
		log.debug("tplPack:" + tplPack);
		beanTpl = new File(getFilePath(tplPack, "entity.txt"));
		daoImplTpl = new File(getFilePath(tplPack, "dao_impl.txt"));
		daoTpl = new File(getFilePath(tplPack, "dao.txt"));
		serviceImplTpl = new File(getFilePath(tplPack, "service_impl.txt"));
		serviceTpl = new File(getFilePath(tplPack, "service.txt"));
		actionTpl = new File(getFilePath(tplPack, "action.txt"));
		pageListTpl = new File(getFilePath(tplPack, "page_list.txt"));
		pageAddTpl = new File(getFilePath(tplPack, "page_add.txt"));
		pageEditTpl = new File(getFilePath(tplPack, "page_edit.txt"));
	}

	private static void stringToFile(File file, String s) throws IOException {
		FileUtils.writeStringToFile(file, s, ENCODING);
	}

	private void writeFile() {
		try {
			stringToFile(beanFile, readTpl(beanTpl));
			
			if ("true".equals(prop.getProperty("is_dao"))) {
				stringToFile(daoImplFile, readTpl(daoImplTpl));
				stringToFile(daoFile, readTpl(daoTpl));
			}
			if ("true".equals(prop.getProperty("is_service"))) {
				stringToFile(serviceImplFile, readTpl(serviceImplTpl));
				stringToFile(serviceFile, readTpl(serviceTpl));
			}
			if ("true".equals(prop.getProperty("is_action"))) {
				stringToFile(actionFile, readTpl(actionTpl));
			}
			if ("true".equals(prop.getProperty("is_page"))) {
				stringToFile(pageListFile, readTpl(pageListTpl));
				stringToFile(pageAddFile, readTpl(pageAddTpl));
				stringToFile(pageEditFile, readTpl(pageEditTpl));
			}
		} catch (IOException e) {
			log.warn("write file faild! " + e.getMessage());
		}
	}

	private String readTpl(File tpl) {
		String content = null;
		try {
			content = FileUtils.readFileToString(tpl, ENCODING);
			Set<Object> ps = prop.keySet();
			for (Object o : ps) {
				String key = (String) o;
				String value = prop.getProperty(key);
				content = content.replaceAll("\\#\\{" + key + "\\}", value);
			}
		} catch (IOException e) {
			log.warn("read file faild. " + e.getMessage());
		}
		return content;

	}

	private String getFilePath(String packageName, String name) {
		log.debug("replace:" + packageName);
		String path = packageName.replaceAll("\\.", "/");
		log.debug("after relpace:" + path);
		return "src/main/java/" + path + "/" + name;
	}

	public void generate() {
		loadProperties();
		prepareFile();
		prepareTemplate();
		writeFile();
	}

	public static void main(String[] args) {
		String packName = "com.shuogesha.cms.template";
		String fileName = "shuogesha.properties";
		new ModuleGenerator(packName, fileName).generate();
	}
}
