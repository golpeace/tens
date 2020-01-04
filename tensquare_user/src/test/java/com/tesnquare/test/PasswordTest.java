package com.tesnquare.test;

import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

/**
 * 密码加密
 * @author 黑马程序员
 * @Company http://www.ithiema.com
 */
public class PasswordTest {

    /**
     * MD5加密是单向不可逆的。生成字节数组转成字符串之后可能是任何字符。
     * 例如：
     *      1234加密完后：�ܛ�R�M� 6��1>�U
     *
     * 字符串中的字符并不都是可见字符。
     * 可见字符：
     *    可以通过键盘直接输入的字符。
     *
     * 我们需要把MD5生成的字符串转成全是由可见字符组成的字符串
     * 需要用到BASE64编码。BASE64是可以编码也可以解码的。
     * 编码用BASE64Encoder 解码使用BASE64Decoder
     *
     *
     * MD5的升级版：
     *      MD5Hash : apache shiro
     *      BCrypt  ：spring security
     *      他们都是在做散列算法
     *
     *      就是使用特定关键字作为打散密码的凭据，把密码打散。
     *      我们一般称之为：盐  salt
     *
     *      通常情况下都会用 用户名做盐
     *
     *
     * BASE64：一个三变四的过程
     *      1个字节：占8位
     *
     *      s12 = czEy
     *
     *      s:115
     *      1:49
     *      2:50
     *
     *      00011100 00110011 00000100 00110010
     *          28     51       4       50
     *          c      z        E       y
     *  BASE64是由64个基础字符组成的
     *         a~z  A~Z 0~9  + /
     * @param args
     * @throws Exception
     */
    public static void main(String[] args)throws Exception{

        BASE64Encoder encoder = new BASE64Encoder();
        String str = encoder.encode("s12".getBytes());
        BASE64Decoder decoder = new BASE64Decoder();
        byte[] by = decoder.decodeBuffer("czEy");
        System.out.println(str);
        System.out.println(new String(by,0,by.length));
    }



//    public static void main(String[] args)throws Exception{
//        String password = "1234";
//        MessageDigest md5 = MessageDigest.getInstance("md5");
//        byte[] by = md5.digest(password.getBytes());
//
//        BASE64Encoder encoder = new BASE64Encoder();
//        String str = encoder.encode(by);
//
//        System.out.println(str);
//    }
}
