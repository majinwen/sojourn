/**
 * @FileName: TT.java
 * @Package com.minsu.spider.test
 * 
 * @author zhangshaobin
 * @created 2016年6月27日 下午9:05:14
 * 
 * Copyright 2016-2025 ziroom
 */
package com.ziroom.minsu.spider.test;

import java.io.File;
import java.io.IOException;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.jsoup.select.Selector;

/**
 * <p>TODO</p>
 * 
 * <PRE>
 * <BR>	修改记录
 * <BR>-----------------------------------------------
 * <BR>	修改日期			修改人			修改内容
 * </PRE>
 * 
 * @author zhangshaobin
 * @since 1.0
 * @version 1.0
 */
public class TT {
	
	private static String split_zhangshaobin = "zhangshaobin";
	
	public static void main(String []args) {
		try {
			AnlysisHTMLByURL();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
    public static void  AnalysisHTMLByString()
    {
       String  html="<p><a href=\"a.html\">a</p><p> 文本</p>";
       Document doc = Jsoup.parse(html);
       Elements ele=doc.getElementsByTag("p");
       for(Element e :ele)
       {
             System.out.println(e.text());
           
       }	
       
       
   }
   
   public static void AnlysisHTMLByFile() throws IOException
   {
        File file=new File(System.getProperty("user.dir")+"\\a.html");
        Document doc=Jsoup.parse(file, "UTF-8");
        Elements eles=doc.getElementsByTag("a");
        for(Element e :eles)
        {
              System.out.println(e.text());
              System.out.println(e.attr("href"));
        }
       Element ele =doc.getElementById("btn");
       System.out.println(ele.html());
       
   }
   
   public static void  AnlysisHTMLByURL2() throws IOException
   {
      Document doc=  Jsoup.connect("https://www.baidu.com/s?wd=jsoup%20https%E8%AF%B7%E6%B1%82&rsp=0&f=1&oq=jsoup%20https&tn=baiduhome_pg&ie=utf-8&rsv_idx=2&rsv_pq=a8ec3834000c0771&rsv_t=0ce8iC9CStxFaVJCxTkxVbvKs9ZAkM%2FTVhhN2T5nNw4N2JM0qFHyKwG5XcAUSjwUlR56&rqlang=cn&rsv_ers=xn1&rs_src=0").get();
      System.out.println(doc.html());
      
   }
   
   public static void  AnlysisHTMLByURL() throws IOException
   {
      Document doc=  Jsoup.connect("http://bj.xiaozhu.com/search-duanzufang-p2-0/").get();
      Elements es = Selector.select("li", doc.getElementsByTag("li"));
      for (Element e : es) {
    	  String detailurl = e.getElementsByTag("a").attr("href");
    	  if (detailurl.contains("fangzi")) {
    		  System.out.println("_______________________________________________________________");
    		  System.out.println(detailurl + split_zhangshaobin + e.getElementsByClass("commenthref").text() + split_zhangshaobin + e.getElementsByTag("span").text() + split_zhangshaobin + e.getElementsByTag("em").text());
    	  }
      }
      
   }

}
