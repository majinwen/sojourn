/**
 * @FileName: TelnetUtil.java
 * @Package com.asura.management.common.utils
 * 
 * @author bushujie
 * @created 2013-10-18 上午9:30:15
 * 
 * Copyright 2011-2015 asura
 */
package com.asura.amp.common.utils;
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

 

public class TelnetUtil {
	
//   //telnet客户端
//   public static TelnetClient tc =new TelnetClient();
//   
//   public static String telnetExecute(String server,int port,String command ,String endStr){
//	   
//	   tc = new TelnetClient();
//	   InputStream in=null;
//	   PrintStream out=null;
//	   
//	   try{
//		   tc.connect(server, port);
//		   in=tc.getInputStream();
//		   out=new PrintStream(tc.getOutputStream());
//		   out.println(command);
//           out.flush();
//           StringBuffer sb = new StringBuffer();
//           InputStreamReader isr = new InputStreamReader(in,"utf-8");
//           BufferedReader br = new BufferedReader(isr);
//           while (true) {
//               int str = br.read();
//               sb.append((char)str);
//               if(sb.toString().endsWith(endStr)){
//            	   break;
//               }
//           }
//           tc.disconnect();
//           return sb.toString();
//	   }catch (Exception e) {
//		   e.printStackTrace();
//		   return "exception error"+e.getMessage();
//	   }
//   }
//   
//   
//   public static void main(String [] args){
//
//       String string=telnetExecute("10.103.14.6", 11212, "get good","END");
//       System.out.println(string);
//       
//       String[] subs = string.split("\n");
//       
//       System.out.println(subs.length);
//       
//       for(int i=0;i<subs.length-1;i++){
//			String[] sb=subs[i].split(" ");
//			System.out.println(sb[1]);
//		}
//
//   }

}

