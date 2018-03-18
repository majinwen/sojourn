package com.zra.evaluate.entity.dto;

import java.util.List;

/**
 * Author: wangxm113
 * CreateDate: 2016/8/5.
 */
public class ChildrenOfHistoryDto {
    private String code;
    private String label;
    private String optionType;
    private List<OptionDto> options;
    private List<AnswersDto> answers;

    public List<AnswersDto> getAnswers() {
        return answers;
    }

    public void setAnswers(List<AnswersDto> answers) {
        this.answers = answers;
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

    public String getOptionType() {
        return optionType;
    }

    public void setOptionType(String optionType) {
        this.optionType = optionType;
    }

    public List<OptionDto> getOptions() {
        return options;
    }

    public void setOptions(List<OptionDto> options) {
        this.options = options;
    }
}
