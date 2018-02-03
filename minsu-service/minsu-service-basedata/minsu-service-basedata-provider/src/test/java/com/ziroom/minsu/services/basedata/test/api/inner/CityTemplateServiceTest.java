package com.ziroom.minsu.services.basedata.test.api.inner;

import com.asura.framework.base.entity.DataTransferObject;
import com.asura.framework.base.util.JsonEntityTransform;
import com.asura.framework.base.util.UUIDGenerator;
import com.asura.framework.cache.redisOne.RedisOperations;
import com.asura.framework.utils.LogUtil;
import com.ziroom.minsu.entity.base.MinsuEleEntity;
import com.ziroom.minsu.entity.conf.CityTemplateEntity;
import com.ziroom.minsu.entity.conf.ConfDicEntity;
import com.ziroom.minsu.entity.conf.DicItemEntity;
import com.ziroom.minsu.entity.conf.TemplateEntity;
import com.ziroom.minsu.services.basedata.api.inner.CityTemplateService;
import com.ziroom.minsu.services.basedata.entity.TreeNodeVo;
import com.ziroom.minsu.services.basedata.test.base.BaseTest;
import com.ziroom.minsu.services.common.dto.PageRequest;
import com.ziroom.minsu.valenum.productrules.ProductRulesEnum;
import com.ziroom.minsu.valenum.productrules.ProductRulesEnum0017Enum;
import com.ziroom.minsu.valenum.traderules.TradeRulesEnum;
import org.codehaus.jackson.type.TypeReference;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import javax.annotation.Resource;
import java.util.List;

/**
 * <p>程式模板测试</p>
 * <p/>
 * <PRE>
 * <BR>	修改记录
 * <BR>-----------------------------------------------
 * <BR>	修改日期			修改人			修改内容
 * </PRE>
 *
 * @author afi on 2016/3/24.
 * @version 1.0
 * @since 1.0
 */
public class CityTemplateServiceTest extends BaseTest {

	private static final Logger LOGGER = LoggerFactory.getLogger(CityTemplateServiceTest.class);

    @Resource(name="basedata.cityTemplateProxy")
    private CityTemplateService cityTemplateService;
    
    @Autowired
	private RedisOperations redisOperations;
    
    
    
    @Test
    public void clearRedis(){
    	
    	String key="minsu:redis:confTradeRulesEnum005";
    	
    	String listJson=redisOperations.get(key);
    	System.out.println(listJson);
    	
    	redisOperations.del(key);
    	listJson=redisOperations.get(key);
    	
    	System.err.println(listJson);
    	
    	
    }


    @Test
    public void testtse(){
        try {

            String aa= "{\"id\":null,\"fid\":\"8a9e9cc953ae451d0153ae46c0910001\",\"showName\":\"d\",\"dicCode\":\"d\",\"dicIndex\":12,\"pfid\":\"8a9e9cce53a250680153a25068550000\",\"dicLevel\":1,\"createDate\":null,\"lastModifyDate\":null,\"createFid\":null,\"dicType\":null,\"isDel\":null,\"isEdit\":1}";
            String rst =  cityTemplateService.insertConfDic(JsonEntityTransform.Object2Json(aa));
            System.out.println(rst);
        }catch (Exception e){
        	LogUtil.error(LOGGER, "error:{}", e);
        }
    }



    @Test
    public void TestinsertDicItem(){
        try {
            DicItemEntity dicItemEntity = new DicItemEntity();
            dicItemEntity.setShowName("测试");
            dicItemEntity.setDicCode("12313123");
            dicItemEntity.setTemplateFid("ssss");
            dicItemEntity.setItemValue("value");
            String rst =  cityTemplateService.insertDicItem(JsonEntityTransform.Object2Json(dicItemEntity));
            System.out.println(rst);
        }catch (Exception e){
        	LogUtil.error(LOGGER, "error:{}", e);
        }
    }


    @Test
    public void TestinsertConfDic(){
        try {

            ConfDicEntity confDicEntity = new ConfDicEntity();
            // confDicEntity.setDicCode("code");
            confDicEntity.setShowName("d");
            confDicEntity.setDicLevel(1);
            confDicEntity.setDicIndex(12);
            confDicEntity.setFid("8a9e9cc953222ae451d015ww3ae46c0910001");
            confDicEntity.setPfid("8a9e9cce53a250680153a25068550000");
            confDicEntity.setDicType(2);
            confDicEntity.setIsEdit(1);
            String rst =  cityTemplateService.insertConfDic(JsonEntityTransform.Object2Json(confDicEntity));
            System.out.println(rst);
        }catch (Exception e){
        	LogUtil.error(LOGGER, "error:{}", e);
        }
    }




    @Test
    public void TestinsertConfDicWithPid(){
        try {

            ConfDicEntity dicEntity = new ConfDicEntity();
            dicEntity.setShowName("测试规则2");
            dicEntity.setDicCode("CSGZ2");
            dicEntity.setDicIndex(10);
            dicEntity.setDicLevel(2);
            dicEntity.setPfid("8a9e9cce53a24f4a0153a24f4a500000");
            dicEntity.setFid(UUIDGenerator.hexUUID());
            String rst =  cityTemplateService.insertConfDic(JsonEntityTransform.Object2Json(dicEntity));
            System.out.println(rst);
        }catch (Exception e){
        	LogUtil.error(LOGGER, "error:{}", e);
        }
    }


    @Test
    public void TestgetConfDicByFid(){
        try {
            String rst =  cityTemplateService.getConfDicByFid("8a9e9cce53a24f4a0153a24f4a500000");
            System.out.println(rst);
        }catch (Exception e){
        	LogUtil.error(LOGGER, "error:{}", e);
        }
    }


    @Test
    public void TestgetDicTree(){
        try {


            String resultJson = cityTemplateService.getDicTree("2");
            DataTransferObject resultDto = JsonEntityTransform.json2DataTransferObject(resultJson);
            List<TreeNodeVo> treeList=resultDto.parseData("list", new TypeReference<List<TreeNodeVo>>() {
            });
            System.out.println(resultJson);

        }catch (Exception e){
        	LogUtil.error(LOGGER, "error:{}", e);
        }
    }




    @Test
    public void insertCityTemplate(){
        try {
            CityTemplateEntity pageRequest = new CityTemplateEntity();
            pageRequest.setCityCode("BJS");
            pageRequest.setTemplateFid("template_fid");
            String aa = cityTemplateService.insertCityTemplate(JsonEntityTransform.Object2Json(pageRequest));
            System.out.println(aa);
        }catch (Exception e){
        	LogUtil.error(LOGGER, "error:{}", e);
        }
    }


    @Test
    public void TestgetTemplateListPage(){
        try {
            PageRequest pageRequest = new PageRequest();
            String aa = cityTemplateService.getTemplateListByPage(JsonEntityTransform.Object2Json(pageRequest));
            System.out.println(aa);
        }catch (Exception e){
        	LogUtil.error(LOGGER, "error:{}", e);
        }
    }


    @Test
    public void TestinsertTemplateWithPfid(){
        try {
            TemplateEntity templateEntity = new TemplateEntity();
            templateEntity.setPfid("template_fid");
            templateEntity.setTemplateName("testeste");
            templateEntity.setCreateFid("sss");
            cityTemplateService.insertTemplate(JsonEntityTransform.Object2Json(templateEntity));
        }catch (Exception e){
        	LogUtil.error(LOGGER, "error:{}", e);
        }
    }

    @Test
    public void TestinsertTemplate(){
        try {
            TemplateEntity templateEntity = new TemplateEntity();
            templateEntity.setTemplateName("testeste");
            templateEntity.setCreateFid("sss");
            cityTemplateService.insertTemplate(JsonEntityTransform.Object2Json(templateEntity));
        }catch (Exception e){
        	LogUtil.error(LOGGER, "error:{}", e);
        }
    }



    @Test
    public void TestgetDicItemListByCodeAndTemplateParNUll1(){
        try {
            String  aa = cityTemplateService.getDicItemListByCodeAndTemplate("code","");
            System.out.println(JsonEntityTransform.Object2Json(aa));
        }catch (Exception e){
        	LogUtil.error(LOGGER, "error:{}", e);
        }
    }

    @Test
    public void TestgetDicItemListByCodeAndTemplateParNUll2(){
        try {
            String  aa = cityTemplateService.getDicItemListByCodeAndTemplate("","adsad");
            System.out.println(JsonEntityTransform.Object2Json(aa));
        }catch (Exception e){
        	LogUtil.error(LOGGER, "error:{}", e);
        }
    }
    
    /**
     * 
     * 子项list查询测试
     *
     * @author bushujie
     * @created 2016年3月26日 下午5:51:03
     *
     */
    @Test
    public void getSubDicListTest(){
    	String listJsonString=cityTemplateService.getSelectSubDic(null, ProductRulesEnum.ProductRulesEnum002.getValue());
    	System.out.println(listJsonString);
    }
    
    /**
     * 
     * 值列表下拉测试
     *
     * @author bushujie
     * @created 2016年3月26日 下午5:52:13
     *
     */
    @Test
    public void getSelectTest(){
    	String listJson=cityTemplateService.getSelectEnum(null, ProductRulesEnum.ProductRulesEnum005.getValue());
    	System.out.println(listJson);
    }
    
    /**
     * 
     * 唯一值获取测试
     *
     * @author bushujie
     * @created 2016年3月26日 下午5:53:21
     *
     */
    @Test
    public void getTextValueTest(){
    	String textValue=cityTemplateService.getTextValue(null,TradeRulesEnum.TradeRulesEnum0019.getValue());
    	System.out.println(textValue);
    }

    /**
     * @author afi
     * 唯一值获取测试
     */
    @Test
    public void getTextListByLikeCodes(){
        String returnJson=cityTemplateService.getTextListByLikeCodes(null, TradeRulesEnum.TradeRulesEnum005.getValue());
        DataTransferObject rerunDto = JsonEntityTransform.json2DataTransferObject(returnJson);
        List<MinsuEleEntity> returnList = null;
        if(rerunDto.getCode() == DataTransferObject.SUCCESS){
            returnList = rerunDto.parseData("confList", new TypeReference<List<MinsuEleEntity>>() {
            });
            System.out.println(JsonEntityTransform.Object2Json(returnList));
        }else {

        }
    }


    /**
     * @author afi
     * 唯一值获取测试
     */
    @Test
    public void getTextListByCodes(){
        String textValue=cityTemplateService.getTextListByCodes(null, "TradeRulesEnum008002");
        System.out.println(textValue);
    }
    
    @Test
    public void getEffectiveSelectEnum(){
    	String hotRegionJson=cityTemplateService.getEffectiveSelectEnum(null,
                ProductRulesEnum0017Enum.ProductRulesEnum0017003.getValue());
    	System.err.println(hotRegionJson);
    }



}
