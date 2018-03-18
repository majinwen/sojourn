package com.zra.evaluate.entity.dto;

import java.util.List;

/**
 * Author: wangxm113
 * CreateDate: 2016/8/5.
 */
public class GetQuestionQuestionDto {
    private String code;
    private String label;
    private String index;
    private List<ChildrenDto> children;

    public String getIndex() {
        return index;
    }

    public void setIndex(String index) {
        this.index = index;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public List<ChildrenDto> getChildren() {
        return children;
    }

    public void setChildren(List<ChildrenDto> children) {
        this.children = children;
    }
}
