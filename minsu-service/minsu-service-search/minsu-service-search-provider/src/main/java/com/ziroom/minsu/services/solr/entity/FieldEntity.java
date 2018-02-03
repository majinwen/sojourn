package com.ziroom.minsu.services.solr.entity;

import com.asura.framework.base.entity.BaseEntity;
import org.apache.solr.client.solrj.beans.Field;

import java.util.Set;

/**
 * <p>FieldEntity</p>
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
public class FieldEntity extends BaseEntity{

    /**
     * 序列化id
     */
    private static final long serialVersionUID = 7527873987270618533L;

    @Field
    private int id;
    @Field
    private String name;
    @Field
    private double price;
    @Field
    private Set<String>  text;
    @Field
    private double latitude;
    @Field
    private double longitude;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public Set<String> getText() {
        return text;
    }

    public void setText(Set<String> text) {
        this.text = text;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }
}
