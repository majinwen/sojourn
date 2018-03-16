package com.ziroom.minsu.api.search.common.converter;

import com.asura.framework.base.entity.DataTransferObject;
import com.asura.framework.base.util.Check;
import com.asura.framework.base.util.JsonEntityTransform;
import com.fasterxml.jackson.core.JsonEncoding;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.ziroom.minsu.api.search.common.dto.ResponseDto;
import com.ziroom.minsu.api.search.common.jsonp.JsonpVo;
import org.springframework.http.HttpOutputMessage;
import org.springframework.http.converter.HttpMessageNotWritableException;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;

import java.io.IOException;
import java.io.UnsupportedEncodingException;

/**
 * <p>返回结构的定义</p>
 * <p/>
 * <PRE>
 * <BR>	修改记录
 * <BR>-----------------------------------------------
 * <BR>	修改日期			修改人			修改内容
 * </PRE>
 *
 * @author afi on 2016/4/30.
 * @version 1.0
 * @since 1.0
 */
public class MessageConverter extends MappingJackson2HttpMessageConverter {


    /**
     * 这里继承了spring自有的MappingJackson2HttpMessageConverter
     * 拦截到需要输出的对象，转换成JSON字符串之后，进行加密操作
     *
     * @param object
     * @param outputMessage
     * @throws IOException
     * @throws HttpMessageNotWritableException
     */
    @Override
    protected void writeInternal(Object object, HttpOutputMessage outputMessage) throws IOException, HttpMessageNotWritableException {
        if (Check.NuNObj(object)) {
            super.writeInternal(object, outputMessage);
        }
        if(object instanceof DataTransferObject){
            //将对象返回
            ResponseDto responseDto = null;
            if(((DataTransferObject) object).getCode() == DataTransferObject.SUCCESS){
                responseDto = ResponseDto.responseOK(((DataTransferObject) object).getData());
            }else {
                responseDto = ResponseDto.responseFail(((DataTransferObject) object).getMsg());
            }
            //直接返回
            byte[] bytes = encryptObject(outputMessage,responseDto);
            outputMessage.getBody().write(bytes);
        }else{
            //直接返回
            byte[] bytes = encryptObject(outputMessage,object);
            outputMessage.getBody().write(bytes);
        }
    }

    /**
     * 输出对象加密
     * @param outputMessage
     * @param object
     * @return
     * @throws JsonProcessingException
     * @throws UnsupportedEncodingException
     */
    public byte[] encryptObject(HttpOutputMessage outputMessage,Object object) throws JsonProcessingException, UnsupportedEncodingException {
        JsonEncoding encoding = getJsonEncoding(outputMessage.getHeaders().getContentType());
        String val =null;
        if (object instanceof JsonpVo){
            ResponseDto responseDto = null;
            DataTransferObject dataTransferObject = ((JsonpVo) object).getDto();
            if (dataTransferObject.getCode() == DataTransferObject.SUCCESS){
                responseDto = ResponseDto.responseOK(dataTransferObject.getData());
            }else {
                responseDto = ResponseDto.responseFail(dataTransferObject.getMsg());
            }
            val = ((JsonpVo) object).getCallBack() + "(" + JsonEntityTransform.Object2Json(responseDto) + ")";
        }else {
            val = JsonEntityTransform.Object2Json(object);
        }
        return val.getBytes(encoding.getJavaName());
    }

}
