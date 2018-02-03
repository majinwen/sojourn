package com.ziroom.minsu.services.basedata.entity;

import java.util.List;

/**
 * <p>含有二级标题的提示信息</p>
 * <p>
 * <PRE>
 * <BR>	修改记录
 * <BR>-----------------------------------------------
 * <BR>	修改日期			修改人			修改内容
 * </PRE>
 *
 * @author jixd
 * @version 1.0
 * @Date Created in 2017年06月22日 09:17
 * @since 1.0
 */
public class TipMsgHasSubTitleVo {
    /**
     * 主标题
     */
    private String title;

    /**
     * 字标题和内容
     */
    private List<SubTitleContent> subTitleContents;


    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public List<SubTitleContent> getSubTitleContents() {
        return subTitleContents;
    }

    public void setSubTitleContents(List<SubTitleContent> subTitleContents) {
        this.subTitleContents = subTitleContents;
    }

    /**
     * 二级标题内容
     * @author jixd
     * @created 2017年06月22日 10:04:49
     * @param
     * @return
     */
    public static class SubTitleContent{
        /**
         * 二级标题
         */
        private String subTitle;
        /**
         * 内容
         */
        private String subContent;

        public String getSubTitle() {
            return subTitle;
        }

        public void setSubTitle(String subTitle) {
            this.subTitle = subTitle;
        }

        public String getSubContent() {
            return subContent;
        }

        public void setSubContent(String subContent) {
            this.subContent = subContent;
        }
    }


}
