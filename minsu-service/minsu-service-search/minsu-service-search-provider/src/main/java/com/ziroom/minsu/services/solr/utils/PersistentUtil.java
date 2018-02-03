package com.ziroom.minsu.services.solr.utils;

import com.asura.framework.base.util.Check;
import com.asura.framework.utils.LogUtil;
import com.ziroom.minsu.services.search.vo.CommunityInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

/**
 * <p>持久化工具</p>
 * <p/>
 * <PRE>
 * <BR>	修改记录
 * <BR>-----------------------------------------------
 * <BR>	修改日期			修改人			修改内容
 * </PRE>
 *
 * @author afi on 2016/4/13.
 * @version 1.0
 * @since 1.0
 */
public class PersistentUtil {


    private static final Logger LOGGER = LoggerFactory.getLogger(PersistentUtil.class);


    /**
     * 文件内容读入内存
     * @author afi
     * @version 1.0
     * @throws IOException
     */
    public synchronized static List<CommunityInfo> loadExtFile() throws IOException {


        List<CommunityInfo> list = new ArrayList<>();
        String path = "D:\\Personal\\Documents\\Tencent Files\\376472526\\FileRecv\\servarea_110000(1).xml";
        try {
            File file=new File(path);
            if(file.isFile() && file.exists()) { //判断文件是否存在
                InputStreamReader read = new InputStreamReader(
                        new FileInputStream(file), "UTF8");//考虑到编码格式
                BufferedReader bufferedReader = new BufferedReader(read);
                String lineTxt = null;
                int i=0;
                CommunityInfo communityInfo = null;
                while ((lineTxt = bufferedReader.readLine()) != null) {
                    if(lineTxt.contains("<ss:Row>")){
                        i=0;
                        communityInfo = null;
                    }

                    if(i == 1){
                        communityInfo = new CommunityInfo();
                        lineTxt = lineTxt.replace("<ss:Cell><Data ss:Type=\"String\">","").replace("</Data></ss:Cell>","").replace(" ","");
//                        System.out.println(lineTxt);
                        communityInfo.setCommunityName(lineTxt);
                    }else if(i == 2){
                        lineTxt = lineTxt.replace("<ss:Cell><Data ss:Type=\"String\">","").replace("</Data></ss:Cell>","").replace(" ","");
//                        System.out.println(lineTxt);
                        communityInfo.setAreaName(lineTxt);
                        communityInfo.setCityCode("beijing");
                        if(!Check.NuNStr(communityInfo.getCommunityName())){
                            list.add(communityInfo);
                        }
                    }
                    i++;
                }
                read.close();
            }
        } catch (Exception e) {
            LogUtil.error(LOGGER, "e:{}", e);
        }
        return list;

    }





}
