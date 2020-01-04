package com.tensquare.search.controller;

import com.tensquare.search.pojo.Article;
import com.tensquare.search.service.ArticleSearchService;
import entity.PageResult;
import entity.Result;
import entity.StatusCode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

/**
 * 搜索微服务的控制器
 * @author 黑马程序员
 * @Company http://www.ithiema.com
 */
@RestController
@RequestMapping("/article")
public class ArticleSearchController {

    @Autowired
    private ArticleSearchService articleSearchService;

    /**
     * 保存
     * @param article
     * @return
     */
    @RequestMapping(method = RequestMethod.POST)
    public Result save(@RequestBody Article article){
        articleSearchService.save(article);
        return new Result(true, StatusCode.OK,"保存成功");
    }


    /**
     * 更新
     * @param article
     * @return
     */
    @RequestMapping(value="/{id}",method = RequestMethod.PUT)
    public Result update(@PathVariable("id") String id,@RequestBody Article article){
        article.setId(id);
        articleSearchService.update(article);
        return new Result(true, StatusCode.OK,"更新成功");
    }


    /**
     * 删除
     * @param id
     * @return
     */
    @RequestMapping(value="/{id}",method = RequestMethod.DELETE)
    public Result delete(@PathVariable("id") String id){
        articleSearchService.delete(id);
        return new Result(true, StatusCode.OK,"删除成功");
    }

    /**
     * 根据id查询
     * @param id
     * @return
     */
    @RequestMapping(value="/{id}",method = RequestMethod.GET)
    public Result findById(@PathVariable("id") String id){
        Article article = articleSearchService.findById(id);
        return new Result(true, StatusCode.OK,"查询成功",article);
    }

    /**
     * 查询所有
     * @return
     */
    @RequestMapping(method = RequestMethod.GET)
    public Result findAll(){
        Iterable<Article> articles = articleSearchService.findAll();
        return new Result(true, StatusCode.OK,"查询成功",articles);
    }

    /**
     * 根据标题或者内容模糊查询
     * @param keywords
     * @param page
     * @param size
     * @return
     */
    @RequestMapping(value="/search/{keywords}/{page}/{size}",method = RequestMethod.GET)
    public Result findSearch(@PathVariable("keywords") String keywords,@PathVariable("page") int page,@PathVariable("size") int size){
        //1.查询结果
        Page<Article> articlePage = articleSearchService.findByKeywords(keywords,page,size);
        //2.创建自定义分页结果集对象
        PageResult<Article> pageResult = new PageResult<>(articlePage.getTotalElements(),articlePage.getContent());
        //3.返回
        return new Result(true,StatusCode.OK,"查询成功",pageResult);
    }
}
