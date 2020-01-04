package com.tensquare.user.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import com.tensquare.user.pojo.User;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

/**
 * 数据访问接口
 * @author Administrator
 *
 */
public interface UserDao extends JpaRepository<User,String>,JpaSpecificationExecutor<User>{

    /**
     * 根据手机号查询用户
     * @param mobile
     * @return
     */
    User findByMobile(String mobile);

    /**
     * 更新用户关注数
     * @param userid        用户的id
     * @param followcount   取值是+1  或者 -1
     */
    @Query("update User u set u.followcount = u.followcount + ?2 where u.id = ?1 ")
    @Modifying
    void updateFollowCount(String userid,int followcount);

    /**
     * 更新用户粉丝数
     * @param userid
     * @param fanscount
     */
    @Query("update User u set u.fanscount = u.fanscount + ?2 where u.id = ?1 ")
    @Modifying
    void updateFansCount(String userid,int fanscount);
}
