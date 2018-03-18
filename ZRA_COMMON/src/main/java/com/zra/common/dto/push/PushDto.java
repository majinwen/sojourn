package com.zra.common.dto.push;

import java.util.ArrayList;
import java.util.List;

public class PushDto {

    /**
     *调用推送系统的ID.
     */
    private String systemId;
    
    /**
     * 调用推送系统的功能标识
     */
    private String funcFlag;

    /**
     * 推送标题.
     */
    private String title;
    
    /**
     * 推送内容.
     */
    private String content;
    
    /**
     * 跳转URL
     */
    private String openUrl;
    
    /**
     * 推送uid.
     */
    private List<String> uidList;
    
    
    

    public PushDto() {
        super();
    }
    
    public PushDto(String systemId, String funcFlag, String title, String content, String openUrl, String uid) {
        super();
        this.systemId = systemId;
        this.funcFlag = funcFlag;
        this.title = title;
        this.content = content;
        this.openUrl = openUrl;
        List<String> tempUidList = new ArrayList<String>();
        tempUidList.add(uid);
        this.uidList = tempUidList;
    }
    

    public String getSystemId() {
        return systemId;
    }

    public void setSystemId(String systemId) {
        this.systemId = systemId;
    }


    public String getFuncFlag() {
        return funcFlag;
    }

    public void setFuncFlag(String funcFlag) {
        this.funcFlag = funcFlag;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    
    public String getOpenUrl() {
        return openUrl;
    }

    public void setOpenUrl(String openUrl) {
        this.openUrl = openUrl;
    }

    public List<String> getUidList() {
        return uidList;
    }

    public void setUidList(List<String> uidList) {
        this.uidList = uidList;
    }
    
    
}
