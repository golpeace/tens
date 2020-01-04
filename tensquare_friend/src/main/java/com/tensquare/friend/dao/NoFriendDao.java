package com.tensquare.friend.dao;

import com.tensquare.friend.pojo.NoFriend;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

/**
 * @author 黑马程序员
 * @Company http://www.ithiema.com
 */
public interface NoFriendDao extends JpaRepository<NoFriend,String> {

    /**
     * 根据用户和好友id在非好友表中查询
     * @param userid
     * @param friendid
     * @return
     */
    @Query("select count(nf) from NoFriend  nf  where nf.userid = ?1 and nf.friendid=?2")
    int selectCount(String userid,String friendid);
}
