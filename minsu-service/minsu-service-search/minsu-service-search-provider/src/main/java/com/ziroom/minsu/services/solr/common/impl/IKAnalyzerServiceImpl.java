/**
 * @Description:
 * @Author:chengxiang.huang
 * @Date:2016年3月23日 下午9:40:36
 * @Version: V1.0
 */
package com.ziroom.minsu.services.solr.common.impl;

import com.asura.framework.utils.LogUtil;
import com.ziroom.minsu.services.common.utils.StringUtils;
import com.ziroom.minsu.services.solr.common.IKAnalyzerService;

import org.apache.lucene.analysis.TokenStream;
import org.apache.lucene.analysis.tokenattributes.CharTermAttribute;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.wltea.analyzer.lucene.IKAnalyzer;

import java.io.IOException;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.List;

/**
 * 
 * @Description:
 * @Author:chengxiang.huang 2016年3月23日
 * @CreateTime: 2016年3月23日 下午9:40:36
 * @Version 1.0
 */
@Service(value = "search.ikAnalyzerService")
public class IKAnalyzerServiceImpl implements IKAnalyzerService {

	private final static Logger logger = LoggerFactory.getLogger(IKAnalyzerServiceImpl.class);


    /**
     * ik分词
     * @param txtOrg
     * @param useSmart
     * @return
     */
	@SuppressWarnings("resource")
	@Override
	public List<String> ikAnalysis(String txtOrg, Boolean useSmart) {
        //过滤文档中的特殊符号
        String txt = StringUtils.removeInvalidCharNoSpace(txtOrg);
		// 创建分词对象
		IKAnalyzer analyzer = new IKAnalyzer(useSmart);
		TokenStream ts = null;
		List<String> ikRsList = null;
		try {
			ts = analyzer.tokenStream("", new StringReader(txt));
			CharTermAttribute term = ts.addAttribute(CharTermAttribute.class);
			ts.reset();
			ikRsList = new ArrayList<String>();
			while (ts.incrementToken()) {
				ikRsList.add(term.toString());
			}
			ts.end();
		} catch (IOException e) {
			if (ts != null){
				try {
					ts.close();
				} catch (IOException e1) {
					e1.printStackTrace();
				}
			}
            LogUtil.error(logger,"使用IK分词产生异常,e={}",e);
			return null;
		} finally {
			if (ts != null){
				try {
					ts.close();
				} catch (IOException e) {
					LogUtil.error(logger,"使用IK分词产生异常,e={}",e);
				}
			}
		}
		return ikRsList;
	}
}
