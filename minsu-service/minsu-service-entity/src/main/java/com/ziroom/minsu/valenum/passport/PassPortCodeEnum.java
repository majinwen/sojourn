package com.ziroom.minsu.valenum.passport;

/**
 * <p>调用用户信息的返回结果</p>
 * <p/>
 * <PRE>
 * <BR>	修改记录
 * <BR>-----------------------------------------------
 * <BR>	修改日期			修改人			修改内容
 * </PRE>
 *
 * @author afi on 2016/6/17.
 * @version 1.0
 * @since 1.0
 */
public enum  PassPortCodeEnum {

    NULL(11, "11","调用用户系统失败"),
    SUCCESS(1,"1", "成功"){
        @Override
        public boolean checkOk() {
            return true;
        }
    },
    TOKEN(0, "0","token失效"),
    USER_NO(-1,"-1", "无此uid");

    PassPortCodeEnum(int code, String str,String name) {
        this.code = code;
        this.name = name;
        this.str = str;
    }

    /**
     * 校验是否可用
     * @return
     */
    public boolean checkOk(){
        return false;
    }

    /**
     * 通过str获取信息
     * @param str
     * @return
     */
    public static PassPortCodeEnum getByStr(String str){
        for(final PassPortCodeEnum ose : PassPortCodeEnum.values()){
            if(ose.getStr().equals(str)){
                return ose;
            }
        }
        return NULL;
    }

    /** code */
    private int code;

    /** str */
    private String str;

    /** 名称 */
    private String name;

    public int getCode() {
        return code;
    }

    public String getName() {
        return name;
    }

    public String getStr() {
        return str;
    }
}
