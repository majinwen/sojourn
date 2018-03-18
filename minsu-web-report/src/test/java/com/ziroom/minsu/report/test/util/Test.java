package com.ziroom.minsu.report.test.util;


import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.ziroom.minsu.report.common.util.ExportExcel;
import com.ziroom.minsu.report.order.entity.OrderEntity;

/**
 * <p>TODO</p>
 * <p/>
 * <PRE>
 * <BR>	修改记录
 * <BR>-----------------------------------------------
 * <BR>	修改日期			修改人			修改内容
 * </PRE>
 *
 * @author afi on 2016/8/6.
 * @version 1.0
 * @since 1.0
 */
public class Test {


    public static void main(String[] args) throws Exception{
        test1();
    }


    public static void test1() throws IOException {
        // 测试学生
        ExportExcel<OrderEntity> ex = new ExportExcel<OrderEntity>();
        String[] headers =
                { "学号", "姓名", "年龄", "性别", "出生日期" };
        List<OrderEntity> dataset = new ArrayList<OrderEntity>();
       /* dataset.add(new OrderEntity(10000001, "张三", 20, true, new Date()));
        dataset.add(new OrderEntity(20000002, "李四", 24, false, new Date()));
        dataset.add(new OrderEntity(30000003, "王五", 22, true, new Date()));*/
        try{
            /*ZipOutputStream out = new ZipOutputStream(new FileOutputStream("D:\\test.zip"), Charset.forName("GBK"));
            //实例化一个名称为ab.txt的ZipEntry对象
            ZipEntry entry = new ZipEntry("a.xls");
            //设置注释
            out.setComment("zip测试for单个文件");
            //把生成的ZipEntry对象加入到压缩文件中，而之后往压缩文件中写入的内容都会放在这个ZipEntry对象里面
            out.putNextEntry(entry);
            ex.exportExcel(headers, dataset, out);


            ZipEntry entry1 = new ZipEntry("b.xls");
            //设置注释
            out.setComment("zip测试for单个文件");
            //把生成的ZipEntry对象加入到压缩文件中，而之后往压缩文件中写入的内容都会放在这个ZipEntry对象里面
            out.putNextEntry(entry1);
            ex.exportExcel(headers, dataset, out);
            out.close();*/
        	FileOutputStream os = new FileOutputStream("D:\\workbook.xls");  
        	ex.exportExcel(headers, dataset, os);
        	os.close();  
        }catch(FileNotFoundException e){
            e.printStackTrace();
        }catch(IOException e) {
            e.printStackTrace();
        }


    }

}
