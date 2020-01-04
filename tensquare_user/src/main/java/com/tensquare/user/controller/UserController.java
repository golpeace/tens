package com.tensquare.user.controller;

import com.tensquare.user.pojo.User;
import com.tensquare.user.service.UserService;
import entity.PageResult;
import entity.Result;
import entity.StatusCode;
import io.jsonwebtoken.Claims;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;
import util.JwtUtil;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;
/**
 * 控制器层
 * @author Administrator
 *
 */
@RestController
@CrossOrigin
@RequestMapping("/user")
public class UserController {

	@Autowired
	private UserService userService;
	
	
	/**
	 * 查询全部数据
	 * @return
	 */
	@RequestMapping(method= RequestMethod.GET)
	public Result findAll(){
		return new Result(true,StatusCode.OK,"查询成功",userService.findAll());
	}
	
	/**
	 * 根据ID查询
	 * @param id ID
	 * @return
	 */
	@RequestMapping(value="/{id}",method= RequestMethod.GET)
	public Result findById(@PathVariable String id){
		return new Result(true,StatusCode.OK,"查询成功",userService.findById(id));
	}


	/**
	 * 分页+多条件查询
	 * @param searchMap 查询条件封装
	 * @param page 页码
	 * @param size 页大小
	 * @return 分页结果
	 */
	@RequestMapping(value="/search/{page}/{size}",method=RequestMethod.POST)
	public Result findSearch(@RequestBody Map searchMap , @PathVariable int page, @PathVariable int size){
		Page<User> pageList = userService.findSearch(searchMap, page, size);
		return  new Result(true,StatusCode.OK,"查询成功",  new PageResult<User>(pageList.getTotalElements(), pageList.getContent()) );
	}

	/**
     * 根据条件查询
     * @param searchMap
     * @return
     */
    @RequestMapping(value="/search",method = RequestMethod.POST)
    public Result findSearch( @RequestBody Map searchMap){
        return new Result(true,StatusCode.OK,"查询成功",userService.findSearch(searchMap));
    }
	
	/**
	 * 增加
	 * @param user
	 */
	@RequestMapping(method=RequestMethod.POST)
	public Result add(@RequestBody User user  ){
		userService.add(user);
		return new Result(true,StatusCode.OK,"增加成功");
	}
	
	/**
	 * 修改
	 * @param user
	 */
	@RequestMapping(value="/{id}",method= RequestMethod.PUT)
	public Result update(@RequestBody User user, @PathVariable String id ){
		user.setId(id);
		userService.update(user);		
		return new Result(true,StatusCode.OK,"修改成功");
	}


	/**
	 * 删除
	 * 需要管理员权限
	 * @param id
	 */
	@RequestMapping(value="/{id}",method= RequestMethod.DELETE)
	public Result delete(@PathVariable String id, HttpServletRequest request){
		//1.从请求域中获取到claims
		Claims claims = (Claims) request.getAttribute("admin_claims");
		//2.判断有没有claims
		if(claims == null){
			return new Result(false,StatusCode.ACCESSERROR,"没有权限");
		}
		//3.执行操作
		userService.deleteById(id);
		return new Result(true,StatusCode.OK,"删除成功");
	}

	
//	/**
//	 * 删除
//	 * @param id
//	 */
//	@RequestMapping(value="/{id}",method= RequestMethod.DELETE)
//	public Result delete(@PathVariable String id,@RequestHeader(value="Authorization",required = false) String header){
//		//1.没有消息头
//		if(StringUtils.isEmpty(header)){
//			return new Result(false,StatusCode.ACCESSERROR,"没有权限（没有消息头）");
//		}
//		//2.判断header是否符合格式
//		if(!header.startsWith("Bearer ")){
//			return new Result(false,StatusCode.ACCESSERROR,"没有权限（消息头格式不对1）");
//		}
//		//3.判断Bearer后面的内容是否正确
//		String token = "";
//		try {
//			token = header.split(" ")[1];
//		}catch (Exception e){
//			return new Result(false,StatusCode.ACCESSERROR,"没有权限（消息头格式不对2）");
//		}
//		//4.验证token
//		Claims claims = jwtUtil.parseJWT(token);
//		if(claims == null){
//			return new Result(false,StatusCode.ACCESSERROR,"没有权限（token内容不合法）");
//		}
//		//5.判断是否是管理员权限
//		String roles = (String)claims.get("roles");
//		if(!"admin".equals(roles)){
//			return new Result(false,StatusCode.ACCESSERROR,"没有权限（没有管理员权限）");
//		}
//
//
//		userService.deleteById(id);
//		return new Result(true,StatusCode.OK,"删除成功");
//	}


	/**
	 * 注册
	 * @param code
	 */
	@RequestMapping(value="/register/{code}",method= RequestMethod.POST)
	public Result registry(@RequestBody User user,@PathVariable("code") String code ){
		userService.registry(user,code);
		return new Result(true,StatusCode.OK,"注册成功");
	}


	/**
	 * 获取验证码
	 * @param mobile
	 */
	@RequestMapping(value="/sendsms/{mobile}",method= RequestMethod.POST)
	public Result generateCode(@PathVariable String mobile ){
		userService.generateCode(mobile);
		return new Result(true,StatusCode.OK,"生成成功");
	}


	@Autowired
	private JwtUtil jwtUtil;
	/**
	 * 用户登录
	 */
	@RequestMapping(value="/login",method= RequestMethod.POST)
	public Result userLogin(@RequestBody User user){
		//1.根据用户手机号和密码查询
		User sysUser = userService.userLogin(user.getMobile(),user.getPassword());
		//2.签发token
		String token = jwtUtil.createJWT(sysUser.getId(),sysUser.getNickname(),"user");
		//3.创建返回的map
		Map<String,String> map = new HashMap<>();
		map.put("nickname",sysUser.getNickname());
		map.put("token",token);
		map.put("avatar",sysUser.getAvatar());
		return new Result(true,StatusCode.OK,"登录成功",map);
	}

	/**
	 *  更新关注数
	 * @param userid
	 * @param followcount
	 */
	@RequestMapping(value="/incfollow/{userid}/{followcount}",method = RequestMethod.PUT)
	public void updateFollowCount(@PathVariable("userid") String userid,@PathVariable("followcount") int followcount){
		userService.updateFollowCount(userid,followcount);
	}


	/**
	 *  更新粉丝数
	 * @param userid
	 * @param fanscount
	 */
	@RequestMapping(value="/incfans/{userid}/{fanscount}",method = RequestMethod.PUT)
	public void updateFansCount(@PathVariable("userid") String userid,@PathVariable("fanscount") int fanscount){
		userService.updateFansCount(userid,fanscount);
	}
}
