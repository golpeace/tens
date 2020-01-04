package com.tensquare.friend.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * 用户微服务的调用客户端类
 * @author 黑马程序员
 * @Company http://www.ithiema.com
 */
@FeignClient("tensquare-user")
public interface UserClient {

    /**
     *  更新关注数
     * @param userid
     * @param followcount
     */
    @RequestMapping(value="/user/incfollow/{userid}/{followcount}",method = RequestMethod.PUT)
    void updateFollowCount(@PathVariable("userid") String userid, @PathVariable("followcount") int followcount);


    /**
     *  更新粉丝数
     * @param userid
     * @param fanscount
     */
    @RequestMapping(value="/user/incfans/{userid}/{fanscount}",method = RequestMethod.PUT)
    void updateFansCount(@PathVariable("userid") String userid,@PathVariable("fanscount") int fanscount);

}
