package com.tensquare.base.pojo;

import javax.persistence.*;
import java.io.Serializable;

/**
 * 标签的实体类
 * @author 黑马程序员
 * @Company http://www.ithiema.com
 */
@Entity
@Table(name="tb_label")
public class Label implements Serializable {

    @Id
    private String id;// ID 文本

    private String labelname;// 标签名称 文本  Field   PropertyDescriptor(属性描述器)
    private String state;// 状态 文本            0：无效 1：有效
    private Integer count;// 使用数量 整型
    private Integer fans;// 关注数    整型
    private String recommend;// 是否推荐  文本    0：不推荐 1:推荐

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getLabelname() {
        return labelname;
    }

    public void setLabelname(String labelname) {
        this.labelname = labelname;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }

    public Integer getFans() {
        return fans;
    }

    public void setFans(Integer fans) {
        this.fans = fans;
    }

    public String getRecommend() {
        return recommend;
    }

    public void setRecommend(String recommend) {
        this.recommend = recommend;
    }

    @Override
    public String toString() {
        return "Label{" +
                "id='" + id + '\'' +
                ", labelname='" + labelname + '\'' +
                ", state='" + state + '\'' +
                ", count=" + count +
                ", fans=" + fans +
                ", recommend='" + recommend + '\'' +
                '}';
    }
}
