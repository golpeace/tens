package com.tensquare;

import com.tensquare.base.pojo.Label;

import java.beans.PropertyDescriptor;
import java.lang.reflect.Method;

/**
 * @author 黑马程序员
 * @Company http://www.ithiema.com
 */
public class ReflactTest {

    public static void main(String[] args)throws Exception {
        //这个是对象
        Label label = new Label();
        label.setState("1");
        //这个是对象的字节码
        Class clazz = label.getClass();

        PropertyDescriptor pd = new PropertyDescriptor("labelname",clazz);

        Method wm = pd.getWriteMethod();//setLabelname();
        wm.invoke(label,"Java");//第一个参数：哪个对象的，第二个参数：方法的参数

//        System.out.println(label);

        Method rm = pd.getReadMethod();
//        Object obj = rm.invoke(label,null);
//        System.out.println(obj);

        System.out.println(rm.getReturnType());
    }
}
