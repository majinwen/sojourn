package com.ziroom.minsu.activity.enums;

import com.asura.framework.base.util.Check;
import com.asura.framework.base.util.DateUtil;
import com.ziroom.minsu.services.common.utils.DateSplitUtil;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * <p></p>
 * <p>
 * <PRE>
 * <BR>	修改记录
 * <BR>-----------------------------------------------
 * <BR>	修改日期			修改人			修改内容
 * </PRE>
 *
 * @author lishaochuan on 2016/12/9 19:26
 * @version 1.0
 * @since 1.0
 */
public enum ChristmasDateEnum {

    DATE1("2016-12-12 00:00:00", "2016-12-21 14:00:00", "2016-12-21 14:00:00", "2016-12-24", "12月21日下午14:00"),

    DATE2("2016-12-21 14:00:00", "2016-12-22 14:00:00", "2016-12-22 14:00:00", "2016-12-25", "12月22日下午14:00"),

    DATE3("2016-12-22 14:00:00", "2016-12-26 14:00:00", "2016-12-28 14:00:00", "2016-12-31", "12月26日下午14:00"),

    DATE4("2016-12-26 14:00:00", "2016-12-28 14:00:00", "2016-12-29 14:00:00", "2016-01-01", "12月28日下午14:00"),

    DATE5("2016-12-28 14:00:00", "2016-12-30 14:00:00", "2016-12-30 14:00:00", "2016-01-02", "12月30日下午14:00");


    ChristmasDateEnum(String startTime, String endTime, String disableTime, String activityTime, String awardTime) {
        this.startTime = startTime;
        this.endTime = endTime;
        this.disableTime = disableTime;
        this.activityTime = activityTime;
        this.awardTime = awardTime;
    }


    /**
     * 每栏活动开始时间
     */
    String startTime;

    /**
     * 每栏活动结束时间
     */
    String endTime;

    /**
     * 置灰时间
     */
    String disableTime;

    /**
     * 活动时间
     */
    String activityTime;

    /**
     * 开奖时间
     */
    String awardTime;


    public void checkDate(String applyStrTime) throws Exception {
        Date applyTime = DateUtil.parseDate(applyStrTime, DateSplitUtil.dateFormatPattern);

    }


    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    public String getDisableTime() {
        return disableTime;
    }

    public void setDisableTime(String disableTime) {
        this.disableTime = disableTime;
    }

    public String getActivityTime() {
        return activityTime;
    }

    public void setActivityTime(String activityTime) {
        this.activityTime = activityTime;
    }

    public String getAwardTime() {
        return awardTime;
    }

    public void setAwardTime(String awardTime) {
        this.awardTime = awardTime;
    }


    /**
     * 检查参与活动的日期是否已经结束
     *
     * @param inputActivityDateStr
     * @return
     * @throws ParseException
     */
    public static boolean checkInputActivityDateStr(String inputActivityDateStr) throws ParseException {
        ChristmasDateEnum christmasDateEnum = null;
        for (ChristmasDateEnum cEnum : ChristmasDateEnum.values()) {
            if(cEnum.getActivityTime().equals(inputActivityDateStr)){
                christmasDateEnum = cEnum;
                break;
            }
        }
        if(Check.NuNObj(christmasDateEnum)){
            return false;
        }

        Date now = new Date();
        Date disableDate = DateUtil.parseDate(christmasDateEnum.getDisableTime(), DateSplitUtil.timestampPattern);
        if(now.after(disableDate)){
            return false;
        }
        return true;

        /*boolean flag = false;
        for (ChristmasDateEnum christmasDateEnum : ChristmasDateEnum.values()) {
            if(christmasDateEnum.getActivityTime().equals(inputActivityDateStr)){
                flag = true;
                break;
            }
        }
        if (!flag){
            return flag;
        }

        Date now = new Date();
        Date inputActivityDate = DateUtil.parseDate(inputActivityDateStr, DateSplitUtil.dateFormatPattern);
        for (ChristmasDateEnum christmasDateEnum : ChristmasDateEnum.values()) {
            Date startTime = DateUtil.parseDate(christmasDateEnum.getStartTime(), DateSplitUtil.timestampPattern);
            Date endTime = DateUtil.parseDate(christmasDateEnum.getEndTime(), DateSplitUtil.timestampPattern);
            if (now.after(startTime) && (now.before(endTime) || now.equals(endTime))) {
                Date activityDate = DateUtil.parseDate(christmasDateEnum.getActivityTime(), DateSplitUtil.dateFormatPattern);
                if (activityDate.equals(inputActivityDate) || activityDate.before(inputActivityDate)) {
                    return true;
                }
            }
        }
        return false;*/
    }

    /**
     * 根据参与活动日期获取开奖日期
     *
     * @param inputActivityDateStr
     * @return
     */
    public static String getAwardTime(String inputActivityDateStr) throws ParseException {
        for (ChristmasDateEnum christmasDateEnum : ChristmasDateEnum.values()) {
            if (christmasDateEnum.getActivityTime().equals(inputActivityDateStr)) {
                return christmasDateEnum.getAwardTime();
            }
        }
        return "";
    }


    public static void main(String[] args) throws ParseException {
        String str = getAwardTime("2016-01-01");
        System.err.println(str);
    }

}
