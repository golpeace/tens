package com.tensquare.friend.service;

import com.tensquare.friend.client.UserClient;
import com.tensquare.friend.dao.FriendDao;
import com.tensquare.friend.dao.NoFriendDao;
import com.tensquare.friend.pojo.Friend;
import com.tensquare.friend.pojo.NoFriend;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * 交友的业务层：业务层可以是对应多个dao
 * @author 黑马程序员
 * @Company http://www.ithiema.com
 */
@Service
public class FriendService {

    @Autowired
    private FriendDao friendDao;

    @Autowired
    private NoFriendDao noFriendDao;

    @Autowired
    private UserClient userClient;

    /**
     * 添加好友
     * @return

    @Transactional
    public int addFriend(String userid,String friendid){
        //1.看看当前用户是否已经加过此人为好友了
        int count = friendDao.selectCount(userid,friendid);
        if(count>0){
            return 0;//表示已经添加过了
        }
        //2.此时没有添加过，需要创建Friend对象
        Friend friend = new Friend();
        friend.setUserid(userid);
        friend.setFriendid(friendid);
        friend.setIslike("0");//此时还不知道是单向还是双向，所以默认单向
        //3.保存
        friendDao.save(friend);
        //4.把friendid作为userid，把userid作为friendid再次查询
        int inversecount = friendDao.selectCount(friendid,userid);
        if(inversecount > 0){
            //此时是互相喜欢,需要更新两条记录的islike为1
            friendDao.updateIsLike(userid,friendid,"1");
            friendDao.updateIsLike(friendid,userid,"1");
        }
        //5.调用用户微服务去添加粉丝数和关注数
        return 1;
    }*/

    /**
     * 添加好友
     * @return
     */
    @Transactional
    public void addFriend(String userid,String friendid){
        //1.看看当前用户是否已经加过此人为好友了
        int count = friendDao.selectCount(userid,friendid);
        if(count>0){
            throw new RuntimeException("表示已经添加过了");
        }
        //2.此时没有添加过，需要创建Friend对象
        Friend friend = new Friend();
        friend.setUserid(userid);
        friend.setFriendid(friendid);
        friend.setIslike("0");//此时还不知道是单向还是双向，所以默认单向
        //3.保存
        friendDao.save(friend);
        //4.把friendid作为userid，把userid作为friendid再次查询
        int inversecount = friendDao.selectCount(friendid,userid);
        if(inversecount > 0){
            //此时是互相喜欢,需要更新两条记录的islike为1
            friendDao.updateIsLike(userid,friendid,"1");
            friendDao.updateIsLike(friendid,userid,"1");
        }
        //5.调用用户微服务去添加粉丝数和关注数
        userClient.updateFansCount(friendid,1);
        userClient.updateFollowCount(userid,1);
    }
    /**
     * 删除好友
     * @param userid
     * @param friendid
     * @return
     */
    @Transactional
    public void deleteFriend(String userid,String friendid){
        //1.直接删除
        friendDao.deleteFriend(userid,friendid);
        //2.让userid和friendid互换位置再次查询，是否互相为好友
        int count = friendDao.selectCount(friendid,userid);
        if(count>0){
            //更新记录的islike为0
            friendDao.updateIsLike(friendid,userid,"0");
        }
        //3.调用用户微服务，更新用户粉丝数和关注数
        userClient.updateFansCount(friendid,-1);
        userClient.updateFollowCount(userid,-1);
    }

    /**
     * 拉黑
     * @param userid
     * @param friendid
     */
    public void addNoFriend(String userid,String friendid){
        //1.使用用户Id和好友id去查询
        int count = noFriendDao.selectCount(userid,friendid);
        if(count > 0){
            throw new RuntimeException("已经拉黑了");
        }
        //2.添加非好友
        NoFriend noFriend = new NoFriend();
        noFriend.setUserid(userid);
        noFriend.setFriendid(friendid);
        noFriendDao.save(noFriend);
    }
}
