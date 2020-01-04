package com.tensquare;

import com.tensquare.base.BaseApplication;
import com.tensquare.base.pojo.Label;
import com.tensquare.base.service.LabelService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.List;

/**
 * @author 黑马程序员
 * @Company http://www.ithiema.com
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = BaseApplication.class)
public class LabelServiceTest {

    @Autowired
    private LabelService labelService;

    @Test
    public void testSave(){
        Label label = new Label();
        label.setLabelname("Java");
        labelService.save(label);
    }


    @Test
    public void testFindOne(){
        Label label = labelService.findById("1037282377732329472");
        System.out.println(label);
    }


    @Test
    public void testUpdate(){
        Label label = labelService.findById("1037282377732329472");
        System.out.println("更新前："+label);
        label.setLabelname("JavaEE");
        labelService.update(label);
        label = labelService.findById("1037282377732329472");
        System.out.println("更新后："+label);
    }



    @Test
    public void testUpdateFans(){
        labelService.updateLableFans("1037282377732329472",1000);
    }


    @Test
    public void testFindAll(){
        List<Label> labels = labelService.findAll();
        System.out.println(labels);
    }





    @Test
    public void testDelete(){
        labelService.delete("1037282377732329472");

    }




}
