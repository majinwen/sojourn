/**
 * @FileName: EncryptMessageConverter.java
 * @Package: com.ziroom.cleaning.common
 * @author sence
 * @created 9/8/2015 7:51 PM
 * <p/>
 * Copyright 2015 ziroom
 */
package com.ziroom.minsu.api.common.converter;

import com.asura.framework.base.util.Check;
import com.asura.framework.base.util.JsonEntityTransform;
import com.fasterxml.jackson.core.JsonEncoding;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.ziroom.minsu.api.common.constant.HeaderParamName;
import com.ziroom.minsu.api.common.dto.GatewayResponseDto;
import com.ziroom.minsu.api.common.dto.ResponseDto;
import com.ziroom.minsu.api.common.dto.ResponseSecurityDto;
import com.ziroom.minsu.api.common.encrypt.EncryptFactory;
import com.ziroom.minsu.api.common.encrypt.IEncrypt;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpOutputMessage;
import org.springframework.http.converter.HttpMessageNotWritableException;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.http.server.ServletServerHttpResponse;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.Collection;

/**
 * 
 * <p>TODO</p>
 * 
 * <PRE>
 * <BR>	修改记录
 * <BR>-----------------------------------------------
 * <BR>	修改日期			修改人			修改内容
 * </PRE>
 * 
 * @author bushujie
 * @since 1.0
 * @version 1.0
 */
public class EncryptMessageConverter extends MappingJackson2HttpMessageConverter {

    private final static Logger logger = LoggerFactory.getLogger(EncryptMessageConverter.class);
    /**
     * 密码类型：默认DES
     */
    private String encryptType="DES";


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
        ServletServerHttpResponse servletResp = (ServletServerHttpResponse) outputMessage;
        Collection<String> headers = servletResp.getServletResponse().getHeaderNames();
        for (String s : headers) {
            System.err.println("dddd=" + s);
        }
        if (!headers.contains(HeaderParamName.X_Forwarded_Ziroom)) {
            if (object instanceof ResponseSecurityDto) {
                ResponseSecurityDto responseSecurityDto = (ResponseSecurityDto) object;
                ResponseDto responseDto = responseSecurityDto.getResponseDto();
                if (responseSecurityDto.isSercurity()) {
                    byte[] bytes = encryptObject(outputMessage, responseDto);
                    outputMessage.getBody().write(bytes);
                } else {
                    super.writeInternal(responseDto, outputMessage);
                }
            } else {
                //加密
                byte[] bytes = encryptObject(outputMessage, object);
                outputMessage.getBody().write(bytes);
            }
        } else {
            if (object instanceof ResponseSecurityDto) {
                ResponseSecurityDto responseSecurityDto = (ResponseSecurityDto) object;
                ResponseDto responseDto = responseSecurityDto.getResponseDto();
                super.writeInternal(GatewayResponseDto.response2GatewayDto(responseDto), outputMessage);
            } else {
                //加密
                byte[] bytes = bytesObject(outputMessage, object);
                outputMessage.getBody().write(bytes);
            }
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
        String val = JsonEntityTransform.Object2Json(object);
        IEncrypt encrypt = EncryptFactory.createEncryption(encryptType);
        String encryptResult = encrypt.encrypt(val);
        return encryptResult.getBytes(encoding.getJavaName());
    }


    public byte[] bytesObject(HttpOutputMessage outputMessage, Object object) throws UnsupportedEncodingException {
        JsonEncoding encoding = getJsonEncoding(outputMessage.getHeaders().getContentType());
        String val = JsonEntityTransform.Object2Json(object);
        return val.getBytes(encoding.getJavaName());
    }

    public static void main(String[] args) {
        IEncrypt encrypt = EncryptFactory.createEncryption("DES");
        String encryptResult = encrypt.decrypt("b29f8942196125a5513afebbf051b09b8636262133d443304e616d188fb75b24d8a645bac1fbaf8294defbc7250b48777f1af18d492c79ab");
        System.out.println(encryptResult);

    }
}
