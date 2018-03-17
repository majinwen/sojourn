package com.ziroom.minsu.portal.fd.center.common.velocity;

import java.io.IOException;
import java.io.Writer;
import java.util.ArrayList;
import java.util.List;

import org.apache.velocity.Template;
import org.apache.velocity.app.event.EventHandlerUtil;
import org.apache.velocity.context.InternalContextAdapter;
import org.apache.velocity.exception.MethodInvocationException;
import org.apache.velocity.exception.ParseErrorException;
import org.apache.velocity.exception.ResourceNotFoundException;
import org.apache.velocity.exception.TemplateInitException;
import org.apache.velocity.exception.VelocityException;
import org.apache.velocity.runtime.RuntimeConstants;
import org.apache.velocity.runtime.RuntimeServices;
import org.apache.velocity.runtime.directive.InputBase;
import org.apache.velocity.runtime.directive.StopCommand;
import org.apache.velocity.runtime.log.Log;
import org.apache.velocity.runtime.parser.node.Node;
import org.apache.velocity.runtime.parser.node.SimpleNode;

/**
 * 自定义#parse命令
 * 作用：继承页面
 * @author yd
 * @date 2016-03-24 23:20
 * @version 1.0
 *
 */
public class ExtendedParse extends InputBase {
		
	private static final String TEMPLATE_PREFIX = "/template/";
	
 	private int maxDepth;

    /**
     * Return name of this directive.
     * @return The name of this directive.
     */
    public String getName()
    {
        return "parse";
    }

    /**
     * Overrides the default to use "template", so that all templates
     * can use the same scope reference, whether rendered via #parse
     * or direct merge.
     */
    public String getScopeName()
    {
        return "template";
    }

    /**
     * Return type of this directive.
     * @return The type of this directive.
     */
    public int getType()
    {
        return LINE;
    }

    /**
     * Init's the #parse directive.
     * @param rs
     * @param context
     * @param node
     * @throws TemplateInitException
     */
    public void init(RuntimeServices rs, InternalContextAdapter context, Node node)
        throws TemplateInitException
    {
        super.init(rs, context, node);

        this.maxDepth = rsvc.getInt(RuntimeConstants.PARSE_DIRECTIVE_MAXDEPTH, 10);
    }

    /**
     *  iterates through the argument list and renders every
     *  argument that is appropriate.  Any non appropriate
     *  arguments are logged, but render() continues.
     * @param context
     * @param writer
     * @param node
     * @return True if the directive rendered successfully.
     * @throws IOException
     * @throws ResourceNotFoundException
     * @throws ParseErrorException
     * @throws MethodInvocationException
     */
    @SuppressWarnings({ "rawtypes", "unchecked" })
	public boolean render( InternalContextAdapter context,
                           Writer writer, Node node)
        throws IOException, ResourceNotFoundException, ParseErrorException,
               MethodInvocationException
    {
        /*
         *  did we get an argument?
         */
        if ( node.jjtGetNumChildren() == 0 )
        {
            throw new VelocityException("#parse(): argument missing at " +
                                        Log.formatFileString(this));
        }

        /*
         *  does it have a value?  If you have a null reference, then no.
         */
        Object value =  node.jjtGetChild(0).value( context );
        if (value == null && rsvc.getLog().isDebugEnabled())
        {
            rsvc.getLog().debug("#parse(): null argument at " +
                                Log.formatFileString(this));
        }

        /*
         *  get the path
         */
        String sourcearg = value == null ? null : value.toString();

        //检查是否是模板文件
        if(sourcearg!=null&&sourcearg.trim().length()>0&& sourcearg.startsWith(TEMPLATE_PREFIX)){
        	sourcearg = sourcearg.substring(1);
        }
        /*
         *  check to see if the argument will be changed by the event cartridge
         */
        String arg = EventHandlerUtil.includeEvent( rsvc, context, sourcearg, context.getCurrentTemplateName(), getName());

        /*
         *   a null return value from the event cartridge indicates we should not
         *   input a resource.
         */
        if (arg == null)
        {
            // abort early, but still consider it a successful rendering
            return true;
        }


        if (maxDepth > 0)
        {
            /* 
             * see if we have exceeded the configured depth.
             */
            Object[] templateStack = context.getTemplateNameStack();
            if (templateStack.length >= maxDepth)
            {
                StringBuffer path = new StringBuffer();
                for( int i = 0; i < templateStack.length; ++i)
                {
                    path.append( " > " + templateStack[i] );
                }
                rsvc.getLog().error("Max recursion depth reached (" +
                                    templateStack.length + ')' + " File stack:" +
                                    path);
                return false;
            }
        }

        /*
         *  now use the Runtime resource loader to get the template
         */

        Template t = null;

        try
        {
            t = rsvc.getTemplate( arg, getInputEncoding(context) );
        }
        catch ( ResourceNotFoundException rnfe )
        {
            /*
             * the arg wasn't found.  Note it and throw
             */
            rsvc.getLog().error("#parse(): cannot find template '" + arg +
                                "', called at " + Log.formatFileString(this));
            throw rnfe;
        }
        catch ( ParseErrorException pee )
        {
            /*
             * the arg was found, but didn't parse - syntax error
             *  note it and throw
             */
            rsvc.getLog().error("#parse(): syntax error in #parse()-ed template '"
                                + arg + "', called at " + Log.formatFileString(this));
            throw pee;
        }
        /**
         * pass through application level runtime exceptions
         */
        catch( RuntimeException e )
        {
            rsvc.getLog().error("Exception rendering #parse(" + arg + ") at " +
                                Log.formatFileString(this));
            throw e;
        }
        catch ( Exception e)
        {
            String msg = "Exception rendering #parse(" + arg + ") at " +
                         Log.formatFileString(this);
            rsvc.getLog().error(msg, e);
            throw new VelocityException(msg, e);
        }

        /**
         * Add the template name to the macro libraries list
         */
        List macroLibraries = context.getMacroLibraries();

        /**
         * if macroLibraries are not set create a new one
         */
        if (macroLibraries == null)
        {
            macroLibraries = new ArrayList();
        }

        context.setMacroLibraries(macroLibraries);

        macroLibraries.add(arg);

        /*
         *  and render it
         */
        try
        {
            preRender(context);
            context.pushCurrentTemplateName(arg);

            ((SimpleNode) t.getData()).render(context, writer);
        }
        catch( StopCommand stop )
        {
            if (!stop.isFor(this))
            {
                throw stop;
            }
        }
        /**
         * pass through application level runtime exceptions
         */
        catch( RuntimeException e )
        {
            /**
             * Log #parse errors so the user can track which file called which.
             */
            rsvc.getLog().error("Exception rendering #parse(" + arg + ") at " +
                                Log.formatFileString(this));
            throw e;
        }
        catch ( Exception e )
        {
            String msg = "Exception rendering #parse(" + arg + ") at " +
                         Log.formatFileString(this);
            rsvc.getLog().error(msg, e);
            throw new VelocityException(msg, e);
        }
        finally
        {
            context.popCurrentTemplateName();
            postRender(context);
        }

        /*
         *    note - a blocked input is still a successful operation as this is
         *    expected behavior.
         */

        return true;
    }

}
