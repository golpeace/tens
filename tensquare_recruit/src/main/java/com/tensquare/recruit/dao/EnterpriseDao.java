package com.tensquare.recruit.dao;

import com.tensquare.recruit.pojo.Enterprise;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
/**
 * 数据访问接口
 * @author Administrator
 *
 */
public interface EnterpriseDao extends JpaRepository<Enterprise,String>,JpaSpecificationExecutor<Enterprise>{

    /**
     * 热门企业列表
     * @param ishot
     * @return
     */
    Page<Enterprise> findByIshot(String ishot, Pageable pageable);
}
