package com.ziroom.minsu.spider.commons;

public enum CharEncoding {
	UTF8("UTF-8"), GBK("GBK"), GB2312("GB2312");
	String _charset = "UTF-8";

	private CharEncoding(String charset) {
		_charset = charset;
	}

	public String value() {
		return _charset;
	}
}
