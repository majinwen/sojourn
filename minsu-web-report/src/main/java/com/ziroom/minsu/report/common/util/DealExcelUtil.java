package com.ziroom.minsu.report.common.util;

import com.asura.framework.base.entity.BaseEntity;
import com.asura.framework.base.paging.PagingResult;
import com.asura.framework.base.util.Check;
import com.asura.framework.utils.LogUtil;
import com.ziroom.minsu.report.common.constant.Constant;
import com.ziroom.minsu.report.common.service.ReportService;
import com.ziroom.minsu.services.common.dto.PageRequest;
import com.ziroom.minsu.services.common.utils.ValueUtil;

import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFPatriarch;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.HSSFColor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.io.OutputStream;
import java.util.List;
import java.util.Map;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;



/**
 * <p>主要用于 上层与底层  之间数据传递</p>
 * <PRE>
 * <BR>	修改记录
 * <BR>-----------------------------------------------
 * <BR>	修改日期			修改人			修改内容
 * </PRE>
 *
 * @author afi
 * @version 1.0
 * @since 1.0
 */
public class DealExcelUtil{
	/**
	 * 日志对象
	 */
	private static final Logger LOGGER = LoggerFactory.getLogger(DealExcelUtil.class);
	private int limit = Constant.PAGE_LIMIT;
    
    @SuppressWarnings("rawtypes")
	private ReportService reportService ;
    
	private String[] headers;
	private String fileName;
	private PageRequest pageRequest;
	
	
	@SuppressWarnings("rawtypes")
	public DealExcelUtil(ReportService reportService, PageRequest pageRequest,
			String[] headers, String fileName) {
		this.reportService = reportService;
		this.headers = headers;
		this.fileName = fileName;
		this.pageRequest = pageRequest;
	}
    
	/**
	 * 导出基本的文件，根据文件记录长度选择生成文件类型
	 * @param response
	 */
	public void exportExcelFile(HttpServletResponse response,List<BaseEntity> dataList){
		 OutputStream outputStream = null;
		 response.setContentType("application/vnd.ms-excel");    
		 response.setCharacterEncoding("utf-8");  
		 response.setHeader("Content-disposition", "attachment;filename="+fileName+".xls");     
         try {
        	outputStream = response.getOutputStream();
        	this.exportExcel(outputStream,dataList);
         }catch(IOException ex){
        	 LogUtil.error(LOGGER, "exportExcelFile error:{}", ex);
		 }  
		 
	}

	/**
	 * 导出基本的文件，根据文件记录长度选择生成文件类型
	 * @param response
	 */
	public void exportExcelFile(HttpServletResponse response,List<BaseEntity> dataList, String pattern){
		if(Check.NuNCollection(dataList)){
			LogUtil.error(LOGGER, "导出的数据为空");
			return;
		}
		OutputStream outputStream = null;
		response.setContentType("application/vnd.ms-excel");
		response.setCharacterEncoding("utf-8");
		response.setHeader("Content-disposition", "attachment;filename="+fileName+".xls");
		try {
			outputStream = response.getOutputStream();
			this.exportExcel(outputStream,dataList,pattern);
		}catch(IOException ex){
			LogUtil.error(LOGGER, "exportExcelFile error:{}", ex);
		}

	}

	
	/**
	 * 
	 * 导出基本的文件,生成多张工作表
	 *
	 * @author liujun
	 * @created 2017年1月19日
	 *
	 * @param response
	 * @param dataMap
	 */
	public void exportMultiSheetExcelFile(HttpServletResponse response, Map<String, List<? extends BaseEntity>> dataMap) {
		OutputStream os = null;
		response.setContentType("application/vnd.ms-excel");
		response.setCharacterEncoding("utf-8");
		response.setHeader("Content-disposition", "attachment;filename=" + fileName + ".xls");
		try {
			os = response.getOutputStream();
			ExportExcel<BaseEntity> ex = new ExportExcel<BaseEntity>();
			ex.exportMultiSheetExcel(headers, dataMap, os);
			os.flush();
			os.close();
		} catch (IOException ex) {
			LogUtil.error(LOGGER, "exportExcelFile error:{}", ex);
		}

	}

	/**
	 * 导出基本的文件，根据文件记录长度选择生成文件类型
	 * @param response
	 */
	public void exportFile(HttpServletResponse response){
		 OutputStream outputStream = null;
		 try{
		    if(checkIsZipFile()){
				response.setContentType("application/zip");
				response.setCharacterEncoding("utf-8");  
				response.setHeader("Content-Disposition", "attachment;filename="+fileName+".zip"); 
				outputStream = response.getOutputStream();
		        exportZip(outputStream);
			}else{
				response.setContentType("application/vnd.ms-excel");    
				response.setHeader("Content-disposition", "attachment;filename="+fileName+".xls");     
		        outputStream = response.getOutputStream();
		        exportExcel(outputStream);
			} 
		 }catch(Exception ex){
			 LogUtil.error(LOGGER, "exportFile error:{}", ex);
		 }
		
	}
	
	/**
	 * 导出zip文件
	 * @param response
	 */
	public void exportZipFile(HttpServletResponse response){
		 OutputStream outputStream = null;
		 try{
		    
			response.setContentType("application/zip");
			response.setCharacterEncoding("utf-8");  
			response.setHeader("Content-Disposition", "attachment;filename="+fileName+".zip"); 
			outputStream = response.getOutputStream();
	        exportZip(outputStream);
		 }catch(Exception ex){
			 LogUtil.error(LOGGER, "exportFile error:{}", ex);
		 }
		
	}
	
	/**
	 * 导出zip文件,指定调用方法名
	 * @param response
	 */
	public void exportZipFile(HttpServletResponse response,String methodName){
		 OutputStream outputStream = null;
		 try{
		    
			response.setContentType("application/zip");
			response.setCharacterEncoding("utf-8");  
			response.setHeader("Content-Disposition", "attachment;filename="+fileName+".zip"); 
			outputStream = response.getOutputStream();
	        exportZip(outputStream,methodName);
		 }catch(Exception ex){
			 LogUtil.error(LOGGER, "exportFile error:{}", ex);
		 }
		
	}
	
	/**
	 * 校验记录长度
	 * @param response
	 */
	@SuppressWarnings("unchecked")
	public boolean checkIsZipFile(){
		boolean result = false;
		Long resultCount = reportService.countDataInfo(pageRequest);
		if(resultCount > limit){
			result = true;
		}
		return result;
	}
	
	/**
	 * 导出excel
	 * @param os
	 */
	@SuppressWarnings("unchecked")
	public void exportExcel(OutputStream os){
		pageRequest.setLimit(limit);
		ExportExcel<BaseEntity> ex = new ExportExcel<BaseEntity>();
		try {
			PagingResult<BaseEntity> result = reportService.getPageInfo(pageRequest);
			List<BaseEntity> dataset = result.getRows();
			ex.exportExcel(headers, dataset, os);
			os.flush();
			os.close();  
		 }catch(Exception e){
			 LogUtil.error(LOGGER, "exportExcel error:{}",e);
		 } 
	}
	
	/**
	 * 导出excel
	 * @param os
	 */
	public void exportExcel(OutputStream os,List<BaseEntity> dataset){
		ExportExcel<BaseEntity> ex = new ExportExcel<BaseEntity>();
		try {
			ex.exportExcel(headers, dataset, os);
			os.flush();
			os.close();  
		 }catch(Exception e){
			 LogUtil.error(LOGGER, "exportExcel error:{}",e);
		 } 
	}

	/**
	 * 导出excel
	 * @param os
	 */
	public void exportExcel(OutputStream os,List<BaseEntity> dataset, String pattern){
		ExportExcel<BaseEntity> ex = new ExportExcel<>();
		try {
			ex.exportExcel(headers, dataset, os, pattern);
			os.flush();
			os.close();
		}catch(Exception e){
			LogUtil.error(LOGGER, "exportExcel error:{}",e);
		}
	}

	/**
	 * 导出zip文件
	 * @param os
	 */
	@SuppressWarnings("unchecked")
	public void exportZip(OutputStream ostream){
		if(Check.NuNObj(reportService.getPageInfo(pageRequest))){
			return;
		}
		Long resultCount = reportService.getPageInfo(pageRequest).getTotal();
		pageRequest.setLimit(limit);
		
		ExportExcel<BaseEntity> ex = new ExportExcel<BaseEntity>();
		try {
			int pageAll = ValueUtil.getPage(resultCount.intValue(), limit);
			ZipOutputStream os =  new ZipOutputStream(ostream);
			for(int i=0,page = 1;i<pageAll;i++,page++){
				pageRequest.setPage(page);
				PagingResult<BaseEntity> result = reportService.getPageInfo(pageRequest);
				List<BaseEntity> dataset = result.getRows();
				ZipEntry entry = new ZipEntry(fileName+"-"+page+".xls");
				os.putNextEntry(entry);
				ex.exportExcel(headers, dataset, os);
			}
			os.flush();
			os.close(); 
			
		  }catch(Exception e){
				 LogUtil.error(LOGGER, "exportZip error:{}",e);
		  } 
	}
   
	/**
	 * 导出zip文件
	 * @param os
	 */
	@SuppressWarnings("unchecked")
	public void exportZip(OutputStream ostream,String methodName){
		try {
			PagingResult<BaseEntity> forPageCount = (PagingResult<BaseEntity>) ReflectUtils.invoke(reportService,methodName,new Class[]{ pageRequest.getClass()}, new Object [] {pageRequest});
			Long resultCount = forPageCount.getTotal();
			pageRequest.setLimit(limit);
			ExportExcel<BaseEntity> ex = new ExportExcel<BaseEntity>();
			int pageAll = ValueUtil.getPage(resultCount.intValue(), limit);
			ZipOutputStream os =  new ZipOutputStream(ostream);
			for(int i=0,page = 1;i<pageAll;i++,page++){
				pageRequest.setPage(page);
				PagingResult<BaseEntity> result = (PagingResult<BaseEntity>) ReflectUtils.invoke(reportService,methodName,new Class[]{ pageRequest.getClass()}, new Object [] {pageRequest});
				List<BaseEntity> dataset = result.getRows();
				ZipEntry entry = new ZipEntry(fileName+"-"+page+".xls");
				os.putNextEntry(entry);
				ex.exportExcel(headers, dataset, os);
			}
			os.flush();
			os.close(); 
			
		  }catch(Exception e){
			LogUtil.error(LOGGER, "exportZip error:{}",e);
		  } 
	}
	
	/**
	 * 
	 * 导出Excel或者zip压缩
	 *
	 * @author bushujie
	 * @created 2017年6月6日 下午3:01:30
	 *
	 * @param ostream
	 * @param methodName
	 */
	@SuppressWarnings("unchecked")
	public void exportExcelOrZip(HttpServletResponse response,String methodName,String title){
		try {
			PagingResult<BaseEntity> forPageCount = (PagingResult<BaseEntity>) ReflectUtils.invoke(reportService,methodName,new Class[]{ pageRequest.getClass()}, new Object [] {pageRequest});
			Long resultCount = forPageCount.getTotal();
			//判断是否超出excel最大值
			if(resultCount<Constant.EXCEL_NUM_LIMIT){
				exportExcel(response,methodName,title);
			} else {
				exportZipFile(response,methodName);
			}
		 } catch(Exception e) {
			 LogUtil.error(LOGGER, "excel导出失败！ error:{}",e);
		 } 
	}
	
	/**
	 * 
	 * 导出excel (按大区分页结构使用)
	 *
	 * @author bushujie
	 * @created 2017年6月7日 上午10:31:28
	 *
	 * @param response
	 * @param methodName
	 */
	@SuppressWarnings("unchecked")
	public void exportExcel(HttpServletResponse response,String methodName,String title){
		try {
			PagingResult<BaseEntity> forPageCount = (PagingResult<BaseEntity>) ReflectUtils.invoke(reportService,methodName,new Class[]{ pageRequest.getClass()}, new Object [] {pageRequest});
			Long resultCount = forPageCount.getTotal();
			pageRequest.setLimit(Constant.AREA_READ_LIMIT);
			ExportExcel<BaseEntity> ex = new ExportExcel<BaseEntity>();
			//一共多少页
			int pageAll = ValueUtil.getPage(resultCount.intValue(), Constant.AREA_READ_LIMIT);
			HSSFWorkbook workbook=null;
			HSSFCellStyle style2=null;
			HSSFFont font2 =null;
			HSSFPatriarch patriarch=null;
			HSSFFont font3=null;
			//一个大区查询一次
			response.setContentType("application/vnd.ms-excel");
			response.setCharacterEncoding("utf-8");
			response.setHeader("Content-disposition", "attachment;filename=" + fileName + ".xls");
			for(int i=1;i<=pageAll;i++){
				pageRequest.setPage(i);
				PagingResult<BaseEntity> result = (PagingResult<BaseEntity>) ReflectUtils.invoke(reportService,methodName,new Class[]{ pageRequest.getClass()}, new Object [] {pageRequest});
				List<BaseEntity> dataset = result.getRows();
				if(i==1){
					workbook=ex.createWorkbook(headers, dataset,title);
			        style2 = workbook.createCellStyle();
			        style2.setBorderBottom(HSSFCellStyle.BORDER_THIN);
			        style2.setBorderLeft(HSSFCellStyle.BORDER_THIN);
			        style2.setBorderRight(HSSFCellStyle.BORDER_THIN);
			        style2.setBorderTop(HSSFCellStyle.BORDER_THIN);
			        style2.setAlignment(HSSFCellStyle.ALIGN_CENTER);
			        style2.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);
			        font2 = workbook.createFont();// 生成另一个字体
			        font2.setBoldweight(HSSFFont.BOLDWEIGHT_NORMAL);
			        style2.setFont(font2);// 把字体应用到当前的样式
                    font3 = workbook.createFont();
                    font3.setColor(HSSFColor.BLUE.index);
			        patriarch = workbook.getSheet(workbook.getSheetName(workbook.getNumberOfSheets()-1)).createDrawingPatriarch(); // 声明一个画图的顶级管理器
				}
				ex.exportExcelAppend(dataset, "yyyy-MM-dd", workbook,style2,patriarch,font3);
			}
			OutputStream outputStream = response.getOutputStream();
			workbook.write(outputStream);
			outputStream.flush();
			outputStream.close();
		 } catch(Exception e) {
			 LogUtil.error(LOGGER, "excel导出失败！ error:{}",e);
		 } 
	}
}