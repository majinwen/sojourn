package com.ziroom.minsu.mapp.card;

import com.ziroom.minsu.services.common.utils.CloseableHttpsUtil;

import java.util.HashMap;
import java.util.Map;

/**
 * <p>TODO</p>
 * <p/>
 * <PRE>
 * <BR>	修改记录
 * <BR>-----------------------------------------------
 * <BR>	修改日期			修改人			修改内容
 * </PRE>
 *
 * @author afi on 2016/6/26.
 * @version 1.0
 * @since 1.0
 */
public class CardTest {


    public static void main(String[] args) {
        String url = "http://customer.q.ziroom.com/api/add_pay_msg.php";

        //http://customer.q.ziroom.com/api/add_pay_msg.php?uid=f8a4375c-7437-b078-6f4b-5e2b7d6dfcb6&card_name=%E4%B8%AD%E5%9B%BD%E5%B9%BF%E5%A4%A7%E9%93%B6%E8%A1%8C&card_code=555620013254425574&is_del=0&province=%E5%8C%97%E4%BA%AC
        Map<String,String> par = new HashMap<>();
        par.put("uid","664524c5-6e75-ee98-4e0d-667d38b9eee1");
        par.put("card_name","银行名称");
        par.put("card_code","555620013254425574");
        par.put("province","测试北京");
        par.put("is_del","1");
        String rst = CloseableHttpsUtil.sendFormPost(url, par);
        System.out.println(rst);
    }



    public static void main1(String[] args) {
        String url = "http://ami.ziroom.com/AMI/finance/financeConfirm!getAccountBank.do";


        //http://customer.q.ziroom.com/api/add_pay_msg.php?uid=f8a4375c-7437-b078-6f4b-5e2b7d6dfcb6&card_name=%E4%B8%AD%E5%9B%BD%E5%B9%BF%E5%A4%A7%E9%93%B6%E8%A1%8C&card_code=555620013254425574&is_del=0&province=%E5%8C%97%E4%BA%AC

        String rst = CloseableHttpsUtil.sendGet(url,null);
        System.out.println(rst);
    }

}
