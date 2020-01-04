package com.tensquare.recruit.dao;

import com.tensquare.recruit.pojo.Recruit;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.List;

/**
 * 数据访问接口
 * @author Administrator
 *
 */
public interface RecruitDao extends JpaRepository<Recruit,String>,JpaSpecificationExecutor<Recruit>{

    /**
     * 查询推荐职位信息，获取前4个 是按照录入是时间降序排序
     * @param state
     * @return
     */
    List<Recruit> findTop4ByStateOrderByCreatetimeDesc(String state);

    /**
     * 根据状态查询职位列表，获取前12个
     * @param state
     * @return
     */
    List<Recruit> findTop12ByStateNotOrderByCreatetimeDesc(String state);
}
