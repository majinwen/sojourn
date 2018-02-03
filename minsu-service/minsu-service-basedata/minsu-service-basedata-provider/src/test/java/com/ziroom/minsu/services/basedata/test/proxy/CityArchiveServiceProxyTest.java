package com.ziroom.minsu.services.basedata.test.proxy;

import com.asura.framework.base.util.Check;
import com.asura.framework.base.util.JsonEntityTransform;
import com.asura.framework.base.util.UUIDGenerator;
import com.ziroom.minsu.entity.file.FileRegionItemsEntity;
import com.ziroom.minsu.services.basedata.dto.CityArchivesRequest;
import com.ziroom.minsu.services.basedata.proxy.CityArchiveServiceProxy;
import com.ziroom.minsu.services.basedata.test.base.BaseTest;
import org.junit.Test;

import javax.annotation.Resource;

/**
 * Created with IntelliJ IDEA
 * User: lunan
 * Date: 2016/11/8
 */
public class CityArchiveServiceProxyTest extends BaseTest {

    @Resource(name="basedata.cityArchiveServiceProxy")
    private CityArchiveServiceProxy cityArchiveServiceProxy;

    @Test
    public void getCityArchiveListTest(){
        CityArchivesRequest cityArchivesRequest  = new CityArchivesRequest();
//        cityArchivesRequest.setShowName("北京");
        String archiveList = cityArchiveServiceProxy.getCityArchiveList(JsonEntityTransform.Object2Json(cityArchivesRequest));
        System.err.println(archiveList.toString());
    }

    @Test
    public void getRegionListTest(){
        CityArchivesRequest cityArchivesRequest  = new CityArchivesRequest();
        cityArchivesRequest.setCityCode("110100");
        String archiveList = cityArchiveServiceProxy.getRegionList(JsonEntityTransform.Object2Json(cityArchivesRequest));
        System.err.println(archiveList.toString());
    }

    @Test
    public void saveOrUpRegion(){
        CityArchivesRequest cityArchivesRequest = new CityArchivesRequest();
        cityArchivesRequest.setHotRegionFid("8a9e9aa856126a5b0156126a5bd30000");
        cityArchivesRequest.setHotRegionBrief("测试proxy是否成功修改而不是插入");
        cityArchivesRequest.setHotRegionContent("卡卡卡卡卡卡为了明显一点");
        String resultJson = cityArchiveServiceProxy.saveOrUpRegion(JsonEntityTransform.Object2Json(cityArchivesRequest));
        System.err.println(resultJson);
    }

    @Test
    public void saveOrUpRegionItem(){
        CityArchivesRequest cityArchivesRequest = new CityArchivesRequest();
        cityArchivesRequest.setHotRegionFid("18a9e9aa856126a5b0156126a5bd30000");
        cityArchivesRequest.setItemFid("8a9e9891588f3bac01588f3baee40001");
        cityArchivesRequest.setFid(UUIDGenerator.hexUUID());
//        cityArchivesRequest.setCityCode("110100");
        cityArchivesRequest.setHotRegionFid(cityArchivesRequest.getHotRegionFid());
        cityArchivesRequest.setItemContent("修改推荐项的内容");
        cityArchivesRequest.setItemAbstract("修改推荐项的摘要");
        cityArchivesRequest.setItemName("ccc修改推荐项的名称");
        cityArchivesRequest.setItemBrief("修改推荐项的描述");
        cityArchivesRequest.setCreateFid("8a9e9aaf5456d812015456de97d406aa");
        String resultJson = cityArchiveServiceProxy.saveOrUpItem(JsonEntityTransform.Object2Json(cityArchivesRequest));
        System.err.println(resultJson);
    }

    @Test
    public void saveOrUpArchive(){
        CityArchivesRequest cityArchivesRequest = new CityArchivesRequest();
        cityArchivesRequest.setFid("8a9e9891588f3e7b01588f3e7bd50000");
        cityArchivesRequest.setCityFileContent("<pre>\n" +
                "哈哈哈哈哈测试日志是否添加成功</pre>\n" +
                "\n" +
                "<p><img alt=\"\" src=\"http://10.16.34.48:8080/group1/M00/01/9C/ChAiMFgrygCASs1AAAEZ9MecwXA358.jpg\" style=\"height:600px; width:800px\" /></p>");
        cityArchivesRequest.setCityCode("110100");
        String s = cityArchiveServiceProxy.saveOrUpArchive(JsonEntityTransform.Object2Json(cityArchivesRequest));
        System.err.println(s);

    }

    @Test
    public void getRegionItemsByHotRegionFidTest(){
        CityArchivesRequest cityArchivesRequest = new CityArchivesRequest();
        cityArchivesRequest.setHotRegionFid("18a9e9aa856126a5b0156126a5bd30000");
        String items = cityArchiveServiceProxy.getItemsByHotRegionFid(JsonEntityTransform.Object2Json(cityArchivesRequest));
        System.err.println(items);
    }

}
