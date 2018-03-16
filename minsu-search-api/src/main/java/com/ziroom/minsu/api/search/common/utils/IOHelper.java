package com.ziroom.minsu.api.search.common.utils;

import com.asura.framework.base.util.Check;
import com.asura.framework.utils.LogUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.Reader;

/**
 * <p>IO 操作辅助类</p>
 * <p/>
 * <PRE>
 * <BR>	修改记录
 * <BR>-----------------------------------------------
 * <BR>	修改日期			修改人			修改内容
 * </PRE>
 *
 * @author afi on 2016/4/15.
 * @version 1.0
 * @since 1.0
 */
public final class IOHelper {

    private static final Logger LOGGER = LoggerFactory.getLogger(IOHelper.class);

    /**
     * 关闭输出流
     *
     * @param ops
     */
    public static void closeOutput(final OutputStream ops) {
        if (!Check.NuNObj(ops)) {
            try {
                ops.flush();
                ops.close();
            } catch (final IOException ignore) {
            }
        }
    }

    /**
     * 关闭输入流
     *
     * @param ips
     */
    public static void closeInput(final InputStream ips) {
        if (!Check.NuNObj(ips)) {
            try {
                ips.close();
            } catch (final IOException ignore) {
            }
        }
    }

    /**
     * 关闭读取流
     *
     * @param reader
     */
    public static void closeReader(final Reader reader) {
        if (!Check.NuNObj(reader)) {
            try {
                reader.close();
            } catch (final IOException ignore) {
            }
        }
    }

    /**
     * 拷贝流
     *
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
            LogUtil.error(LOGGER,"e:{}",ignore);
        }
    }

    private IOHelper() {
        throw new AssertionError("Uninstantiable class");
    }
}
