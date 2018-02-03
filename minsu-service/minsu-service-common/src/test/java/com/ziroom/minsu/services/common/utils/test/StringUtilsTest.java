package com.ziroom.minsu.services.common.utils.test;

public class StringUtilsTest {

	/**
	 * 将查询词中的非汉字，非数字，非字母过滤掉
	 * 
	 * @param keyword
	 * @return
	 */
	public static String removeInvalidChar(String keyword) {
		return keyword.replaceAll("[^\u4e00-\u9fa5a-zA-Z0-9]", " ").replaceAll("\\s+", " ").trim();
	}
	
	/**
	 * 将查询词中的非汉字，非数字，非字母过滤掉,并不保留空格
	 * 
	 * @param keyword
	 * @return
	 */
	public static String removeInvalidCharNoSpace(String keyword) {
		return keyword.replaceAll("[^\u4e00-\u9fa5a-zA-Z0-9]", "").trim();
	}
	
	/**
	 * 将查询词中的非汉字，非数字，非字母，非*过滤掉
	 * 
	 * @param keyword
	 * @return
	 */
	public static String removeInvalidChar1(String keyword) {
		return keyword.replaceAll("[^\u4e00-\u9fa5a-zA-Z0-9\\*]", " ").replaceAll("\\s+", " ").trim();
	}

	public static void main(String[] args) {
		String keyword = "(ff**)";
		System.out.println(removeInvalidChar1(keyword));
	}

}
