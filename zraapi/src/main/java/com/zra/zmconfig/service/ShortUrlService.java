package com.zra.zmconfig.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.zra.zmconfig.dao.CfShortUrlMapper;
import com.zra.zmconfig.entity.CfShortUrl;

@Component
public class ShortUrlService {
	
	@Autowired
	private CfShortUrlMapper cfShortUrlMapper;
	
	public boolean saveCfShortUrl(CfShortUrl cfShortUrl){
		int size = cfShortUrlMapper.insert(cfShortUrl);
		return size > 0;
	}
	
	public CfShortUrl getCfShortUrl(String suffix){
		CfShortUrl cfShortUrl = cfShortUrlMapper.selectByPrimaryKey(suffix);
		return cfShortUrl;
	}
}

