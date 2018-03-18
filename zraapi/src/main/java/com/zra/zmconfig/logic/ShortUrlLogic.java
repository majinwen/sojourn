package com.zra.zmconfig.logic;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.zra.common.utils.ShortUrlUtils;
import com.zra.zmconfig.entity.CfShortUrl;
import com.zra.zmconfig.service.ShortUrlService;

@Component
public class ShortUrlLogic {
	
	@Autowired
	private ShortUrlService shortUrlService;	
	
	
	/**
	 * 生成短链接后缀
	 * @param longUrl
	 * @return
	 */
	public CfShortUrl genShortUrlSuff(String longUrl){
		String suid = ShortUrlUtils.shortUrl(longUrl);
		CfShortUrl cfShortUrl = new CfShortUrl(suid, longUrl, new Date());
		boolean flag = shortUrlService.saveCfShortUrl(cfShortUrl);
		if(flag){
			return cfShortUrl;
		}else{
			return null;
		}
		
	}
	
	/**
	 * @return
	 */
	public CfShortUrl getLongUrl(String shortUrlSuff){
		CfShortUrl cfShortUrl = shortUrlService.getCfShortUrl(shortUrlSuff);
		return cfShortUrl;
	}
}

