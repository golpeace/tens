package com.tensquare.search.service;

import com.tensquare.search.dao.ArticleSearchDao;
import com.tensquare.search.pojo.Article;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import util.IdWorker;

/**
 * @author 黑马程序员
 * @Company http://www.ithiema.com
 */
@Service
public class ArticleSearchService {

    @Autowired
    private ArticleSearchDao articleSearchDao;

    @Autowired
    private IdWorker idWorker;

    /**
     * 保存
     * @param article
     */
    public void save(Article article){
        article.setId(String.valueOf(idWorker.nextId()));
        articleSearchDao.save(article);
    }

    /**
     * 更新
     * @param article
     */
    public void update(Article article){
        articleSearchDao.save(article);
    }

    /**
     * 删除
     * @param id
     */
    public void delete(String id){
        articleSearchDao.deleteById(id);
    }

    /**
     * 根据id查询
     * @param id
     * @return
     */
    public Article findById(String id){
        return articleSearchDao.findById(id).get();
    }

    /**
     * 查询所有
     * @return
     */
    public Iterable<Article> findAll(){
        return articleSearchDao.findAll();
    }

    /**
     * 根据关键词查询
     * @param keywords
     * @param page
     * @param size
     * @return
     */
    public Page<Article> findByKeywords(String keywords, int page,int size){
        //1.创建分页对象
        PageRequest pageRequest = PageRequest.of(page-1,size);
        //2.调用持久层查询
        return articleSearchDao.findByTitleLikeOrContentLike(keywords, keywords,pageRequest);
    }
}
