package com.tensquare.spit.controller;

import com.tensquare.spit.pojo.Spit;
import com.tensquare.spit.service.SpitService;
import entity.PageResult;
import entity.Result;
import entity.StatusCode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author 黑马程序员
 * @Company http://www.ithiema.com
 */
@RestController
@RequestMapping("/spit")
public class SpitController {
    @Autowired
    private SpitService spitService;

    /**
     * 发布吐槽
     * @param spit
     * @return
     */
    @RequestMapping(method= RequestMethod.POST)
    public Result add(@RequestBody Spit spit){
        //调用业务层实现保存
        spitService.save(spit);
        //返回
        return new Result(true, StatusCode.OK,"操作成功");
    }


    /**
     * 更新
     * @param spit
     * @return
     */
    @RequestMapping(value="/{id}",method= RequestMethod.PUT)
    public Result update(@RequestBody Spit spit,@PathVariable("id") String id){
        //给吐槽赋值id
        spit.set_id(id);
        //调用业务层实现更新
        spitService.update(spit);
        //返回
        return new Result(true, StatusCode.OK,"操作成功");
    }

    /**
     * 删除
     * @param id
     * @return
     */
    @RequestMapping(value="/{id}",method= RequestMethod.DELETE)
    public Result delete(@PathVariable("id") String id){
        //调用业务层实现删除
        spitService.delete(id);
        //返回
        return new Result(true, StatusCode.OK,"操作成功");
    }

    /**
     * 根据id查询
     * @param id
     * @return
     */
    @RequestMapping(value="/{id}",method= RequestMethod.GET)
    public Result findById(@PathVariable("id") String id){
        //调用业务层实现根据id查询
        Spit spit = spitService.findById(id);
        //返回
        return new Result(true, StatusCode.OK,"操作成功",spit);
    }

    /**
     * 查询所有
     * @return
     */
    @RequestMapping(method= RequestMethod.GET)
    public Result findAll(){
        //调用业务层实现查询所有
        List<Spit> spits = spitService.findAll();
        //返回
        return new Result(true, StatusCode.OK,"操作成功",spits);
    }

    /**
     * 根据父id查询吐槽
     * @param parentid
     * @param page
     * @param size
     * @return
     */
    @RequestMapping(value="/comment/{parentid}/{page}/{size}",method = RequestMethod.GET)
    public Result findByParentid(@PathVariable("parentid") String parentid,@PathVariable("page") int page,@PathVariable("size") int size){
        //1.调用业务层查询
        Page<Spit> spitPage = spitService.findByParentid(parentid,page,size);
        //2.创建自定义的分页结果集对象
        PageResult<Spit> spitPageResult = new PageResult<>(spitPage.getTotalElements(),spitPage.getContent());
        //3.返回
        return new Result(true,StatusCode.OK,"查询成功",spitPageResult);
    }

    /**
     * 吐槽点赞
     * @param id
     * @return
     */
    @RequestMapping(value="/thumbup/{id}",method = RequestMethod.PUT )
    public Result updateThumbup(@PathVariable("id") String id){
        spitService.updateThumbup(id);
        return new Result(true,StatusCode.OK,"点赞成功");
    }
}
