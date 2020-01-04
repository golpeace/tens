package com.tensquare.friend.controller;

import com.tensquare.friend.service.FriendService;
import entity.Result;
import entity.StatusCode;
import io.jsonwebtoken.Claims;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

/**
 * 交友的控制器
 * @author 黑马程序员
 * @Company http://www.ithiema.com
 */
@RestController
@RequestMapping("/friend")
public class FriendController {

    @Autowired
    private FriendService friendService;

    /**
     * 添加好友 ||
     * @param friendid
     * @param type      互粉还是拉黑
     * @param request
     * @return
     */
    @RequestMapping(value="/like/{friendid}/{type}",method = RequestMethod.PUT )
    public Result addFriend(@PathVariable("friendid") String friendid,@PathVariable("type") String type, HttpServletRequest request){
        //1.取出claims
        Claims claims = (Claims)request.getAttribute("user_claims");
        if(claims == null){
            return new Result(false, StatusCode.ACCESSERROR,"没有权限添加好友或者拉黑");
        }
        //2.获取用户id
        String userid = claims.getId();
        //3.判断当前的类型
        if("1".equals(type)){
            //添加好友
            friendService.addFriend(userid,friendid);
        }else{
            //拉黑
            friendService.addNoFriend(userid,friendid);
        }
        //3.返回
        return new Result(true,StatusCode.OK,"操作成功");
    }

    /**
     * 删除好友
     * @param friendid
     * @return
     */
    @RequestMapping(value="/{friendid}",method = RequestMethod.DELETE)
    public Result deleteFriend(@PathVariable("friendid") String friendid,HttpServletRequest request){
        //1.取出claims
        Claims claims = (Claims)request.getAttribute("user_claims");
        if(claims == null){
            return new Result(false, StatusCode.ACCESSERROR,"没有权限删除");
        }
        //2.获取用户id
        String userid = claims.getId();
        //3.调用业务层实现删除
        friendService.deleteFriend(userid,friendid);
        //4.返回
        return new Result(true,StatusCode.OK,"删除成功");
    }



















}
