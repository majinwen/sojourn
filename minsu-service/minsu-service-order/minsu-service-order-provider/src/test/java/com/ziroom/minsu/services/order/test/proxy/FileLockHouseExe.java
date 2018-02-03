package com.ziroom.minsu.services.order.test.proxy;

import com.asura.framework.base.exception.BusinessException;
import com.asura.framework.base.util.DateUtil;
import com.asura.framework.base.util.UUIDGenerator;
import com.asura.framework.utils.LogUtil;
import com.ziroom.minsu.services.common.utils.DateSplitUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class FileLockHouseExe {
        /**
         * 日志对象
         */
        private static Logger logger = LoggerFactory.getLogger(FileExe.class);

        public static String readHouseTxt() throws ParseException {
            String insert = "insert into `t_base_house_lock` (`fid`, `house_fid`,`rent_way`, `lock_time`, `lock_type`) ";

            File file = new File("F:\\lock.txt");
            BufferedReader reader = null;
            try {
                reader=new BufferedReader(new InputStreamReader(new FileInputStream(file),"gbk"));
                String tempString = null;
                StringBuffer sb = new StringBuffer();
                Date startDate  = DateUtil.parseDate("2017-04-01","yyyy-MM-dd");
                Date endDate = DateUtil.parseDate("2017-04-06","yyyy-MM-dd");
                List<Date> dateSplitFullDaySet = dateSplit(startDate, endDate);
                while ((tempString = reader.readLine()) != null) {
                    System.err.println(tempString);
                    for (Date time : dateSplitFullDaySet){
                        String timeStr = DateUtil.dateFormat(time,"yyyy-MM-dd HH:mm:ss");
                        String value = "VALUES('"+ UUIDGenerator.hexUUID()+"','" + tempString +"','0','"+timeStr+"','2');";
                        System.out.println(insert + " " + value);
                    }

                }

                System.out.println(insert);
                reader.close();

            } catch (IOException e) {
                LogUtil.error(logger, "e={}", e);
            } finally {
                if (reader != null) {
                    try {
                        reader.close();
                    } catch (IOException e1) {
                        LogUtil.error(logger, "e={}", e1);
                    }
                }
            }
            return null;
        }


    public static String rendRoomText() throws ParseException {
        String insert = "insert into `t_base_house_lock` (`fid`, `house_fid`,`room_fid`,`rent_way`, `lock_time`, `lock_type`) ";

        File file = new File("F:\\lock.txt");
        BufferedReader reader = null;
        try {
            reader=new BufferedReader(new InputStreamReader(new FileInputStream(file),"gbk"));
            String tempString = null;
            StringBuffer sb = new StringBuffer();
            Date startDate  = DateUtil.parseDate("2017-04-01","yyyy-MM-dd");
            Date endDate = DateUtil.parseDate("2017-04-01","yyyy-MM-dd");
            List<Date> dateSplitFullDaySet = dateSplit(startDate, endDate);
            while ((tempString = reader.readLine()) != null) {
                String[] arr = tempString.split(" ");
                for (Date time : dateSplitFullDaySet){
                    String timeStr = DateUtil.dateFormat(time,"yyyy-MM-dd HH:mm:ss");
                    String value = "VALUES('"+ UUIDGenerator.hexUUID()+"','" + arr[0] +"','" + arr[1] +"','1','"+timeStr+"','2');";
                    System.out.println(insert + " " + value);
                }

            }

            System.out.println(insert);
            reader.close();

        } catch (IOException e) {
            LogUtil.error(logger, "e={}", e);
        } finally {
            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException e1) {
                    LogUtil.error(logger, "e={}", e1);
                }
            }
        }
        return null;
    }


    public static List<Date> dateSplit(Date startDate, Date endDate) {
        if (endDate.before(startDate)) throw new BusinessException("开始时间应该在结束时间之后");
        List<Date> dateList = new ArrayList<Date>();
        dateList.add(startDate);
        Date tomorrow = DateSplitUtil.getTomorrow(startDate);
        while (DateSplitUtil.transDateTime2Date(tomorrow).before(DateSplitUtil.transDateTime2Date(endDate)) || DateSplitUtil.transDateTime2Date(tomorrow).equals(DateSplitUtil.transDateTime2Date(endDate))){
            dateList.add(tomorrow);
            tomorrow = DateSplitUtil.getTomorrow(tomorrow);
        }
        List<Date> rst = new ArrayList<Date>();
        for(int i=dateList.size()-1;i>=0;i--){
            rst.add(dateList.get(i));
        }
        return rst;
    }

        /**
         * insert into `t_base_house_lock` (`fid`, `house_fid`,`rent_way`, `lock_time`, `lock_type`) VALUES('8a9e988b5a925c19015a925c19b00000','8a90997756fae5320156fde855a20243','0','2017-03-03 00:00:00','2');
         * @param args
         */
        public static void main(String[] args) {
            try {
                readHouseTxt();
            } catch (ParseException e) {
                e.printStackTrace();
            }




        }

}
