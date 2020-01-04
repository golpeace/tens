package com.tensquare.search.dao;

import com.tensquare.search.pojo.Article;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * 搜索微服务的文章持久层接口
 * @author 黑马程序员
 * @Company http://www.ithiema.com
 */
public interface ArticleSearchDao extends ElasticsearchRepository<Article,String>{

    /**
     * 根据文章的标题或者内容查询文章
     * 带分页
     */
    Page<Article> findByTitleLikeOrContentLike(String title, String content, Pageable pageable);
}
