package com.tensquare.qa.controller;

import com.tensquare.qa.client.LabelClient;
import com.tensquare.qa.pojo.Problem;
import com.tensquare.qa.service.ProblemService;
import entity.PageResult;
import entity.Result;
import entity.StatusCode;
import io.jsonwebtoken.Claims;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;
/**
 * 控制器层
 * @author Administrator
 *
 */
@RestController
@CrossOrigin
@RequestMapping("/problem")
public class ProblemController {

	@Autowired
	private ProblemService problemService;
	
	
	/**
	 * 查询全部数据
	 * @return
	 */
	@RequestMapping(method= RequestMethod.GET)
	public Result findAll(){
		return new Result(true,StatusCode.OK,"查询成功",problemService.findAll());
	}
	
	/**
	 * 根据ID查询
	 * @param id ID
	 * @return
	 */
	@RequestMapping(value="/{id}",method= RequestMethod.GET)
	public Result findById(@PathVariable String id){
		return new Result(true,StatusCode.OK,"查询成功",problemService.findById(id));
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
		Page<Problem> pageList = problemService.findSearch(searchMap, page, size);
		return  new Result(true,StatusCode.OK,"查询成功",  new PageResult<Problem>(pageList.getTotalElements(), pageList.getContent()) );
	}

	/**
     * 根据条件查询
     * @param searchMap
     * @return
     */
    @RequestMapping(value="/search",method = RequestMethod.POST)
    public Result findSearch( @RequestBody Map searchMap){
        return new Result(true,StatusCode.OK,"查询成功",problemService.findSearch(searchMap));
    }
	
	/**
	 * 增加
	 * @param problem
	 */
	@RequestMapping(method=RequestMethod.POST)
	public Result add(@RequestBody Problem problem, HttpServletRequest request){
		//1.获取请求域中的claims
		Claims claims = (Claims) request.getAttribute("user_claims");
		//2.判断claims是否存在
		if(claims == null){
			return new Result(false,StatusCode.ACCESSERROR,"没有权限发布问题");
		}
		problemService.add(problem);
		return new Result(true,StatusCode.OK,"增加成功");
	}
	
	/**
	 * 修改
	 * @param problem
	 */
	@RequestMapping(value="/{id}",method= RequestMethod.PUT)
	public Result update(@RequestBody Problem problem, @PathVariable String id ){
		problem.setId(id);
		problemService.update(problem);		
		return new Result(true,StatusCode.OK,"修改成功");
	}
	
	/**
	 * 删除
	 * @param id
	 */
	@RequestMapping(value="/{id}",method= RequestMethod.DELETE)
	public Result delete(@PathVariable String id,HttpServletRequest request){
		//1.获取请求域中的claims
		Claims claims = (Claims) request.getAttribute("user_claims");
		//2.判断claims是否存在
		if(claims == null){
			return new Result(false,StatusCode.ACCESSERROR,"没有权限删除问题");
		}
		problemService.deleteById(id);
		return new Result(true,StatusCode.OK,"删除成功");
	}


	/**
	 * 获取最新回复的问题列表
	 * @param labelid
	 * @param page
	 * @param size
	 * @return
	 */
	@RequestMapping(value="/newlist/{labelid}/{page}/{size}",method = RequestMethod.GET)
	public Result findNewRecommendList(@PathVariable("labelid") String labelid,@PathVariable("page") int page ,@PathVariable("size") int size){
		//1.调用业务层方法查询
		Page<Problem> problemPage = problemService.findNewRecommendList(labelid,page,size);
		//2.创建自定义的分页对象
		PageResult<Problem> problemPageResult = new PageResult<>(problemPage.getTotalElements(),problemPage.getContent());
		//3.返回
		return new Result(true,StatusCode.OK,"查询成功",problemPageResult);
	}


	/**
	 * 获取热门回复的问题列表
	 * @param labelid
	 * @param page
	 * @param size
	 * @return
	 */
	@RequestMapping(value="/hotlist/{labelid}/{page}/{size}",method = RequestMethod.GET)
	public Result findHotRecommendList(@PathVariable("labelid") String labelid,@PathVariable("page") int page ,@PathVariable("size") int size){
		//1.调用业务层方法查询
		Page<Problem> problemPage = problemService.findHotRecommendList(labelid,page,size);
		//2.创建自定义的分页对象
		PageResult<Problem> problemPageResult = new PageResult<>(problemPage.getTotalElements(),problemPage.getContent());
		//3.返回
		return new Result(true,StatusCode.OK,"查询成功",problemPageResult);
	}


	/**
	 * 获取等待回复的问题列表
	 * @param labelid
	 * @param page
	 * @param size
	 * @return
	 */
	@RequestMapping(value="/waitlist/{labelid}/{page}/{size}",method = RequestMethod.GET)
	public Result findWaitRecommendList(@PathVariable("labelid") String labelid,@PathVariable("page") int page ,@PathVariable("size") int size){
		//1.调用业务层方法查询
		Page<Problem> problemPage = problemService.findWaitRecommendList(labelid,page,size);
		//2.创建自定义的分页对象
		PageResult<Problem> problemPageResult = new PageResult<>(problemPage.getTotalElements(),problemPage.getContent());
		//3.返回
		return new Result(true,StatusCode.OK,"查询成功",problemPageResult);
	}

	@Autowired
	private LabelClient client;

	@RequestMapping(value="/label/{labelid}",method = RequestMethod.GET)
	public Result findByLabelid(@PathVariable String labelid){
		return client.findById(labelid);
	}
}
