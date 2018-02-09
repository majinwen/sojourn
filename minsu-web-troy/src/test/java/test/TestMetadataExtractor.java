package test;


import com.alibaba.fastjson.JSONObject;
import com.asura.framework.base.util.Check;
import com.drew.imaging.ImageMetadataReader;
import com.drew.metadata.Directory;
import com.drew.metadata.Metadata;
import com.drew.metadata.jpeg.JpegDirectory;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.HttpClients;

import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class TestMetadataExtractor {

    private static final String PIC_BASE_ADDR = "http://image.ziroom.com/";

    private static final HttpClient httpClient = HttpClients.custom().build();


    public static void main(String[] args) {
//        TestMetadataExtractor.exif2();

////       String aa =  exif2("http://image.ziroom.com/g2/M00/33/D9/ChAFFViuS72AFtgtAACbGMQ4oPs377.jpg");
//
////        System.out.println(aa);
        String s = "jfldsjflsdajfja";
        s = s.substring(1);
        JSONObject jsonObject = new JSONObject();
        List<String> strings = new ArrayList<>();
        strings.add("fjkldsajfja");
        strings.add("fjkldsffdsfds");
        jsonObject.put("strings",strings);
        System.err.println(jsonObject.toJSONString());

        Map<String ,Object> paramMap = new HashMap<>();
        Object o = paramMap.get("o");
        paramMap.put("page",30);
        int i = ( paramMap.get("page")==null?10:(int) paramMap.get("page"));
        System.err.print(i);

            bbb();
    }


    public static String  bbb() {
        String fileName = "bb.txt";
        try {
            String path = Thread.currentThread().getContextClassLoader().getResource(fileName).getPath();
            File file=new File(path);
            if(file.isFile() && file.exists()) { //判断文件是否存在
                InputStreamReader read = new InputStreamReader(
                        new FileInputStream(file), "UTF8");//考虑到编码格式
                BufferedReader bufferedReader = new BufferedReader(read);
                String lineTxt = null;
                while ((lineTxt = bufferedReader.readLine()) != null) {

                    if (Check.NuNStr(lineTxt)){
                        continue;
                    }

                    String  tmp = lineTxt.substring(lineTxt.indexOf("-")+1);
                    String bbb = "INSERT INTO `t_emp` ( `info_url`) " +
                            "VALUES " +
                            "('" + tmp + "');";
                    System.out.println(bbb);

                }
                read.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";
    }




    public static String  aaa() {
        String fileName = "aa.txt";
        try {
            String path = Thread.currentThread().getContextClassLoader().getResource(fileName).getPath();
            File file=new File(path);
            if(file.isFile() && file.exists()) { //判断文件是否存在
                InputStreamReader read = new InputStreamReader(
                        new FileInputStream(file), "UTF8");//考虑到编码格式
                BufferedReader bufferedReader = new BufferedReader(read);
                String lineTxt = null;
                while ((lineTxt = bufferedReader.readLine()) != null) {
                    String  full = PIC_BASE_ADDR + lineTxt +"";

                    String out = exif2(full);
                    if (!Check.NuNStr(out)){
                        System.out.println(out);

//                        if (out.equals("500-500")){
//                            System.out.println(full);
//
//                        }

                    }
                }
                read.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";
    }


    public static String  exif2(String picUrl) {
//        System.out.println(picUrl);
        String  rst  = "";
//        String picUrl = "http://image.ziroom.com/g2/M00/00/2F/ChAFEVdOataAXrskAAEbIlO8goU540.jpg";
        HttpGet httpGet = new HttpGet(picUrl);
        HttpResponse response;
        HttpEntity entity;
        InputStream in = null;
        try {
            response = httpClient.execute(httpGet);
            entity = response.getEntity();
            in = entity.getContent();
            Metadata metadata = ImageMetadataReader.readMetadata(in);
            Directory jpegDirectory = null;
            int widthPixel = -1;
            int heightPixel  = -1;
            if(metadata.containsDirectoryOfType(JpegDirectory.class)){
                jpegDirectory = metadata.getFirstDirectoryOfType(JpegDirectory.class);
                widthPixel = Check.NuNStrStrict(jpegDirectory.getString(JpegDirectory.TAG_IMAGE_WIDTH))?-1:Integer.valueOf(jpegDirectory.getString(JpegDirectory.TAG_IMAGE_WIDTH) );
                heightPixel = Check.NuNStrStrict(jpegDirectory.getString(JpegDirectory.TAG_IMAGE_HEIGHT))?-1:Integer.valueOf(jpegDirectory.getString(JpegDirectory.TAG_IMAGE_HEIGHT));
            }
//            System.out.println(widthPixel + "-" + heightPixel);
            if (widthPixel > 0  && heightPixel>0){
                rst = widthPixel + "-" + heightPixel;
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (in != null) {
                    in.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return rst;
    }


}


