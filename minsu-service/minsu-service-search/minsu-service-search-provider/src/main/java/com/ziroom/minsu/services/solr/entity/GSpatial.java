package com.ziroom.minsu.services.solr.entity;

import org.apache.solr.client.solrj.beans.Field;

import java.io.Serializable;

/**
 * <p>GSpatial</p>
 * <p/>
 * <PRE>
 * <BR>	修改记录
 * <BR>-----------------------------------------------
 * <BR>	修改日期			修改人			修改内容
 * </PRE>
 *
 * @author afi on 2016/3/15.
 * @version 1.0
 * @since 1.0
 */
public class GSpatial implements Serializable {

    /**
     * 序列化id
     */
    private static final long serialVersionUID = 7527873987270618533L;

    @Field
    private String id;
    @Field
    private double longitude;
    @Field
    private double latitude;
    @Field
    private String name;
    @Field
    private String store;

    @Field
    private String text = "text";

    public GSpatial() {

    }


    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public GSpatial(String id, double latitude, double longitude, String name, String store) {
        this.id = id;
        this.longitude = longitude;
        this.latitude = latitude;
        this.name = name;
        this.store = store;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getStore() {
        return store;
    }

    public void setStore(String store) {
        this.store = store;
    }




}
