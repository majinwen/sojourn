/**
 * @FileName: TableauTrustedUtil.java
 * @Package com.ziroom.minsu.tableau.common
 * 
 * @author bushujie
 * @created 2017年12月19日 下午8:07:26
 * 
 * Copyright 2011-2015 asura
 */
package com.ziroom.minsu.tableau.common;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;

import javax.servlet.ServletException;

/**
 * <p>TODO</p>
 * 
 * <PRE>
 * <BR>	修改记录
 * <BR>-----------------------------------------------
 * <BR>	修改日期			修改人			修改内容
 * </PRE>
 * 
 * @author bushujie
 * @since 1.0
 * @version 1.0
 */
public class TableauTrustedUtil {
	
	
	/**
	 * 
	 * 用户名 server地址，客户端地址获取认证
	 *
	 * @author bushujie
	 * @created 2017年12月19日 下午8:14:39
	 *
	 * @param wgserver
	 * @param user
	 * @param remoteAddr
	 * @return
	 * @throws ServletException
	 */
	public static String getTrustedTicket(String wgserver, String user, String remoteAddr) throws ServletException{
        OutputStreamWriter out = null;
        BufferedReader in = null;
        try {
            // Encode the parameters
            StringBuffer data = new StringBuffer();
            data.append(URLEncoder.encode("username", "UTF-8"));
            data.append("=");
            data.append(URLEncoder.encode(user, "UTF-8"));
            data.append("&");
            data.append(URLEncoder.encode("client_ip", "UTF-8"));
            data.append("=");
            data.append(URLEncoder.encode(remoteAddr, "UTF-8"));
            System.err.println("http://" + wgserver + "/trusted");
            // Send the request
            URL url = new URL("http://" + wgserver + "/trusted");
            URLConnection conn = url.openConnection();
            conn.setDoOutput(true);
            out = new OutputStreamWriter(conn.getOutputStream());
            out.write(data.toString());
            out.flush();

            // Read the response
            StringBuffer rsp = new StringBuffer();
            in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            String line;
            while ((line = in.readLine()) != null) {
                rsp.append(line);
            }

            return rsp.toString();

        } catch (Exception e) {
            throw new ServletException(e);
        } finally {
            try {
                if (in != null) in.close();
                if (out != null) out.close();
            } catch (IOException e) {
            }
        }
	}
}
