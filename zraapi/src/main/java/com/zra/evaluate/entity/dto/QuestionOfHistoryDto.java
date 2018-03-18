package com.zra.evaluate.entity.dto;

import java.util.List;

/**
 * Author: wangxm113
 * CreateDate: 2016/8/5.
 */
public class QuestionOfHistoryDto {
    private String code;
    private String index;
    private String label;
    private List<ChildrenOfHistoryDto> children;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getIndex() {
        return index;
    }

    public void setIndex(String index) {
        this.index = index;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public List<ChildrenOfHistoryDto> getChildren() {
        return children;
    }

    public void setChildren(List<ChildrenOfHistoryDto> children) {
        this.children = children;
    }
}
