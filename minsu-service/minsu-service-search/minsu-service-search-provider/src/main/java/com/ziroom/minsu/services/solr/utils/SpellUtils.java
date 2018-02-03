/**
 * @FileName: SpellUtils.java
 * @Package com.ziroom.minsu.services.solr.utils
 * 
 * @author zhangshaobin
 * @created 2016年4月13日 上午4:27:35
 * 
 * Copyright 2015-2025 ziroom
 */
package com.ziroom.minsu.services.solr.utils;

import java.util.ArrayList;
import java.util.List;
import java.util.Vector;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.regex.Pattern;
import com.asura.framework.base.exception.BusinessException;
import com.asura.framework.base.util.Check;
import com.ziroom.minsu.services.common.utils.PinYinUtil;
import com.ziroom.minsu.services.common.utils.ValueUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.asura.framework.utils.LogUtil;

/**
 * <p>
 * 拼写工具类，主要包括：拼音转汉字、汉字转拼音
 * </p>
 * 
 * <PRE>
 * <BR>	修改记录
 * <BR>-----------------------------------------------
 * <BR>	修改日期			修改人			修改内容
 * </PRE>
 * 
 * @author zhangshaobin
 * @since 1.0
 * @version 1.0
 */
public class SpellUtils {

	/**
	 * 日志文件
	 */
	private static final Logger logger = LoggerFactory
			.getLogger(SpellUtils.class);

	/**
	 * 拼音汉字存储,按照26个字母归类。 线程安全【用户端：房东 区域 景点 商圈 地铁 楼盘】
	 */
	private static ConcurrentMap<String, ConcurrentMap<String, Vector<String>>> spell26ConcurrentMap;

	/**
	 * 拼音汉字存储。 线程安全【用户端：房东 区域 景点 商圈 地铁 楼盘】
	 */
	private static ConcurrentMap<String, String> spellConcurrentMap;


    /**
     * 拼音汉字存储,按照26个字母归类。 线程安全 【房东端：只有楼盘信息】
     */
    private static ConcurrentMap<String, ConcurrentMap<String, Vector<String>>> spell26HouseOnlyConcurrentMap;

    /**
     * 拼音汉字存储。 线程安全【房东端：只有楼盘信息】
     */
    private static ConcurrentMap<String, String> spellHouseOnlyConcurrentMap;

	/**
	 * 初始化数据
	 */
	static {

        // put以26个字母为key的map进去
        // {f={}, g={}, d={}, e={}, b={}, c={}, a={}, n={}, o={}, l={}, m={},
        // j={}, k={}, h={}, i={}, w={}, v={}, u={}, t={}, s={}, r={}, q={},
        // p={}, z={}, y={}, x={}}

        //初始化用户的map
		spellConcurrentMap = new ConcurrentHashMap<>();
		spell26ConcurrentMap = new ConcurrentHashMap<String, ConcurrentMap<String, Vector<String>>>();
		for (char c = 'a'; c <= 'z'; c++) {
			spell26ConcurrentMap.put(String.valueOf(c),
					new ConcurrentHashMap<String, Vector<String>>());
		}
        //初始化只有房源的map
        spellHouseOnlyConcurrentMap = new ConcurrentHashMap<>();
        spell26HouseOnlyConcurrentMap = new ConcurrentHashMap<String, ConcurrentMap<String, Vector<String>>>();
        for (char c = 'a'; c <= 'z'; c++) {
            spell26HouseOnlyConcurrentMap.put(String.valueOf(c),
                    new ConcurrentHashMap<String, Vector<String>>());
        }
	}
	
	

	/**
	 * 
	 * 通过部分拼音查询对应的汉字，返回多个汉字
	 *
	 * @author zhangshaobin
	 * @created 2016年4月13日 上午5:20:54
	 *
	 * @param py 拼音
	 * @param num 返回汉字的个数
	 * @return
	 */
	public static List<String> spell2Words(String py, int num) {
		List<String> sugests = new ArrayList<String>();
		// spellMap里的可以是小写的，q要先转成小写
		py = py.toLowerCase();
		// 如果q是全部由英文组成
		if (Pattern.matches("^[a-z]+$", py)) {
			// 首字母
			String firstLetter = String.valueOf(py.charAt(0));
            ConcurrentMap<String, Vector<String>> tmp = spell26ConcurrentMap.get(firstLetter);
			for (String k : tmp.keySet()) {
				if (sugests.size() >= num) {
					break;
				}
				if (k.startsWith(py)) {
					sugests.addAll(tmp.get(k));
				}
			}
		} else {
			logger.debug("关键字[" + py + "]不是拼音");
		}
		return sugests;
	}


    /**
     * 将输入字转化成中文
     * @param org
     * @param num
     * @param split
     * @return
     */
    public static String trans2Words(String org,int num,String split){
        if(ValueUtil.checkZimuOnly(org)){
            org = ValueUtil.removeFristNum(org);
            StringBuffer sb = new StringBuffer();
            List<String> stringList = spell2Words(org, num);
            if(!Check.NuNCollection(stringList)){
                for (String ele: stringList){
                    sb.append(ele).append(ValueUtil.getStrValue(split));
                }
            }
            return sb.toString();
        }else {
            return org;
        }
    }

    /**
     * 只获取房源的词语
     * @param py
     * @param num
     * @return
     */
    public static List<String> spell2HouseOnlyWords(String py, int num) {
        List<String> sugests = new ArrayList<String>();
        // spellMap里的可以是小写的，q要先转成小写
        py = py.toLowerCase();
        // 如果q是全部由英文组成
        if (Pattern.matches("^[a-z]+$", py)) {
            // 首字母
            String firstLetter = String.valueOf(py.charAt(0));
            for (String k : spell26HouseOnlyConcurrentMap.get(firstLetter).keySet()) {
                if (sugests.size() >= num) {
                    break;
                }
                if (k.startsWith(py)) {
                    sugests.addAll(spell26HouseOnlyConcurrentMap.get(firstLetter).get(k));
                }
            }
        } else {
            logger.debug("关键字[" + py + "]不是拼音");
        }
        return sugests;
    }
	
	/**
	 * 
	 * 根据拼音查询一个汉字
	 *
	 * @author zhangshaobin
	 * @created 2016年4月13日 上午5:42:38
	 *
	 * @param py
	 * @return
	 */
	public static String getSepllOfWord(String py) {
		String hanzi = spellConcurrentMap.get(py);
		return hanzi == null ? "" : hanzi;
	}


    /**
     * 只添加到房源的map中
     * @param hanzi
     */
    public static void addHouseOnlySpell(String hanzi) {

        hanzi = ValueUtil.removeFristNum(hanzi);

        String py_multi = PinYinUtil.getPinYinMulti(hanzi);
        String[] pys = py_multi.split(",");
        try {
            for (String py : pys) {
                if(Check.NuNStr(py)){
                    continue;
                }
                if (!spellHouseOnlyConcurrentMap.containsKey(py)) { // 如果该拼音不在map中,
                    // 将拼音汉字一并放入拼音-汉字存储中
                    spellHouseOnlyConcurrentMap.put(py, hanzi);
                    // 获取首字母
                    String firstLetter = String.valueOf(py.charAt(0)).toLowerCase();
                    // 以firstLetter开头的spellMap的一个子集
                    ConcurrentMap<String, Vector<String>> smallSpellMap = spell26HouseOnlyConcurrentMap
                            .get(firstLetter);
                    if (smallSpellMap.containsKey(py)) {
                        Vector<String> v = smallSpellMap.get(py);
                        v.add(hanzi);
                        smallSpellMap.put(py, v);
                    } else {
                        Vector<String> v = new Vector<String>();
                        v.add(hanzi);
                        smallSpellMap.put(py, v);
                    }
                    spell26HouseOnlyConcurrentMap.put(firstLetter, smallSpellMap);
                }
            }
        }catch (Exception e){
            LogUtil.error(logger, "e:{}", e);
            throw new BusinessException(e);
        }


    }

	/**
	 * 
	 * 将汉字添加到spellConcurrentMap
	 *
	 * @author zhangshaobin
	 * @created 2016年4月13日 上午5:04:12
	 *
	 * @param hanzi
	 */
	public static void addSpell(String hanzi) {
        hanzi = ValueUtil.removeFristNum(hanzi);
        String py_multi = PinYinUtil.getPinYinMulti(hanzi);
		String[] pys = py_multi.split(",");
		for (String py : pys) {
            if(Check.NuNStr(py)){
                continue;
            }
			if (!spellConcurrentMap.containsKey(py)) { // 如果该拼音不在map中,
				// 将拼音汉字一并放入拼音-汉字存储中
				spellConcurrentMap.put(py, hanzi);
				// 获取首字母
				String firstLetter = String.valueOf(py.charAt(0)).toLowerCase();
				// 以firstLetter开头的spellMap的一个子集
				ConcurrentMap<String, Vector<String>> smallSpellMap = spell26ConcurrentMap
						.get(firstLetter);
                if (smallSpellMap.containsKey(py)) {
                    Vector<String> v = smallSpellMap.get(py);
                    v.add(hanzi);
                    smallSpellMap.put(py, v);
                } else {
                    Vector<String> v = new Vector<String>();
                    v.add(hanzi);
                    smallSpellMap.put(py, v);
                }
				spell26ConcurrentMap.put(firstLetter, smallSpellMap);
			}
		}

	}


    /**
     * 获取map的
     * @return
     */
    public static Integer getMapSize(){
        return spellConcurrentMap.size();
    }




	public static void main(String[] args) {
		 //{r={renmin=[人民], rencan=[人参], rencen=[人参], renshen=[人参], renren=[人人], renda=[人大], rendai=[人大]}, n={}, g={}, u={}, o={}, a={}, t={}, b={}, f={}, w={}, e={}, l={}, c={}, v={}, m={}, d={}, x={}, k={}, y={}, j={}, p={}, i={}, z={zhangshaobin=[张少斌]}, s={}, h={}, q={}}
		addSpell("红色");
        addSpell("朝阳门");
		addSpell("人民");
		addSpell("人大");
		addSpell("人人");
		addSpell("张少斌");
		System.out.println(spell2Words("r",2));
		System.out.println(getSepllOfWord("cym"));
	}

}
