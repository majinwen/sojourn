package com.ziroom.minsu.services.cms.entity;

import com.ziroom.minsu.entity.cms.ColumnCityEntity;

import java.util.ArrayList;
import java.util.List;

/**
 * 查询专栏以及专栏主图对应的路径Vo
 * Created with IntelliJ IDEA
 * User: lunan
 * Date: 2016/11/17
 */
public class FileRegionsVo extends ColumnCityEntity {

    /**
     * 序列化id
     */
    private static final long serialVersionUID = -2331573198939397987L;

    private List<ColumnRegionPicVo> regionList = new ArrayList<ColumnRegionPicVo>();

    public List<ColumnRegionPicVo> getRegionList() {
        return regionList;
    }

    public void setRegionList(List<ColumnRegionPicVo> regionList) {
        this.regionList = regionList;
    }
}
