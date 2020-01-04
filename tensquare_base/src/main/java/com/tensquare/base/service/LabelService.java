package com.tensquare.base.service;

import com.tensquare.base.dao.LabelDao;
import com.tensquare.base.pojo.Label;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import util.IdWorker;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.List;

/**
 * 标签的业务层实现类
 * @author 黑马程序员
 * @Company http://www.ithiema.com
 */
@Service
public class LabelService {

    @Autowired
    private LabelDao labelDao;
    @Autowired
    private IdWorker idWorker;

    /**
     * 查询所有
     * @return
     */
    public List<Label> findAll(){
        return labelDao.findAll();
    }

    /**
     * 根据id查询
     * @param id
     * @return
     */
    public Label findById(String id){
        return labelDao.findById(id).get();
    }

    /**
     * 新增
     * @param label
     */
    public void save(Label label){
        //1.设置id
        label.setId(String.valueOf(idWorker.nextId()));
        label.setCount(0);//新增的标签使用情况是0
        label.setFans(0);//新增的标签关注数是0
        label.setRecommend("1");//推荐的
        label.setState("1");//新增的都是有效的

        labelDao.save(label);
    }

    /**
     * 更新
     * @param label
     */
    public void update(Label label){
        labelDao.save(label);
    }

    /**
     * 根据id删除
     * @param id
     */
    public void delete(String id){
        labelDao.deleteById(id);
    }

    @Transactional
    public void updateLableFans(String id,Integer fans){
        labelDao.updateLabelFans(id,fans);
    }


    /**
     *  根据条件查询并且带分页
     *      labelname
     *      state
     *      recommend
     * @return
     */
    public Page<Label> findByConditionPage(Label label,int page,int size){
        //1.创建Specification对象
        Specification<Label> spec =  createCondition(label);
        //2.创建分页对象
        PageRequest pageRequest = PageRequest.of(page-1,size);

        //3.使用spec和pageable查询并返回
        return labelDao.findAll(spec,pageRequest);
    }


    /**
     *  根据条件查询
     *      labelname
     *      state
     *      recommend
     * @return
     */
    public List<Label> findByCondition(Label label){
        //1.创建Specification对象
        Specification<Label> spec = createCondition(label);

        //2.使用spec查询并返回
        return labelDao.findAll(spec);
    }

    /**
     * 创建查询条件
     * @param label
     * @return
     */
    private Specification<Label> createCondition(Label label){
        return new Specification<Label>() {
            @Override
            public Predicate toPredicate(Root<Label> root, CriteriaQuery<?> cq, CriteriaBuilder cb) {
                //用于封装条件
                ArrayList<Predicate> predicates = new ArrayList<>();

                //1.添加labelname的模糊条件
                if(!StringUtils.isEmpty(label.getLabelname())){
                    Predicate p1 = cb.like(root.get("labelname"),"%"+label.getLabelname()+"%");
                    predicates.add(p1);
                }
                //2.添加状态的精确查询
                if(!StringUtils.isEmpty(label.getState())){
                    Predicate p2 = cb.equal(root.get("state"),label.getState());
                    predicates.add(p2);
                }
                //3.添加是否推荐的条件
                if(!StringUtils.isEmpty(label.getRecommend())){
                    Predicate p3 = cb.equal(root.get("recommend"),label.getRecommend());
                    predicates.add(p3);
                }
                return cb.and(predicates.toArray(new Predicate[predicates.size()]));
            }
        };
    }
}
