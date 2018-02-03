package com.ziroom.minsu.services.search.service;

import base.BaseTest;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.asura.framework.base.util.Check;
import com.asura.framework.base.util.JsonEntityTransform;
import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.HashMultiset;
import com.google.common.collect.Multimap;
import com.google.common.collect.Multiset;
import com.ziroom.minsu.services.search.dto.CmsArticleDetailRequest;
import com.ziroom.minsu.services.search.dto.CmsArticleRequest;
import com.ziroom.minsu.services.search.entity.CmsArticleDetailEntity;
import com.ziroom.minsu.services.search.entity.QueryResult;
import com.ziroom.minsu.services.search.vo.CmsArticleInfoVo;
import org.junit.Test;

import javax.annotation.Resource;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * <p></p>
 * <p>
 * <PRE>
 * <BR>    修改记录
 * <BR>-----------------------------------------------
 * <BR>    修改日期         修改人          修改内容
 * </PRE>
 *
 * @author zl
 * @version 1.0
 * @Date Created in 2017年07月31日 11:57
 * @since 1.0
 */
public class CmsServiceImplTest extends BaseTest {

    @Resource(name = "search.freshCmsIndexServiceImpl")
    private FreshCmsIndexServiceImpl freshCmsIndexServiceImpl;

    @Resource(name = "search.searchCmsServiceImpl")
    private SearchCmsServiceImpl searchCmsServiceImpl;


    @Test
    public void dealSearchIndex() {

        List<CmsArticleInfoVo> articleVoList = new ArrayList<>();

        for (int i = 10; i < 100; i++) {
            CmsArticleInfoVo vo = new CmsArticleInfoVo();
            vo.setTitle("titles" + i);
            vo.setId(String.valueOf(i));
            articleVoList.add(vo);
        }
        Set<String> existIdes = new HashSet<>();
        freshCmsIndexServiceImpl.dealBatchIndex(articleVoList, existIdes);
    }


    @Test
    public void getCmsArticleList() {
        List<CmsArticleInfoVo> list = freshCmsIndexServiceImpl.getCmsArticleList(1, 2);
        System.out.println(JsonEntityTransform.Object2Json(list));

    }

    @Test
    public void freshIndexAll() {
        freshCmsIndexServiceImpl.freshIndexAll();
    }

    @Test
    public void searchArticleList() {
        CmsArticleRequest paramsRequest = new CmsArticleRequest();
        paramsRequest.setPage(1);
        paramsRequest.setLimit(10);
        QueryResult result = searchCmsServiceImpl.searchArticleList(paramsRequest);

        System.out.println(JsonEntityTransform.Object2Json(result));

    }

    @Test
    public void getArticleDetail() {

        CmsArticleDetailRequest paramsRequest = new CmsArticleDetailRequest();
        paramsRequest.setId("53");

        CmsArticleDetailEntity result = searchCmsServiceImpl.getArticleDetail(paramsRequest);
        System.out.println(JsonEntityTransform.Object2Json(result));

    }


    /**
     * 跑攻略浏览量
     * 10.16.45.22,10.16.45.26
     * 改solr.properties
     * solr.cms.url=http://search.ziroom.com
     * grep 查询攻略详情参数 all_data-2017090*
     */
    @Test
    public void getArticleAccessCount() {

        Multimap<String, Object> map = ArrayListMultimap.create();

        Multiset<String> idSet = HashMultiset.create();

        String s;
        try {
            FileInputStream fis = new FileInputStream(new File("F:\\gonglue.log"));
            BufferedReader br = new BufferedReader(new InputStreamReader(fis));

            while ((s = br.readLine()) != null) {
                if (s.indexOf("=") > 0) {
                    s = s.substring(s.indexOf("=") + 1);
                    JSONObject json = JSON.parseObject(s);

                    String id = json.getString("id");
                    idSet.add(id);
                }
            }
            System.out.println("-----------idSet:" + idSet);
            System.out.println("-----------idSet:" + idSet.elementSet().size());
        } catch (Exception e) {
            e.printStackTrace();
        }


        for (String id : idSet.elementSet()) {

            CmsArticleDetailRequest paramsRequest = new CmsArticleDetailRequest();
            paramsRequest.setId(id);

            CmsArticleDetailEntity result = searchCmsServiceImpl.getArticleDetail(paramsRequest);

            map.put(id, Check.NuNObj(result) ? "————" : result.getTitle());
            map.put(id, Check.NuNObj(result) ? "————" : result.getCityName());
            map.put(id, Check.NuNObj(result) ? "————" : result.getSubCategorys());
            map.put(id, idSet.count(id));

        }

        String fileName = "F:\\gonglue.txt";

        try {
            FileWriter writer = new FileWriter(fileName);

            String space = " ";

            writer.write("id");
            writer.write(space);
            writer.write("title");
            writer.write(space);
            writer.write("cityName");
            writer.write(space);
            writer.write("subCategorys");
            writer.write(space);
            writer.write("浏览量");
            writer.write(System.getProperty("line.separator"));
            writer.write(System.getProperty("line.separator"));

            for (String id : map.keySet()) {

                writer.write(id);
                for (Object o : map.get(id)) {
                    writer.write(System.getProperty("line.separator"));
                    writer.write(o.toString());
                }
                writer.write(System.getProperty("line.separator"));
            }
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }


        System.out.println(map);

    }


}
