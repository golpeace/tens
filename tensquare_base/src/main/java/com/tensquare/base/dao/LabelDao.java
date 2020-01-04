package com.tensquare.base.dao;

import com.tensquare.base.pojo.Label;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

/**
 * 标签的持久层接口
 * @author 黑马程序员
 * @Company http://www.ithiema.com
 */
public interface LabelDao extends JpaRepository<Label,String>,JpaSpecificationExecutor<Label>{

    @Modifying
    @Query("update Label  set fans = ?2 where id = ?1 ")
    void updateLabelFans(String labelid,Integer fans);
}
