package solr; /**
 * @FileName: Notice.java
 * @Package
 * @author lusp
 * @created 2017年8月15日 下午4:55:41
 * <p>
 * Copyright 2011-2015 asura
 */

import org.apache.solr.client.solrj.beans.Field;


public class Notice {
  @Field("id")
  private String id;
  @Field
  private String title;
  @Field
  private String subject;
  @Field
  private String description;

  @Field
  private String myfield;

  public String getMyfield() {
    return myfield;
  }

  public void setMyfield(String myfield) {
    this.myfield = myfield;
  }

  public String getId() {
    return id;
  }

  public void setId(String id) {
    this.id = id;
  }

  public String getTitle() {
    return title;
  }

  public void setTitle(String title) {
    this.title = title;
  }

  public String getSubject() {
    return subject;
  }

  public void setSubject(String subject) {
    this.subject = subject;
  }

  public String getDescription() {
    return description;
  }

  public void setDescription(String description) {
    this.description = description;
  }

  @Override
  public String toString() {
    return "Notice [id=" + id + ", title=" + title + ", subject=" + subject + ", description="
        + description + "]";
  }

}
