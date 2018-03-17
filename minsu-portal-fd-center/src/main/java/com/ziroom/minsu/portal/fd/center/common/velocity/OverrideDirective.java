package com.ziroom.minsu.portal.fd.center.common.velocity;

import java.io.IOException;
import java.io.Writer;

import org.apache.velocity.context.InternalContextAdapter;
import org.apache.velocity.exception.MethodInvocationException;
import org.apache.velocity.exception.ParseErrorException;
import org.apache.velocity.exception.ResourceNotFoundException;
import org.apache.velocity.runtime.directive.Directive;
import org.apache.velocity.runtime.parser.node.Node;


/**
 * 
 * <p>自定义#override 作用：覆盖#block </p>
 * @author yd
 * @date 2016-03-24 23:20
 * @version 1.0
 *
 */
public class OverrideDirective extends Directive{

	@Override
	public String getName() {
		return "override";
	}

	@Override
	public int getType() {
		return BLOCK;
	}

	@Override
	public boolean render(InternalContextAdapter context, Writer writer, Node node)
			throws IOException, ResourceNotFoundException, ParseErrorException,MethodInvocationException {
		
		String name = VelocityUtils.getRequiredArgument(context, node, 0,getName());
		OverrideNodeWrapper override = (OverrideNodeWrapper)context.get(VelocityUtils.getOverrideVariableName(name));
        if(override == null) {
    		Node body = node.jjtGetChild(1);
        	context.put(VelocityUtils.getOverrideVariableName(name), new OverrideNodeWrapper(body));
        }else {
        	OverrideNodeWrapper current = new OverrideNodeWrapper(node.jjtGetChild(1));
        	VelocityUtils.setParentForTop(current,override);
        }
        return true;
	}

//	private boolean isOverrided(InternalContextAdapter context,String name) {
//		return context.get(Utils.getOverrideVariableName(name)) != null;
//	}
	
//	public static class OverrideNodeWrapper extends SimpleNode {
//		Node current;
//		OverrideNodeWrapper parentNode;
//		public OverrideNodeWrapper(Node node) {
//			super(1);
//			this.current = node;
//		}
//		public boolean render(InternalContextAdapter context, Writer writer)
//				throws IOException, MethodInvocationException,
//				ParseErrorException, ResourceNotFoundException {
//			OverrideNodeWrapper preNode = (OverrideNodeWrapper)context.get(VelocityUtils.OVERRIDE_CURRENT_NODE);
//			try {
//				context.put(VelocityUtils.OVERRIDE_CURRENT_NODE,this);
//				return current.render(context, writer);
//			}finally {
//				context.put(VelocityUtils.OVERRIDE_CURRENT_NODE,preNode);
//			}
//		}
//	}

}
