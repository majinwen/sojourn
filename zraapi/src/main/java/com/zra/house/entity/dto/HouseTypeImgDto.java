package com.zra.house.entity.dto;

import io.swagger.annotations.ApiModelProperty;

import java.util.List;

/**
 * <p></p>
 * <p/>
 * <PRE>
 * <BR>	修改记录
 * <BR>-----------------------------------------------
 * <BR>	修改日期			修改人			修改内容
 * </PRE>
 *
 * @author renxw
 * @version 1.0
 * @date 2016/8/5 17:10
 * @since 1.0
 */
public class HouseTypeImgDto {

    @ApiModelProperty(value = "图片类型名称")
    private String imgTypeName;

    @ApiModelProperty(value = "图片路径列表")
    private List<String> imgPathList;

    public String getImgTypeName() {
        return imgTypeName;
    }

    public void setImgTypeName(String imgTypeName) {
        this.imgTypeName = imgTypeName;
    }

    public List<String> getImgPathList() {
        return imgPathList;
    }

    public void setImgPathList(List<String> imgPathList) {
        this.imgPathList = imgPathList;
    }
}
