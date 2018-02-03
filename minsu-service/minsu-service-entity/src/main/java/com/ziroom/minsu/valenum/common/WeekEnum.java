package com.ziroom.minsu.valenum.common;

import com.asura.framework.base.util.Check;

import java.util.Calendar;
import java.util.Date;
/**
 * 星期枚举类
 * 
 * @author zl
 * @created 2016年9月9日
 *
 */
public enum WeekEnum {
	MONDAY("星期一", "Monday", "Mon.", 1),  
    TUESDAY("星期二", "Tuesday", "Tues.", 2),  
    WEDNESDAY("星期三", "Wednesday", "Wed.", 3),  
    THURSDAY("星期四", "Thursday", "Thur.", 4),  
    FRIDAY("星期五", "Friday", "Fri.", 5),  
    SATURDAY("星期六", "Saturday", "Sat.", 6),  
    SUNDAY("星期日", "Sunday", "Sun.", 7);  
      
    String name_cn;  
    String name_en;  
    String name_enShort;  
    int number;  
      
    WeekEnum(String name_cn, String name_en, String name_enShort, int number) {  
        this.name_cn = name_cn;  
        this.name_en = name_en;  
        this.name_enShort = name_enShort;  
        this.number = number;  
    }  
      
    public String getChineseName() {  
        return name_cn;  
    }  
  
    public String getName() {  
        return name_en;  
    }  
  
    public String getShortName() {  
        return name_enShort;  
    }  
  
    public int getNumber() {  
        return number;  
    }  
    
    public static WeekEnum getWeek(Date date) {  
    	WeekEnum week = null;
        if (Check.NuNObj(date)){
            return null;
        }
        Calendar calendar = Calendar.getInstance();  
        calendar.setTime(date);  
        int weekNumber = calendar.get(Calendar.DAY_OF_WEEK) - 1;  
        switch (weekNumber) {  
        case 0:  
            week = WeekEnum.SUNDAY;  
            break;  
        case 1:  
            week =WeekEnum.MONDAY;  
            break;  
        case 2:  
            week = WeekEnum.TUESDAY;  
            break;  
        case 3:  
            week = WeekEnum.WEDNESDAY;  
            break;  
        case 4:  
            week = WeekEnum.THURSDAY;  
            break;  
        case 5:  
            week = WeekEnum.FRIDAY;  
            break;  
        case 6:  
            week = WeekEnum.SATURDAY;  
            break;  
        }  
        return week;  
    }
    
}
