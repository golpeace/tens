package com.tensquare.spit.service;

import com.tensquare.spit.dao.SpitDao;
import com.tensquare.spit.pojo.Spit;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import util.IdWorker;

import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * @author 黑马程序员
 * @Company http://www.ithiema.com
 */
@Service
public class SpitService {

    @Autowired
    private SpitDao spitDao;

    @Autowired
    private IdWorker idWorker;

    @Autowired
    private MongoTemplate mongoTemplate;

    /**
     * 保存
     * @param spit
     */
    public void save(Spit spit){
        //设置id
        spit.set_id(String.valueOf(idWorker.nextId()));
        spit.setPublishtime(new Date());
        spit.setComment(0);//回复数
        spit.setShare(0);//分享数
        spit.setState("1");//是否可用
        spit.setThumbup(0);//点赞数
        spit.setVisits(0);//访问数
        spitDao.save(spit);

        //判断当前的吐槽是新的，还是对已有吐槽的回复
        if(!StringUtils.isEmpty(spit.getParentid())){
            //针对已有吐槽的回复，需要更新已有吐槽的回复数

            //全字段更新的思路
//            Spit parentSpit = findById(spit.getParentid());
//            parentSpit.setComment(parentSpit.getComment()+1);
//            spitDao.save(parentSpit);

            //更新指定字段的思路
            //创建查询对象
            Query query=new Query();
            //添加查询条件
            query.addCriteria(Criteria.where("_id").is(spit.getParentid()));
            //创建更新对象
            Update update=new Update();
            update.inc("comment",1);
            mongoTemplate.updateFirst(query,update,"spit");
        }
    }

    /**
     *
     *
     * 更新
     * @param spit  它有可能只有内容和id （从技术角度说可以有很多信息，但是从业务上分析，我们只能改内容）
     */
    public void update(Spit spit){
        //1.从mongodb中查询出吐槽信息
        Spit mongodbspit = findById(spit.get_id());//它才有全部内容
        //2.给mongodb吐槽信息的内容字段赋值
        mongodbspit.setContent(spit.getContent());
        //3.更新mongodbspit
        spitDao.save(mongodbspit);


       // update   set content = ? where id =?

//        //创建查询对象
//        Query query=new Query();
//        //添加查询条件
//        query.addCriteria(Criteria.where("_id").is(spit.get_id()));
//
//        //创建更新对象
//        Update update=new Update();
//        update.set("content",spit.getContent());
//
//
//        mongoTemplate.updateFirst(query,update,"spit");
    }

    /**
     * 删除
     * @param id
     */
    public void delete(String id){
        spitDao.deleteById(id);
    }

    /**
     * 查询所有
     * @return
     */
    public List<Spit> findAll(){
        return spitDao.findAll();
    }

    /**
     * 根据id查询
     * @param id
     * @return
     */
    public Spit findById(String id){
        return spitDao.findById(id).get();
    }

    /**
     * 查询吐槽回复带分页
     * @param parentid
     * @param page
     * @param size
     * @return
     */
    public Page<Spit> findByParentid(String parentid, int page,int size){
        //1.创建分页对象
        PageRequest pageRequest = PageRequest.of(page-1,size);
        //2.查询并返回
        return spitDao.findByParentid(parentid,pageRequest);
    }


    @Autowired
    private RedisTemplate redisTemplate;
    /**
     * 吐槽点赞
     */
    public void updateThumbup(String id) {
//        //1.从mongodb中查询出吐槽信息
//////        Spit mongodbspit = findById(spit.get_id());//它才有全部内容
//////        //2.点赞就是给查出来的mongodbspit的thumbup字段值加1
//////        mongodbspit.setThumbup(mongodbspit.getThumbup()+1);
//////        //3.更新mongodbspit
//////        spitDao.save(mongodbspit);

        //目前只能写死用户id，等讲完jwt鉴权就可以从指定位置获取了
        String userid = "1001";


        //1.前往redis中获取是否点过赞的信息
        Object obj = redisTemplate.opsForValue().get(userid+"_"+id);
        if(obj != null){
            throw new RuntimeException("请不要重复点赞");
        }

        //创建查询对象
        Query query=new Query();
        //添加查询条件
        query.addCriteria(Criteria.where("_id").is(id));

        //创建更新对象
        Update update=new Update();
        update.inc("thumbup",1);

        mongoTemplate.updateFirst(query,update,"spit");

        redisTemplate.opsForValue().set(userid+"_"+id,"thumbup",30, TimeUnit.DAYS);
    }
}
