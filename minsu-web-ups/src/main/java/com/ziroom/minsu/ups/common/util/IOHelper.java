package com.ziroom.minsu.ups.common.util;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

/**
 * <p>流的操作类</p>
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
public class IOHelper {



    /**
     * 拷贝流
     * @author afi
     * @param ips
     * @param ops
     */
    public static void copyStream(final InputStream ips, final OutputStream ops) {
        final byte[] buf = new byte[10];
        int len;
        try {
            while ((len = ips.read(buf)) != -1) {
                ops.write(buf, 0, len);
            }
        } catch (final IOException ignore) {
            ignore.printStackTrace();
        }
    }

}
