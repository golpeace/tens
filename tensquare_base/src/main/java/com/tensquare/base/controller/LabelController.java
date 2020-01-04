package com.tensquare.base.controller;

import com.tensquare.base.pojo.Label;
import com.tensquare.base.service.LabelService;
import entity.PageResult;
import entity.Result;
import entity.StatusCode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 标签的控制器
 * @author 黑马程序员
 * @Company http://www.ithiema.com
 */
@RestController
@RequestMapping("/label")
@CrossOrigin//表示当前控制器支持跨域访问（跨域：协议 主机 端口任意一个不同都是跨域）
public class LabelController {

    @Autowired
    private LabelService labelService;

    /**
     * 查询所有
     * @return
     */
    @RequestMapping(method = RequestMethod.GET)
    public Result findAll(){
        //1.调用业务层查询
        List<Label> labels = labelService.findAll();
        //2.返回
        return new Result(true, StatusCode.OK,"查询成功",labels);
    }

    /**
     * 根据Id查询
     * @param id
     * @return
     */
    @RequestMapping(value="/{id}",method = RequestMethod.GET)
    public Result findById(@PathVariable("id") String id){
        System.out.println("No.2 根据id查询标签");
        //1.调用业务层查询
        Label label = labelService.findById(id);
        //2.返回
        return new Result(true, StatusCode.OK,"查询成功",label);
    }

    /**
     * 保存
     * @param label
     * @return
     */
    @RequestMapping(method = RequestMethod.POST)
    public Result save(@RequestBody Label label){
        //1.调用业务层保存
        labelService.save(label);
        //2.返回
        return new Result(true, StatusCode.OK,"保存成功");
    }


    /**
     * 更新
     * @param label
     * @return
     */
    @RequestMapping(value="/{id}",method = RequestMethod.PUT)
    public Result update(@PathVariable("id") String id,@RequestBody Label label){
        //1.调用业务层更新
        label.setId(id);
        labelService.update(label);
        //2.返回
        return new Result(true, StatusCode.OK,"更新成功");
    }


    /**
     * 删除
     * @param id
     * @return
     */
    @RequestMapping(value="/{id}",method = RequestMethod.DELETE)
    public Result delete(@PathVariable("id") String id){
        //1.调用业务层删除
        labelService.delete(id);
        //2.返回
        return new Result(true, StatusCode.OK,"删除成功");
    }

    /**
     * 条件查询
     * @param label
     * @return
     */
    @RequestMapping(value="/search",method = RequestMethod.POST)
    public Result findByCondition(@RequestBody Label label){
        //1.调用业务层查询
        List<Label> labels = labelService.findByCondition(label);
        //2.创建返回值对象
        return new Result(true,StatusCode.OK,"查询成功",labels);
    }


    /**
     * 条件查询
     * @param label
     * @return
     */
    @RequestMapping(value="/search/{page}/{size}",method = RequestMethod.POST)
    public Result findByCondition(@RequestBody Label label,@PathVariable("page") int page,@PathVariable("size") int size){
        //1.调用业务层查询
        Page<Label> labelPage = labelService.findByConditionPage(label,page,size);
        //2.创建字节定义的分页对象
        PageResult<Label> pageResult = new PageResult<>(labelPage.getTotalElements(),labelPage.getContent());
        //2.创建返回值对象
        return new Result(true,StatusCode.OK,"查询成功",pageResult);
    }
}
