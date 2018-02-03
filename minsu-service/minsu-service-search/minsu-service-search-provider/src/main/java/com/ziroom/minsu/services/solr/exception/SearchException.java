package com.ziroom.minsu.services.solr.exception;

/**
 * <p>搜索异常</p>
 * <p/>
 * <PRE>
 * <BR>	修改记录
 * <BR>-----------------------------------------------
 * <BR>	修改日期			修改人			修改内容
 * </PRE>
 *
 * @author afi on 2016/3/16.
 * @version 1.0
 * @since 1.0
 */
public class SearchException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    public SearchException(String message) {
        super(message);
    }

    public SearchException(Throwable cause) {
        super(cause);
    }

    public SearchException(String message, Throwable cause) {
        super(message, cause);
    }
}
