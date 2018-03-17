/**
 * @FileName: TableauTest.java
 * @Package com.ziroom.minsu.ups.common.util
 * 
 * @author bushujie
 * @created 2017年12月19日 下午5:16:07
 * 
 * Copyright 2011-2015 asura
 */
package com.ziroom.minsu.ups.common.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;

import javax.servlet.ServletException;

import com.ziroom.minsu.services.common.sms.base.HttpClientUtils;
import com.ziroom.minsu.services.common.utils.HttpUtil;

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
public class TableauTest {

	/**
	 * TODO
	 *
	 * @author bushujie
	 * @created 2017年12月19日 下午5:16:07
	 *
	 * @param args
	 * @throws ServletException 
	 */
	public static void main(String[] args) throws ServletException {
		
		    final String user = "lvju";
	        final String wgserver = "10.16.25.13";
	        final String dst = "views/_1/1";
	        final String params = ":iid=1";

	        String ticket = getTrustedTicket(wgserver, user, "10.30.26.61");
	        
	        System.err.println(ticket);
	        if ("-1".equals(ticket)) {
	            // handle error
	            throw new ServletException("Invalid ticket " + ticket);

	        } else {
	            System.err.println("http://" + wgserver + "/trusted/" + ticket + "/" + dst + "?" + params);
	        }
		
	}
	
	/**
	 * 
	 * ceshi
	 *
	 * @author bushujie
	 * @created 2017年12月19日 下午5:20:20
	 *
	 * @param wgserver
	 * @param user
	 * @param remoteAddr
	 * @return
	 * @throws ServletException
	 */
	public static String getTrustedTicket(String wgserver, String user, String remoteAddr)
            throws ServletException {
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
