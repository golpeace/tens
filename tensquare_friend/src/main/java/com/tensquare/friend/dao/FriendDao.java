package com.tensquare.friend.dao;

import com.tensquare.friend.pojo.Friend;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

/**
 * 交友的持久层接口
 * @author 黑马程序员
 * @Company http://www.ithiema.com
 */
public interface FriendDao extends JpaRepository<Friend,String>{

    //判断是否可以添加好友

    /**
     * 根据用户id和好友id查询是否有记录
     * @param userid
     * @param friendid
     * @return
     */
    @Query("select count(f) from Friend  f   where  f.userid = ?1  and f.friendid = ?2 ")
    int selectCount(String userid,String friendid);

    /**
     * 更新好友表中的islike字段
     * @param userid
     * @param friendid
     * @param islike
     */
    @Query("update Friend f set f.islike = ?3 where f.userid = ?1  and f.friendid = ?2 ")
    @Modifying
    void updateIsLike(String userid,String friendid,String islike);

    /**
     * 删除好友
     * @param userid
     * @param friendid
     */
    @Query("delete from Friend f where f.userid = ?1 and f.friendid = ?2 ")
    @Modifying
    void deleteFriend(String userid,String friendid);
}
