package com.ziroom.minsu.services.common.entity;

/**
 * <p>标题描述 数据值展示</p>
 * <p>
 * <PRE>
 * <BR>	修改记录
 * <BR>-----------------------------------------------
 * <BR>	修改日期			修改人			修改内容
 * </PRE>
 *
 * @author jixd
 * @version 1.0
 * @Date Created in 2017年07月07日 19:07
 * @since 1.0
 */
public class FieldTitleTextValueVo<T> extends FieldBaseVo {

    /**
     * 文字描述
     */
    private String title;

    /**
     * 值
     */
    private T value;

    /**
     * 描述
     */
    private String text;

    public FieldTitleTextValueVo(String title, T value, String text, boolean isEdit) {
        this.title = title;
        this.value = value;
        this.text = text;
        super.setIsEdit(isEdit);
    }

    public FieldTitleTextValueVo(String title, String text, boolean isEdit) {
        this.title = title;
        this.text = text;
        super.setIsEdit(isEdit);
    }


    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public T getValue() {
        return value;
    }

    public void setValue(T value) {
        this.value = value;
    }
}
