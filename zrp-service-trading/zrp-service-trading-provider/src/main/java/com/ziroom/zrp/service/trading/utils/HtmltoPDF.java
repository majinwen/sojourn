package com.ziroom.zrp.service.trading.utils;

import com.itextpdf.awt.geom.Rectangle2D;
import com.itextpdf.text.pdf.PdfReader;
import com.itextpdf.text.pdf.parser.ImageRenderInfo;
import com.itextpdf.text.pdf.parser.PdfReaderContentParser;
import com.itextpdf.text.pdf.parser.RenderListener;
import com.itextpdf.text.pdf.parser.TextRenderInfo;
import org.apache.log4j.Logger;
import org.zefer.pd4ml.PD4Constants;
import org.zefer.pd4ml.PD4ML;
import org.zefer.pd4ml.PD4PageMark;
import org.zefer.pd4ml.PD4ProgressListener;

import java.awt.*;
import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

/**
 * HTML转pdf文件
 * @author
 */
public class HtmltoPDF {

		private static Logger LOGGER = Logger.getLogger(HtmltoPDF.class);

		public static String pdfPath = "/apartment/upload";//合同存储根目录

		public static String contractFile = "/contractFile/";//个人合同存储根目录

		public static String contractEpsFile = "/contractEpsFile/";//企业合同存储根目录

		public static String pdfSuffix = ".pdf";

		private static int i = 0;// 定义返回页码

//		public static String pdfHeaderLogo = "http://localhost:8080/ZRAMS/img/logo/logo_20161015.png"; // 默认地址，在spring中可以注入
		public static String pdfHeaderLogo = "http://zra.ziroom.com/ZRAMS/img/logo/logo_20161015.png"; // 默认地址，在spring中可以注入
		public static String bjpdfHeaderLogo = "http://localhost:8080/ZRAMS/img/logo/logo_110000.png"; // 默认地址，在spring中可以注入

		public static PD4ML pd4ml = null;

		private static void initPD4ML() {
			pd4ml = new PD4ML();

	        pd4ml.setPageInsets(new Insets(0, 20, 10, 20));
	        pd4ml.setHtmlWidth(800);
	        pd4ml.setPageSize(PD4Constants.A4);
	        try {
	        	LOGGER.info("initPD4ML before useTTF");
	        	pd4ml.useTTF("java:fonts", true);
			} catch (FileNotFoundException e) {
				LOGGER.error("---HtmltoPDF-加载字体失败", e);
			}

	       //页眉
	        PD4PageMark headerMark = new PD4PageMark();
	        headerMark.setAreaHeight(40);
	        headerMark.setInitialPageNumber(0);
	        headerMark.setPagesToSkip(0);
	        headerMark.setTitleAlignment(PD4PageMark.CENTER_ALIGN);
	        headerMark.setHtmlTemplate("<div id='lg'><img width='100' height='32' src='"+pdfHeaderLogo+"' height='30'></div><hr/>"); // autocompute
	        pd4ml.setPageHeader(headerMark);

	        //页脚
	        PD4PageMark footerMark = new PD4PageMark();
	        footerMark.setAreaHeight(20);
	        footerMark.setInitialPageNumber(10);
	        footerMark.setHtmlTemplate("");
	        pd4ml.setPageFooter(footerMark);

		}

		/**
		 * htmlstr装pdf文件
		 * @param htmlStr
		 * @param filePath
		 * @throws Exception
		 */
	    public static void htmlStrToPdf(String htmlStr,String filePath) throws Exception
	    {
	    	if(!new File(filePath).getParentFile().exists()) {
				new File(filePath).getParentFile().mkdirs();
			}
	    	//如果已经存在了pdf 那么删除掉
	    	if(new File(filePath).exists()){
	    		new File(filePath).delete();
	    	}
	    	FileOutputStream fos = new FileOutputStream(filePath);
	    	if( pd4ml== null) {
	    		initPD4ML();
	    	}
	        pd4ml.enableDebugInfo();
	        pd4ml.clearCache();
	        pd4ml.render(new StringReader(htmlStr), fos);
	    }

	    public static BlockingQueue<Object> getProgress(){
	    	final BlockingQueue<Object> result = new ArrayBlockingQueue<Object>(1);
	    	pd4ml.monitorProgress(new PD4ProgressListener() {
	        	/**
	        	 * i----messageID
	        	 * j----progress  进度
	        	 * s----notes
	        	 * l -----总毫秒数
	        	 */
				@Override
				public void progressUpdate(int i, int j, String s, long l) {

					try {
						if(j == 100){
							result.put("100");
						}
					} catch (Exception e) {
					    LOGGER.error("", e);
					}
				}
			});
	    	return result;
	    }

		public static void setPdfHeaderLogo(String pdfHeaderLogo) {
			HtmltoPDF.pdfHeaderLogo = pdfHeaderLogo;
		}


		/**
		 * htmlstr装pdf文件
		 * @author tianxf9
		 * @param htmlStr
		 * @param filePath
		 * @throws Exception
		 */
	    public static void htmlStrToPdf(String htmlStr,String filePath,String cityId) throws Exception
	    {
	    	if(!new File(filePath).getParentFile().exists()) {
				new File(filePath).getParentFile().mkdirs();
			}
	    	//如果已经存在了pdf 那么删除掉
	    	if(new File(filePath).exists()){
	    		new File(filePath).delete();
	    	}
	    	FileOutputStream fos = new FileOutputStream(filePath);
	    	// if( pd4ml== null) {
	    		initPD4ML(cityId);
	    	//}
	        pd4ml.enableDebugInfo();
	        pd4ml.clearCache();
	        pd4ml.render(new StringReader(htmlStr), fos);
	    }

	    /**
	     * tianxf9
	     * @param cityId
	     */
		private static void initPD4ML(String cityId) {
			pd4ml = new PD4ML();

	        pd4ml.setPageInsets(new Insets(0, 20, 10, 20));
	        pd4ml.setHtmlWidth(800);
	        pd4ml.setPageSize(PD4Constants.A4);
	        try {
	        	LOGGER.info("initPD4ML before useTTF");
	        	pd4ml.useTTF("java:fonts", true);
			} catch (FileNotFoundException e) {
				LOGGER.error("---HtmltoPDF-加载字体失败", e);
			}

	       //页眉
	        PD4PageMark headerMark = new PD4PageMark();
	        headerMark.setAreaHeight(40);
	        headerMark.setInitialPageNumber(0);
	        headerMark.setPagesToSkip(0);
	        headerMark.setTitleAlignment(PD4PageMark.CENTER_ALIGN);
	        //and by tianxf9 20161015 更改logo
	       // if(cityId.equals(SysConstant.BJID)) {
	        headerMark.setHtmlTemplate("<div id='lg'><img width='100' height='32' src='"+pdfHeaderLogo+"' height='30'></div><hr/>"); // autocompute
	      //  }else {
	        	//headerMark.setHtmlTemplate("<div id='lg'><img width='100' height='32' src='"+pdfHeaderLogo+"' height='30'></div><hr/>"); // autocompute
	       // }
	        //end by tianxf9
	        pd4ml.setPageHeader(headerMark);

	        //页脚
	        PD4PageMark footerMark = new PD4PageMark();
	        footerMark.setAreaHeight(20);
	        footerMark.setInitialPageNumber(10);
	        footerMark.setHtmlTemplate("");
	        pd4ml.setPageFooter(footerMark);

		}

		public static void main(String[] args) {
//			try {
//				HtmltoPDF.htmlStrToPdf("测试中文", "E:\\apartment\\upload\\contractFile\\test.pdf");
//			} catch (Exception e) {
//			    LOGGER.error("", e);//			}
			java.util.List<HashMap<String,Object>> espCoordinateMap = getKeyWordsCoordinate("E:\\apartment\\upload\\contractFile\\test.pdf","承租人乙方");
			System.err.print(espCoordinateMap);

//            java.util.List<HashMap<String,Object>> keyWordCoordinateMaps = getKeyWordsCoordinate("C:\\Users\\Administrator\\Desktop\\测试合同\\test.pdf","出租人(甲方)签章");
//            System.err.print(keyWordCoordinateMaps);
		}

		/**
		  * @description: 在指定的pdf文件中查找关键字的页码和坐标
		  * @author: lusp
		  * @date: 2017/11/13 上午 9:26
		  * @params: filePath，keyWord
		  * @return: HashMap<String,Object>
		  */
		public static java.util.List<HashMap<String,Object>> getKeyWordsCoordinate(String filePath,String keyWord){
			java.util.List<HashMap<String,Object>> keyWordCoordinateMaps = new ArrayList<>();// 定义关键字坐标集合
			PdfReader pdfReader = null;
			try{
				pdfReader = new PdfReader(filePath);
				int pageNum = pdfReader.getNumberOfPages();
				PdfReaderContentParser pdfReaderContentParser = new PdfReaderContentParser(pdfReader);
				for(i=1;i<pageNum;i++){
					pdfReaderContentParser.processContent(i, new RenderListener() {
						@Override
						public void beginTextBlock() {}

						@Override
						public void renderText(TextRenderInfo textRenderInfo) {
							String text = textRenderInfo.getText();
							if(null != text && text.contains(keyWord)){
								Rectangle2D.Float boundingRectange = textRenderInfo.getBaseline().getBoundingRectange();
                                HashMap<String,Object> keyWordCoordinateMap = new HashMap<>();
								keyWordCoordinateMap.put("pageNum",i);
								keyWordCoordinateMap.put("X",boundingRectange.x);
								keyWordCoordinateMap.put("Y",boundingRectange.y);
                                keyWordCoordinateMaps.add(keyWordCoordinateMap);
							}
						}

						@Override
						public void endTextBlock() {}

						@Override
						public void renderImage(ImageRenderInfo imageRenderInfo) {}
					});
				}
			}catch (IOException e){
				e.printStackTrace();
			}finally{
				try {
					pdfReader.close();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
			return keyWordCoordinateMaps;
		}
}
