package com.ziroom.zrp.service.trading.utils.builder;


import com.asura.framework.base.entity.DataTransferObject;
import org.codehaus.plexus.util.cli.shell.CmdShell;

/**
 * <p>DataTransferObject 建造者</p>
 * <p>
 * <PRE>
 * <BR>    修改记录
 * <BR>-----------------------------------------------
 * <BR>    修改日期         修改人          修改内容
 * </PRE>
 *
 * @author cuiyh9
 * @version 1.0
 * @Date Created in 2017年10月13日 15:10
 * @since 1.0
 */
public class DataTransferObjectBuilder {

    private  DataTransferObjectBuilder() {

    }



    /**
     * 返回错误json
     * @author cuiyuhui
     * @created
     * @param
     * @return
     */
    public static String buildErrorJsonStr(String message) {
        return buildError(message).toJsonString();
    }

    /**
     * 返回错误dto
     * @author cuiyuhui
     * @created
     * @param
     * @return
     */
    public static DataTransferObject buildError(String message) {
        DataTransferObject dto =  new DataTransferObject();
        dto.setErrCode(DataTransferObject.ERROR);
        dto.setMsg(message);
        return dto;
    }

    /**
     * 返回成功dto
     * @author cuiyuhui
     * @created
     * @param
     * @return
     */
    public static String buildOkJsonStr(Object obj) {
        return buildOkJsonStr("data", obj);
    }

    /**
     * 返回成功dto string
     * @author cuiyuhui
     * @created
     * @param
     * @return
     */
    public static  String buildOkJsonStr(String key, Object obj) {
        return buildOk(key, obj).toJsonString();
    }

    /**
     * 返回成功dto
     * @author cuiyuhui
     * @created
     * @param
     * @return
     */
    public static  DataTransferObject buildOk(String key, Object obj) {
        DataTransferObject dto = new DataTransferObject();
        dto.putValue(key, obj);
        return dto;
    }
    /**
     * 返回成功dto
     * @author cuiyuhui
     * @created
     * @param
     * @return
     */
    public static  DataTransferObject buildOk(String str) {
        return buildOk("data", str);
    }

}
