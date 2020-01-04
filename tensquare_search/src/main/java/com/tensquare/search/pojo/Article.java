package com.tensquare.search.pojo;

import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;

import java.io.Serializable;

/**
 * 文章的实体类，对应的是ElasticSearch
 * @author 黑马程序员
 * @Company http://www.ithiema.com
 *
 * 在elasticsearch 5.x以后的版本就没有string类型了，全都变成了text
 */
@Document(indexName = "tensquare",type ="article" )
public class Article implements Serializable {

    @Id
    private String id;

    @Field(index = true,store = true,analyzer = "ik_max_word",searchAnalyzer = "ik_max_word")
    private String title;
    @Field(index = true,store = true,analyzer = "ik_max_word",searchAnalyzer = "ik_max_word")
    private String content;

    private String state;

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

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }
}
