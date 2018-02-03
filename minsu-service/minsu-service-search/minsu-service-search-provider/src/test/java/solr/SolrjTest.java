package solr; /**
 * @FileName: SolrjTest.java
 * @Package
 * @author lusp
 * @created 2017年8月15日 下午4:55:41
 * <p>
 * Copyright 2011-2015 asura
 */


import base.BaseTest;
import com.alibaba.dubbo.common.utils.StringUtils;
import com.asura.framework.base.paging.PagingResult;
import com.ziroom.minsu.services.common.utils.ValueUtil;
import com.ziroom.minsu.services.customer.dao.CustomerDbDao;
import com.ziroom.minsu.services.house.dao.HouseDbInfoDao;
import com.ziroom.minsu.services.search.dto.CreatIndexRequest;
import com.ziroom.minsu.services.search.vo.HouseDbInfoVo;
import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.impl.HttpSolrClient;
import org.apache.solr.client.solrj.impl.XMLResponseParser;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.client.solrj.response.UpdateResponse;
import org.junit.Before;
import org.junit.Test;

import javax.annotation.Resource;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;


/**
 * <p>solrj测试类</p>
 *
 * <PRE>
 * <BR>	修改记录
 * <BR>-----------------------------------------------
 * <BR>	修改日期			修改人			修改内容
 * </PRE>
 *
 * @author lusp
 * @since 1.0
 * @version 1.0
 */
public class SolrjTest extends BaseTest{

  private HttpSolrClient httpSolrClient;

  @Before
  public void setUp() throws Exception {
    String url = "http://localhost:8080/solr/customerDbInfo/";
    HttpSolrClient httpSolrClient = new HttpSolrClient(url);
    httpSolrClient.setParser(new XMLResponseParser()); // 设置响应解析器
    httpSolrClient.setConnectionTimeout(500); // 建立连接的最长时间
    this.httpSolrClient = httpSolrClient;
  }
  
  @Test
  public void testAdd() throws IOException, SolrServerException {
    Notice notice = new Notice();
    String desc = "风刀霜剑卡浩丰科技安徽省非富即贵记录时大家啊两个价位cookie（以下统称admckid）的mapping关系，还包括了admckid的人口标签、移动端id（主要是idfa和imei）的人口标签，以及一些黑名单id、ip等数据。"
        +"房东是否哈斯卡哈flash饭卡时打分。由于cookie这种id本身具有不稳定性，所以很多的真实用户的浏览行为会导致大量的新cookie生成，只有及时同步mapping的数据才能命中DMP的人口标签，无法通过预热来获取较高的命中，这就跟缓存存储带来了极大的挑战。"
        +"房东司法鉴定撒雷锋精神大了多副本那带来的消耗是巨大的，另外kv的长短不齐也会带来很多内存碎片，这就需要超大规模的存储方案来解决上述问题。";
    notice.setId(UUID.randomUUID().toString());
    notice.setTitle("附近的拉萨发飞机拉进来发大水");
    notice.setSubject("范德萨卡拉胶弗利萨家里附近的撒立减返的数量急功近利苦尽甘来的时间阿里");
    notice.setDescription(desc);
    notice.setMyfield("我的第一个属性");
    this.httpSolrClient.addBean(notice);
    this.httpSolrClient.commit();
  }

  @Test
  public void testQuery() throws SolrServerException, IOException{
    String keywords = "应用";
    int page = 1;
    int rows = 10;
    
    SolrQuery solrQuery = new SolrQuery(); // 构造搜索条件
    solrQuery.setQuery("text:" + keywords); // 搜索关键词
    // 设置分页 
    solrQuery.setStart((Math.max(page, 1) - 1) * rows);
    solrQuery.setRows(rows);
    
    QueryResponse queryResponse = this.httpSolrClient.query(solrQuery);
    List<Notice> notices = queryResponse.getBeans(Notice.class);
    for (Notice notice : notices) {
      System.out.println(notice.toString());
    }
  }
  
  
  @Test
  public void testHighlighting() throws SolrServerException, IOException{
    String keywords = "应用";
    int page = 1;
    int rows = 10;
    
    SolrQuery solrQuery = new SolrQuery(); // 构造搜索条件
    solrQuery.setQuery("text:" + keywords); // 搜索关键词
    // 设置分页 
    solrQuery.setStart((Math.max(page, 1) - 1) * rows);
    solrQuery.setRows(rows);
    
    // 是否需要高亮
    boolean isHighlighting = true;
    if (isHighlighting) {
      // 设置高亮
      solrQuery.setHighlight(true); // 开启高亮组件
      solrQuery.addHighlightField("title");// 高亮字段
      solrQuery.addHighlightField("subject");
      solrQuery.setHighlightSimplePre("<span style='color:red;'>");// 标记，高亮关键字前缀
      solrQuery.setHighlightSimplePost("</span>");// 后缀
    }
    
    QueryResponse queryResponse = this.httpSolrClient.query(solrQuery);
    List<Notice> notices = queryResponse.getBeans(Notice.class);
    
    if (isHighlighting) {
      // 将高亮的标题数据写回到数据对象中
      Map<String, Map<String, List<String>>> map = queryResponse.getHighlighting();
      for (Map.Entry<String, Map<String, List<String>>> highlighting : map.entrySet()) {
        for (Notice notice : notices) {
          if (!highlighting.getKey().equals(notice.getId().toString())) {
            continue;
          }
          if(highlighting.getValue().get("title") != null){
            notice.setTitle(StringUtils.join(highlighting.getValue().get("title"), ""));
          }
          if(highlighting.getValue().get("subject") != null){
            notice.setSubject(StringUtils.join(highlighting.getValue().get("subject"),""));
          }
          break;
        }
      }
    }
    
    for (Notice notice : notices) {
      System.out.println(notice.toString());
    }
  }
  
  @Test
  public void testDelete() throws SolrServerException, IOException{
    String id = "30a6d598-9762-455a-94f7-21fa011640d1";
    this.httpSolrClient.deleteById(id);
    this.httpSolrClient.commit();
  }


  @Test
  public void addBeans() {

    Notice notice = new Notice();
    String desc = "第一个描述";
    notice.setId(UUID.randomUUID().toString());
    notice.setTitle("第一个title");
    notice.setSubject("第一个subject");
    notice.setDescription(desc);
    notice.setMyfield("我的第一个属性");

    List<Notice> notices = new ArrayList<>();
    notices.add(notice);

    notice = new Notice();

    notice.setId(UUID.randomUUID().toString());
    notice.setTitle("第二个title");
    notice.setSubject("第二个subject");
    notice.setDescription(desc);
    notice.setMyfield("我的第二个属性");

    notices.add(notice);

    try {
      //添加索引库
      UpdateResponse response = httpSolrClient.addBeans(notices);
      httpSolrClient.commit();
      System.out.print(response);
    } catch (SolrServerException e) {
      e.printStackTrace();
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  @Resource(name = "search.houseDbInfoDao")
  private HouseDbInfoDao houseDbInfoDao;

  //批量导入测试
  @Test
  public void batchCreateIndexTest() throws Exception{

    int betchNum = 100;

    CreatIndexRequest pageRequest = new CreatIndexRequest();
    pageRequest.setLimit(betchNum);
    PagingResult<HouseDbInfoVo> pagingResult = houseDbInfoDao.getHouseDbInfoForPage(pageRequest);

    int page = ValueUtil.getPage(((int) pagingResult.getTotal()), betchNum);
    long START = System.currentTimeMillis();
    for(int i=1;i<=page;i++){
        pageRequest.setLimit(betchNum);
        pageRequest.setPage(i);
        pagingResult = houseDbInfoDao.getHouseDbInfoForPage(pageRequest);
        List<HouseDbInfoVo> houseDbInfoVos = pagingResult.getRows();

        httpSolrClient.addBeans(houseDbInfoVos);
        httpSolrClient.commit();
    }
    long END = System.currentTimeMillis();
    System.out.print("批量导入耗时："+(END-START)+"ms"+"，导入总条数："+pagingResult.getTotal()+"每批次条数： "+betchNum);

  }

  @Resource(name = "search.customerDbDao")
  private CustomerDbDao customerDbDao;


  //批量导入测试
//  @Test
//  public void batchCreateIndexForCustomerInfoTest() throws Exception{
//
//    int betchNum = 100;
//
//    PageRequest pageRequest = new PageRequest();
//    pageRequest.setLimit(betchNum);
//    PagingResult<CustomerDbInfoVo1> pagingResult = customerDbDao.getCustomerInfoForPage(pageRequest);
//
//    int page = ValueUtil.getPage(((int) pagingResult.getTotal()), betchNum);
//    long START = System.currentTimeMillis();
//    for(int i=1;i<=page;i++){
//      pageRequest.setLimit(betchNum);
//      pageRequest.setPage(i);
//      pagingResult = customerDbDao.getCustomerInfoForPage(pageRequest);
//      List<CustomerDbInfoVo1> customerDbInfoVos = pagingResult.getRows();
//
//      if(!Check.NuNCollection(customerDbInfoVos)){
//        try {
//          httpSolrClient.addBeans(customerDbInfoVos);
////          httpSolrClient.commit();
//        }catch (Exception e){
//          System.out.println("exception:[],"+e.getMessage());
//        }finally {
//          continue;
//        }
//
//      }
//
//    }
//    long END = System.currentTimeMillis();
//    System.out.print("批量导入耗时："+(END-START)+"ms"+"，导入总条数："+pagingResult.getTotal()+"每批次条数： "+betchNum);
//
//  }











}
