package com.ziroom.zrp.service.trading.filter;

import com.alibaba.dubbo.common.Constants;
import com.alibaba.dubbo.common.extension.Activate;
import com.alibaba.dubbo.rpc.*;
import com.alibaba.dubbo.rpc.support.RpcUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * <p>去掉dubbo异步调用的传递性. 参考:http://blog.csdn.net/windrui/article/details/52150345</p>
 * <p>
 * <PRE>
 * <BR>    修改记录
 * <BR>-----------------------------------------------
 * <BR>    修改日期         修改人          修改内容
 * </PRE>
 *
 * @author cuiyh9
 * @version 1.0
 * @Date Created in 2017年11月21日 11:40
 * @since 1.0
 */
@Activate(group= {Constants.PROVIDER})
public class AsyncFilter implements Filter{

    private static final Logger LOGGER = LoggerFactory.getLogger(AsyncFilter.class);

    @Override
    public Result invoke(Invoker<?> invoker, Invocation invocation) throws RpcException {
        RpcContext.getContext().getAttachments().remove(Constants.ASYNC_KEY);
        Result result = invoker.invoke(invocation);
        return result;
    }
}
