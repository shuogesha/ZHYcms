package com.shuogesha.cms.template;


public class CmsModuleGenerator {
	private static String packName = "com.shuogesha.cms.template";
	private static String fileName = "shuogesha.properties";

	public static void main(String[] args) {
		new ModuleGenerator(packName, fileName).generate();
	}
}
