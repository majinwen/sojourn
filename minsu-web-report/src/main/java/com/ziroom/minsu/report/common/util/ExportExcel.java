package com.ziroom.minsu.report.common.util;


import com.asura.framework.base.util.Check;
import com.asura.framework.utils.LogUtil;
import com.ziroom.minsu.report.common.annotation.FieldMeta;
import org.apache.poi.hssf.usermodel.*;
import org.apache.poi.hssf.util.HSSFColor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.OutputStream;
import java.lang.reflect.Field;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * <p>导出excel ExportExcel</p>
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
public class ExportExcel<T> {
	/**
	 * 日志对象
	 */
	private static final Logger LOGGER = LoggerFactory.getLogger(ExportExcel.class);

    public void exportExcel(List<T> dataset, OutputStream out){
        exportExcel("民宿报表", null, dataset, out, "yyyy-MM-dd");
    }

    public void exportExcel(String[] headers, List<T> dataset,
                            OutputStream out){
        exportExcel("民宿报表", headers, dataset, out, "yyyy-MM-dd");
    }

    public void exportExcel(String[] headers, List<T> dataset,
                            OutputStream out, String pattern){
        exportExcel("民宿报表", headers, dataset, out, pattern);
    }

    /**
     * 这是一个通用的方法，利用了JAVA的反射机制，可以将放置在JAVA集合中并且符号一定条件的数据以EXCEL 的形式输出到指定IO设备上
     * @param title 表格标题名
     * @param headers 表格属性列名数组
     * @param dataset 需要显示的数据集合,集合中一定要放置符合javabean风格的类的对象。此方法支持的
     * javabean属性的数据类型有基本数据类型及String,Date,byte[](图片数据)
     * @param out 与输出设备关联的流对象，可以将EXCEL文档导出到本地文件或者网络中
     * @param pattern  如果有时间数据，设定输出格式。默认为"yyy-MM-dd"
     */
    @SuppressWarnings("resource")
    public void exportExcel(String title, String[] headers,
                            List<T> dataset, OutputStream out, String pattern){
        
        HSSFWorkbook workbook = new HSSFWorkbook();// 声明一个工作薄
        HSSFSheet sheet = workbook.createSheet(title);// 生成一个表格
        sheet.setDefaultColumnWidth(15);// 设置表格默认列宽度为15个字节
        sheet.setDefaultRowHeight((short) 800);
        HSSFCellStyle style = workbook.createCellStyle();// 生成一个样式
        // 设置这些样式
        style.setFillForegroundColor(HSSFColor.SKY_BLUE.index);
        style.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
        style.setBorderBottom(HSSFCellStyle.BORDER_THIN);
        style.setBorderLeft(HSSFCellStyle.BORDER_THIN);
        style.setBorderRight(HSSFCellStyle.BORDER_THIN);
        style.setBorderTop(HSSFCellStyle.BORDER_THIN);
        style.setAlignment(HSSFCellStyle.ALIGN_CENTER);
        
        // 生成一个字体
        HSSFFont font = workbook.createFont();
        font.setColor(HSSFColor.VIOLET.index);
        font.setFontHeightInPoints((short) 12);
        font.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
        
        // 把字体应用到当前的样式
        style.setFont(font);
        style.setWrapText(true);
        // 生成并设置另一个样式
        HSSFCellStyle style2 = workbook.createCellStyle();
        style2.setBorderBottom(HSSFCellStyle.BORDER_THIN);
        style2.setBorderLeft(HSSFCellStyle.BORDER_THIN);
        style2.setBorderRight(HSSFCellStyle.BORDER_THIN);
        style2.setBorderTop(HSSFCellStyle.BORDER_THIN);
        style2.setAlignment(HSSFCellStyle.ALIGN_CENTER);
        style2.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);
        
        HSSFFont font2 = workbook.createFont();// 生成另一个字体
        font2.setBoldweight(HSSFFont.BOLDWEIGHT_NORMAL);
        style2.setFont(font2);// 把字体应用到当前的样式
        HSSFPatriarch patriarch = sheet.createDrawingPatriarch(); // 声明一个画图的顶级管理器
        HSSFComment comment = patriarch.createComment(new HSSFClientAnchor(0,
                0, 0, 0, (short) 4, 2, (short) 6, 5));// 定义注释的大小和位置,详见文档
        comment.setString(new HSSFRichTextString("可以在POI中添加注释！")); // 设置注释内容
        comment.setAuthor("minsu");// 设置注释作者，当鼠标移动到单元格上是可以在状态栏中看到该内容.
        HSSFCell cell = null;
        try {
        	// 产生表格标题行
            int index = 0;

            HSSFRow row = null;
            if(!Check.NuNObj(headers) && headers.length>0){
                row = sheet.createRow(index++);
                HSSFCellStyle style3 = workbook.createCellStyle();
                style3.cloneStyleFrom(style2);
                style3.setAlignment(HSSFCellStyle.ALIGN_LEFT);
                int c = 0;
                for (String header : headers) {
                    cell = row.createCell(c++);
                    cell.setCellValue(header);
                    cell.setCellStyle(style3);
                }
            }

            int columnIndex = 0;
            FieldMeta excel = null;
            // 写入标题
            row = sheet.createRow(index++);
			Field[] fieldsTitle = ReflectUtils.getClassFieldsAndSuperClassFields(dataset.get(0).getClass());
		    for (Field field : fieldsTitle){
	            field.setAccessible(true);
	            excel = field.getAnnotation(FieldMeta.class);
	            if (excel == null || excel.skip() == true) {
	                continue;
	            }
	            cell = row.createCell(columnIndex);
	            cell.setCellValue(excel.name());
	            cell.setCellStyle(style2);
	            columnIndex++;
	         }
	        
	        // 遍历集合数据，产生数据行
	        Iterator<T> it = dataset.iterator();
			HSSFFont font3 = workbook.createFont();
			font3.setColor(HSSFColor.BLUE.index);
	        while (it.hasNext()){
	            row = sheet.createRow(index++);
	            T t = it.next();
	            // 利用反射，得到属性值
	            Field[] fields = ReflectUtils.getClassFieldsAndSuperClassFields(t.getClass());
	            columnIndex = 0;
	            for (Field field : fields){
	            	//跳过不需要导出的列
	            	excel = field.getAnnotation(FieldMeta.class);
		            if (excel == null || excel.skip() == true) {
		                continue;
		            }
		            cell = row.createCell(columnIndex);
	                cell.setCellStyle(style2);
	                Object value =  ReflectUtils.getFieldValue(t,field.getName());//等价于 ;
	                
	                // 判断值的类型后进行强制类型转换
                    String textValue = null;
                    if (value instanceof Date){
                        Date date = (Date) value;
                        SimpleDateFormat sdf = new SimpleDateFormat(pattern);
                        textValue = sdf.format(date);
                    }else if (value instanceof byte[]){
                        row.setHeightInPoints(60);// 有图片时，设置行高为60px;
                        sheet.setColumnWidth(columnIndex, (short) (35.7 * 80));// 设置图片所在列宽度为80px,注意这里单位的一个换算
                        byte[] bsValue = (byte[]) value;
                        HSSFClientAnchor anchor = new HSSFClientAnchor(0, 0,1023, 255, (short) 6, index, (short) 6, index);
                        anchor.setAnchorType(2);
                        patriarch.createPicture(anchor, workbook.addPicture(bsValue, HSSFWorkbook.PICTURE_TYPE_JPEG));
                    }else{
                        // 其它数据类型都当作字符串简单处理
                    	if(Check.NuNObj(value)){
                    		textValue = "";
                    	}else{
                    		textValue = value.toString();	
                    	}
                        
                    }
                    // 如果不是图片数据，就利用正则表达式判断textValue是否全部由数字组成
                    if (textValue != null){
                        Pattern p = Pattern.compile("^//d+(//.//d+)?$");
                        Matcher matcher = p.matcher(textValue);
                        if (matcher.matches()){
                            cell.setCellValue(Double.parseDouble(textValue));// 是数字当作double处理
                        }else{
                            HSSFRichTextString richString = new HSSFRichTextString(textValue);
                            richString.applyFont(font3);
                            cell.setCellValue(richString);
                        }
                    }
                    columnIndex++;
	            }
	        }
        } catch (Exception e) {
        	LogUtil.error(LOGGER, "ExportExcel IOException error:{}",e);
		}
        
        try{
            workbook.write(out);
        }catch (IOException e){
        	LogUtil.error(LOGGER, "ExportExcel IOException error:{}",e);
        }
    }
    
    /**
     * 导出excel,生成多张工作表
     *
     * @author liujun
     * @created 2017年1月19日
     *
     * @param headers
     * @param dataMap
     * @param os
     */
    public void exportMultiSheetExcel(String[] headers, Map<String, List<? extends T>> dataMap, OutputStream out) {
    	exportMultiSheetExcel(headers, dataMap, out, "yyyy-MM-dd");
    }

	/**
	 * 导出excel,生成多张工作表
	 *
	 * @author liujun
	 * @created 2017年1月19日
	 *
	 * @param headers
	 * @param dataMap
	 * @param os
	 */
	public void exportMultiSheetExcel(String[] headers, Map<String, List<? extends T>> dataMap, OutputStream out, String pattern) {
		HSSFWorkbook workbook = new HSSFWorkbook();// 声明一个工作薄
		int num = 0;
		for (Entry<String, List<? extends T>> entry : dataMap.entrySet()) {
			num ++;
			String title = entry.getKey();
			if (Check.NuNStr(title)) {
				title = new StringBuilder("民宿报表-").append(num).toString();
			}
			List<? extends T> dataset = entry.getValue();
			createSheet(workbook, title, headers, dataset, out, pattern);
		}
		
		try{
			workbook.write(out);
		}catch (IOException e){
			LogUtil.error(LOGGER, "ExportExcel IOException error:{}",e);
		}
	}

	/**
	 * 生成工作表
	 *
	 * @author liujun
	 * @created 2017年1月19日
	 *
	 * @param title
	 * @param headers
	 * @param dataSet
	 * @param os
	 * @param pattern
	 */
	private void createSheet(HSSFWorkbook workbook, String title, String[] headers, List<? extends T> dataset, 
			OutputStream os, String pattern) {
		HSSFSheet sheet = workbook.createSheet(title);// 生成一个表格
		sheet.setDefaultColumnWidth(15);// 设置表格默认列宽度为15个字节
		sheet.setDefaultRowHeight((short) 800);
		HSSFCellStyle style = workbook.createCellStyle();// 生成一个样式
		// 设置这些样式
		style.setFillForegroundColor(HSSFColor.SKY_BLUE.index);
		style.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
		style.setBorderBottom(HSSFCellStyle.BORDER_THIN);
		style.setBorderLeft(HSSFCellStyle.BORDER_THIN);
		style.setBorderRight(HSSFCellStyle.BORDER_THIN);
		style.setBorderTop(HSSFCellStyle.BORDER_THIN);
		style.setAlignment(HSSFCellStyle.ALIGN_CENTER);

		// 生成一个字体
		HSSFFont font = workbook.createFont();
		font.setColor(HSSFColor.VIOLET.index);
		font.setFontHeightInPoints((short) 12);
		font.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);

		// 把字体应用到当前的样式
		style.setFont(font);
		style.setWrapText(true);
		// 生成并设置另一个样式
		HSSFCellStyle style2 = workbook.createCellStyle();
		style2.setBorderBottom(HSSFCellStyle.BORDER_THIN);
		style2.setBorderLeft(HSSFCellStyle.BORDER_THIN);
		style2.setBorderRight(HSSFCellStyle.BORDER_THIN);
		style2.setBorderTop(HSSFCellStyle.BORDER_THIN);
		style2.setAlignment(HSSFCellStyle.ALIGN_CENTER);
		style2.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);

		HSSFFont font2 = workbook.createFont();// 生成另一个字体
		font2.setBoldweight(HSSFFont.BOLDWEIGHT_NORMAL);
		style2.setFont(font2);// 把字体应用到当前的样式
		HSSFPatriarch patriarch = sheet.createDrawingPatriarch(); // 声明一个画图的顶级管理器
		HSSFComment comment = patriarch.createComment(new HSSFClientAnchor(0, 0, 0, 0, (short) 4, 2, (short) 6, 5));// 定义注释的大小和位置,详见文档
		comment.setString(new HSSFRichTextString("可以在POI中添加注释！")); // 设置注释内容
		comment.setAuthor("minsu");// 设置注释作者，当鼠标移动到单元格上是可以在状态栏中看到该内容.
		HSSFCell cell = null;
		try {
			// 产生表格标题行
			int index = 0;

			HSSFRow row = null;
			if (!Check.NuNObj(headers) && headers.length > 0) {
				row = sheet.createRow(index++);
				HSSFCellStyle style3 = workbook.createCellStyle();
				style3.cloneStyleFrom(style2);
				style3.setAlignment(HSSFCellStyle.ALIGN_LEFT);
				int c = 0;
				for (String header : headers) {
					cell = row.createCell(c++);
					cell.setCellValue(header);
					cell.setCellStyle(style3);
				}
			}

			int columnIndex = 0;
			FieldMeta excel = null;
			// 写入标题
			row = sheet.createRow(index++);
			Field[] fieldsTitle = ReflectUtils.getClassFieldsAndSuperClassFields(dataset.get(0).getClass());
			for (Field field : fieldsTitle) {
				field.setAccessible(true);
				excel = field.getAnnotation(FieldMeta.class);
				if (excel == null || excel.skip() == true) {
					continue;
				}
				cell = row.createCell(columnIndex);
				cell.setCellValue(excel.name());
				cell.setCellStyle(style2);
				columnIndex++;
			}

			// 遍历集合数据，产生数据行
			Iterator<? extends T> it = dataset.iterator();
			HSSFFont font3 = workbook.createFont();
			font3.setColor(HSSFColor.BLUE.index);
			while (it.hasNext()) {
				row = sheet.createRow(index++);
				T t = it.next();
				// 利用反射，得到属性值
				Field[] fields = ReflectUtils.getClassFieldsAndSuperClassFields(t.getClass());
				columnIndex = 0;
				for (Field field : fields) {
					// 跳过不需要导出的列
					excel = field.getAnnotation(FieldMeta.class);
					if (excel == null || excel.skip() == true) {
						continue;
					}
					cell = row.createCell(columnIndex);
					cell.setCellStyle(style2);
					Object value = ReflectUtils.getFieldValue(t, field.getName());// 等价于
																					// ;

					// 判断值的类型后进行强制类型转换
					String textValue = null;
					if (value instanceof Date) {
						Date date = (Date) value;
						SimpleDateFormat sdf = new SimpleDateFormat(pattern);
						textValue = sdf.format(date);
					} else if (value instanceof byte[]) {
						row.setHeightInPoints(60);// 有图片时，设置行高为60px;
						sheet.setColumnWidth(columnIndex, (short) (35.7 * 80));// 设置图片所在列宽度为80px,注意这里单位的一个换算
						byte[] bsValue = (byte[]) value;
						HSSFClientAnchor anchor = new HSSFClientAnchor(0, 0, 1023, 255, (short) 6, index, (short) 6, index);
						anchor.setAnchorType(2);
						patriarch.createPicture(anchor, workbook.addPicture(bsValue, HSSFWorkbook.PICTURE_TYPE_JPEG));
					} else {
						// 其它数据类型都当作字符串简单处理
						if (Check.NuNObj(value)) {
							textValue = "";
						} else {
							textValue = value.toString();
						}

					}
					// 如果不是图片数据，就利用正则表达式判断textValue是否全部由数字组成
					if (textValue != null) {
						Pattern p = Pattern.compile("^//d+(//.//d+)?$");
						Matcher matcher = p.matcher(textValue);
						if (matcher.matches()) {
							cell.setCellValue(Double.parseDouble(textValue));// 是数字当作double处理
						} else {
							HSSFRichTextString richString = new HSSFRichTextString(textValue);
							richString.applyFont(font3);
							cell.setCellValue(richString);
						}
					}
					columnIndex++;
				}
			}
		} catch (Exception e) {
			LogUtil.error(LOGGER, "ExportExcel IOException error:{}", e);
		}
	}
	
	/**
	 * 
	 * 生产表格excel  sheet
	 *
	 * @author bushujie
	 * @created 2017年6月6日 下午3:40:55
	 *
	 * @param title
	 * @param headers
	 * @param dataset
	 * @param workbook
	 */
	public HSSFWorkbook createWorkbook(String[] headers,List<T> dataset,String title){
        HSSFWorkbook workbook = new HSSFWorkbook();// 声明一个工作薄
        HSSFSheet sheet = workbook.createSheet(title);// 生成一个表格
        sheet.setDefaultColumnWidth(15);// 设置表格默认列宽度为15个字节
        sheet.setDefaultRowHeight((short) 800);
        HSSFCellStyle style = workbook.createCellStyle();// 生成一个样式
        // 设置这些样式
        style.setFillForegroundColor(HSSFColor.SKY_BLUE.index);
        style.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
        style.setBorderBottom(HSSFCellStyle.BORDER_THIN);
        style.setBorderLeft(HSSFCellStyle.BORDER_THIN);
        style.setBorderRight(HSSFCellStyle.BORDER_THIN);
        style.setBorderTop(HSSFCellStyle.BORDER_THIN);
        style.setAlignment(HSSFCellStyle.ALIGN_CENTER);
        
        // 生成一个字体
        HSSFFont font = workbook.createFont();
        font.setColor(HSSFColor.VIOLET.index);
        font.setFontHeightInPoints((short) 12);
        font.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
        
        // 把字体应用到当前的样式
        style.setFont(font);
        style.setWrapText(true);
        // 生成并设置另一个样式
        HSSFCellStyle style2 = workbook.createCellStyle();
        style2.setBorderBottom(HSSFCellStyle.BORDER_THIN);
        style2.setBorderLeft(HSSFCellStyle.BORDER_THIN);
        style2.setBorderRight(HSSFCellStyle.BORDER_THIN);
        style2.setBorderTop(HSSFCellStyle.BORDER_THIN);
        style2.setAlignment(HSSFCellStyle.ALIGN_CENTER);
        style2.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);
        
        HSSFFont font2 = workbook.createFont();// 生成另一个字体
        font2.setBoldweight(HSSFFont.BOLDWEIGHT_NORMAL);
        style2.setFont(font2);// 把字体应用到当前的样式
        HSSFPatriarch patriarch = sheet.createDrawingPatriarch(); // 声明一个画图的顶级管理器
        HSSFComment comment = patriarch.createComment(new HSSFClientAnchor(0,
                0, 0, 0, (short) 4, 2, (short) 6, 5));// 定义注释的大小和位置,详见文档
        comment.setString(new HSSFRichTextString("可以在POI中添加注释！")); // 设置注释内容
        comment.setAuthor("minsu");// 设置注释作者，当鼠标移动到单元格上是可以在状态栏中看到该内容.
        HSSFCell cell = null;
        try {
        	// 产生表格标题行
            int index = 0;

            HSSFRow row = null;
            if(!Check.NuNObj(headers) && headers.length>0){
                row = sheet.createRow(index++);
                HSSFCellStyle style3 = workbook.createCellStyle();
                style3.cloneStyleFrom(style2);
                style3.setAlignment(HSSFCellStyle.ALIGN_LEFT);
                int c = 0;
                for (String header : headers) {
                    cell = row.createCell(c++);
                    cell.setCellValue(header);
                    cell.setCellStyle(style3);
                }
            }

            int columnIndex = 0;
            FieldMeta excel = null;
            // 写入标题
            row = sheet.createRow(index++);
			Field[] fieldsTitle = ReflectUtils.getClassFieldsAndSuperClassFields(dataset.get(0).getClass());
		    for (Field field : fieldsTitle){
	            field.setAccessible(true);
	            excel = field.getAnnotation(FieldMeta.class);
	            if (excel == null || excel.skip() == true) {
	                continue;
	            }
	            cell = row.createCell(columnIndex);
	            cell.setCellValue(excel.name());
	            cell.setCellStyle(style2);
	            columnIndex++;
	        }
        } catch (Exception e) {
        	LogUtil.error(LOGGER, "ExportExcel IOException error:{}",e);
		}
        return workbook;
	}
	
	/**
	 * 
	 * 表格追加数据
	 *
	 * @author bushujie
	 * @created 2017年6月6日 下午4:52:47
	 *
	 * @param title
	 * @param dataset
	 * @param pattern
	 * @param workbook
	 */
    public void exportExcelAppend(List<T> dataset,String pattern,HSSFWorkbook workbook,HSSFCellStyle style2,HSSFPatriarch patriarch,HSSFFont font3){
        HSSFSheet sheet = workbook.getSheet(workbook.getSheetName(workbook.getNumberOfSheets()-1));// 获取最后一个sheet
        HSSFCell cell = null;
        FieldMeta excel = null;
        try {
        	HSSFRow row = null;
        	int index=sheet.getLastRowNum()+1;
	        // 遍历集合数据，产生数据行
	        Iterator<T> it = dataset.iterator();
	        while (it.hasNext()){
	            row = sheet.createRow(index++);
	            T t = it.next();
	            // 利用反射，得到属性值
	            Field[] fields = ReflectUtils.getClassFieldsAndSuperClassFields(t.getClass());
	            int columnIndex = 0;
	            for (Field field : fields){
	            	//跳过不需要导出的列
	            	excel = field.getAnnotation(FieldMeta.class);
		            if (excel == null || excel.skip() == true) {
		                continue;
		            }
		            cell = row.createCell(columnIndex);
	                cell.setCellStyle(style2);
	                Object value =  ReflectUtils.getFieldValue(t,field.getName());//等价于 ;
	                
	                // 判断值的类型后进行强制类型转换
                    String textValue = null;
                    if (value instanceof Date){
                        Date date = (Date) value;
                        SimpleDateFormat sdf = new SimpleDateFormat(pattern);
                        textValue = sdf.format(date);
                    }else if (value instanceof byte[]){
                        row.setHeightInPoints(60);// 有图片时，设置行高为60px;
                        sheet.setColumnWidth(columnIndex, (short) (35.7 * 80));// 设置图片所在列宽度为80px,注意这里单位的一个换算
                        byte[] bsValue = (byte[]) value;
                        HSSFClientAnchor anchor = new HSSFClientAnchor(0, 0,1023, 255, (short) 6, index, (short) 6, index);
                        anchor.setAnchorType(2);
                        patriarch.createPicture(anchor, workbook.addPicture(bsValue, HSSFWorkbook.PICTURE_TYPE_JPEG));
                    }else{
                        // 其它数据类型都当作字符串简单处理
                    	if(Check.NuNObj(value)){
                    		textValue = "";
                    	}else{
                    		textValue = value.toString();	
                    	}
                        
                    }
                    // 如果不是图片数据，就利用正则表达式判断textValue是否全部由数字组成
                    if (textValue != null){
                        Pattern p = Pattern.compile("^//d+(//.//d+)?$");
                        Matcher matcher = p.matcher(textValue);
                        if (matcher.matches()){
                            cell.setCellValue(Double.parseDouble(textValue));// 是数字当作double处理
                        }else{
                            HSSFRichTextString richString = new HSSFRichTextString(textValue);
                            richString.applyFont(font3);
                            cell.setCellValue(richString);
                        }
                    }
                    columnIndex++;
	            }
	        }
        } catch (Exception e) {
        	LogUtil.error(LOGGER, "ExportExcel IOException error:{}",e);
		}
    }
}
