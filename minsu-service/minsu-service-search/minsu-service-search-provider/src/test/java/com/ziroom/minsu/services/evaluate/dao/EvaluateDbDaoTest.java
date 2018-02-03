package com.ziroom.minsu.services.evaluate.dao;

import base.BaseTest;
import com.asura.framework.base.util.JsonEntityTransform;
import com.asura.framework.utils.LogUtil;
import com.ziroom.minsu.services.search.vo.EvaluateDbInfoVo;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.Resource;

/**
 * <p>TODO</p>
 * <p/>
 * <PRE>
 * <BR>	修改记录
 * <BR>-----------------------------------------------
 * <BR>	修改日期			修改人			修改内容
 * </PRE>
 *
 * @author afi on 2016/4/11.
 * @version 1.0
 * @since 1.0
 */
public class EvaluateDbDaoTest extends BaseTest {

    private static final Logger LOGGER = LoggerFactory.getLogger(EvaluateDbDaoTest.class);

    @Resource(name="search.evaluateDbDao")
    private EvaluateDbDao evaluateDbDao;





    @Test
    public void TestgetEvaluateByHouse(){
        try {
            EvaluateDbInfoVo evaluateDbInfoVo =evaluateDbDao.getEvaluateByHouse("45fsfdfd6dd4f5");
            System.out.println(JsonEntityTransform.Object2Json(evaluateDbInfoVo));
        }catch (Exception e){
            LogUtil.error(LOGGER, "e:{}", e);
        }
    }

    @Test
    public void TestgetEvaluateByRoom(){
        try {
            EvaluateDbInfoVo evaluateDbInfoVo =evaluateDbDao.getEvaluateByRoom("roomfid4fffdfddfdfdfffdsfds56dsa4f56s4f5");
            System.out.println(JsonEntityTransform.Object2Json(evaluateDbInfoVo));
        }catch (Exception e){
            LogUtil.error(LOGGER, "e:{}", e);
        }
    }

}
