/**
 * @Description:
 * @Author:chengxiang.huang
 * @Date:2016年3月24日 下午2:24:14
 * @Version: V1.0
 */
package com.ziroom.minsu.services.solr.entity;

import java.util.Date;

import org.apache.solr.client.solrj.beans.Field;

/**
 * 
 * @Description:
 * @Author:chengxiang.huang 2016年3月24日
 * @CreateTime: 2016年3月24日 下午2:24:14
 * @Version 1.0
 */
public class Item {
	@Field
    private long id;
    @Field
    private String subject;
    @Field
    private String content;
    @Field("category_id")
    private long categoryId;
    @Field("category_name")
    private String categoryName;
    @Field("last_update_time")
    private Date lastUpdateTime;
    
    public long getId() {
        return id;
    }
    public void setId(long id) {
        this.id = id;
    }
    public String getSubject() {
        return subject;
    }
    public void setSubject(String subject) {
        this.subject = subject;
    }
    public String getContent() {
        return content;
    }
    public void setContent(String content) {
        this.content = content;
    }
    public long getCategoryId() {
        return categoryId;
    }
    public void setCategoryId(long categoryId) {
        this.categoryId = categoryId;
    }
    public String getCategoryName() {
        return categoryName;
    }
    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }
    public Date getLastUpdateTime() {
        return lastUpdateTime;
    }
    public void setLastUpdateTime(Date lastUpdateTime) {
        this.lastUpdateTime = lastUpdateTime;
    }
}
