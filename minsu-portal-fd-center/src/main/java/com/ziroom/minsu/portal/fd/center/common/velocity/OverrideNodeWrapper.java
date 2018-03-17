package com.ziroom.minsu.portal.fd.center.common.velocity;

import org.apache.velocity.context.InternalContextAdapter;
import org.apache.velocity.exception.MethodInvocationException;
import org.apache.velocity.exception.ParseErrorException;
import org.apache.velocity.exception.ResourceNotFoundException;
import org.apache.velocity.runtime.parser.node.Node;


import java.io.IOException;
import java.io.Writer;

/**
 * 作用：To change this template use File | Settings | File Templates.
 * @author yd
 * @date 2016-3-23 下午
 * @version 1.0
 *
 */

public class OverrideNodeWrapper {

    Node current;
    OverrideNodeWrapper parentNode;
    public OverrideNodeWrapper(Node node) {
        super();
        this.current = node;
    }
    public boolean render(InternalContextAdapter context, Writer writer)
            throws IOException, MethodInvocationException,
            ParseErrorException, ResourceNotFoundException {
        OverrideNodeWrapper preNode = (OverrideNodeWrapper)context.get(VelocityUtils.OVERRIDE_CURRENT_NODE);
        try {
            context.put(VelocityUtils.OVERRIDE_CURRENT_NODE,this);
            return current.render(context, writer);
        }finally {
            context.put(VelocityUtils.OVERRIDE_CURRENT_NODE,preNode);
        }
    }
}
