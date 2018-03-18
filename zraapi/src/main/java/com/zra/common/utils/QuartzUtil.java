package com.zra.common.utils;

import com.zra.common.util.DBUtil;
import org.apache.log4j.Logger;
import org.springframework.transaction.annotation.Transactional;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

@Transactional
public class QuartzUtil {
	
	protected static final Logger logger = Logger.getLogger(QuartzUtil.class) ;
	
	public final static String strSec = "0";//  秒标志
	public final static String strMin = "1";//分钟标志
	public final static String strHou = "2";//小时标志
	
	public QuartzUtil(){
		
	}
	/**
	 * 服务器调度同步类，使集群中所有机器的相同quartz任务只有一个机器可以执行成功。
	 * 注意：由于oracle的无阻塞读的副作用，此类需要约束不同job的启动时间不能相同
	 * @param taskName 调度服务名称
	 * @param dTime 间隔时间（单位：秒、分、小时）
	 * @param dFlag 时间类型（"0"：秒、"1"：分、"2"：小时、其他：天）
	 * @throws SQLException 
	 */
	public static Boolean getThreadFlg(String taskName, int dTime, String dStrFlag) throws SQLException {
		
		ResultSet rs = null ;
		Connection conn = DBUtil.makeConnection("dataSource");
		Statement statemt = conn.createStatement() ;
		int maxNo = 0;//获得要插入新记录的ID
		String maxNoSql = "SELECT MAX(FID) FROM TSYSQUARTZQUEUE";
		rs = statemt.executeQuery(maxNoSql);

        try {
            try {
                while (rs.next()) {
                    maxNo = rs.getInt(1) + 1;
                }
            } catch (Exception e) {
                maxNo = 1;
                logger.error("", e);
            }

            String ip = null;
            try{
                InetAddress addr = InetAddress.getLocalHost();
                ip = (String)addr.getHostAddress();
            }catch(UnknownHostException e) {
                ip = "0.0.0.0";
                logger.error(e.getMessage(),e);
            }

            String insertSql = "INSERT INTO TSYSQUARTZQUEUE (FID,FQUARTZID,FQUARTZBEGINTIME,FOPERATIP) VALUES("+maxNo+",'"+taskName+"', now(),'"+ip+"')";//插入语句

            StringBuilder selectSql = new StringBuilder();
            selectSql.append("SELECT COUNT(*) FROM TSYSQUARTZQUEUE WHERE FQUARTZID = '")
                     .append(taskName)
                     .append("' AND FQUARTZBEGINTIME >= SYSDATE");

            StringBuilder chkSql = new StringBuilder();
            chkSql.append(" BEGIN ")
                  .append("   DECLARE ")
                  .append("   v_output VARCHAR(50); ")
                  .append("   v_cnt INT; ")
                  .append(" SELECT count(*) INTO v_cnt FROM TSYSQUARTZQUEUE WHERE FQUARTZID = '")
                  .append(taskName)
                  .append("' AND FQUARTZBEGINTIME >= now() ");

            StringBuilder appendSql = new StringBuilder();
            appendSql.append(";  IF v_cnt > 0 THEN ")
                     .append(" v_output := 'false';  ")
                     .append("  ELSE ")
                     .append(insertSql).append(";")
                     .append(" v_output := 'true';  ")
                     .append("  END IF; ")
                     .append(" set param1 := v_output ; ")
                       .append(" END ; ");

            if(strSec.equals(dStrFlag)) {
                selectSql.append(" -' ")
                         .append(dTime)
                         .append(" '/ ")
                         .append(24*60*60);//秒

                chkSql.append(" -' ")
                      .append(dTime)
                      .append(" '/ ")
                      .append(24*60*60)//秒
                      .append(appendSql);
            }else if(strMin.equals(dStrFlag)) {
                selectSql.append(" -' ")
                         .append(dTime)
                         .append(" '/ ")
                         .append(24*60);//分钟

                chkSql.append(" -' ")
                        .append(dTime)
                        .append(" '/ ")
                        .append(24*60)//分钟
                        .append(appendSql);

            }else if(strHou.equals(dStrFlag)) {
                selectSql.append(" -' ")
                          .append(dTime)
                        .append(" '/ ")
                          .append(24);//小时

                chkSql.append(" -' ")
                        .append(dTime)
                        .append(" '/ ")
                        .append(24)//小时
                        .append(appendSql);

            }else {
                selectSql.append(" - ")
                          .append(dTime);//天

                chkSql.append(" - ")//天
                        .append(dTime)
                        .append(appendSql);
            }

            String res = "true";
            try{
                statemt.executeUpdate(insertSql);
                    }catch(Exception e){
                logger.error("exception is :",e);
                //当ID被占用时校验该ID是否被本任务占用
                res = "false";

            }

            if(res.equals("true")){
                return true;
            }else{
                return false;
            }
        } finally {
            DBUtil.closeConnection(conn , statemt , rs);
        }

    }
}
