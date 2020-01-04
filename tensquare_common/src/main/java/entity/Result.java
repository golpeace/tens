package entity;

import java.io.Serializable;

/**
 * 服务端响应结果封装类
 * @author 黑马程序员
 * @Company http://www.ithiema.com
 */
public class Result implements Serializable{

    private boolean flag;//是否成功
    private int code;//状态码
    private String message;//执行结果信息
    private Object data;//响应数据  （有可能有，也有可能没有。增删改方法没有此信息）

    public Result(){

    }


    /**
     * 增删改
     * @param flag
     * @param code
     * @param message
     */
    public Result(boolean flag, int code, String message) {
        this.flag = flag;
        this.code = code;
        this.message = message;
    }

    /**
     * 查询
     * @param flag
     * @param code
     * @param message
     * @param data
     */
    public Result(boolean flag, int code, String message, Object data) {
        this.flag = flag;
        this.code = code;
        this.message = message;
        this.data = data;
    }

    public boolean isFlag() {
        return flag;
    }

    public void setFlag(boolean flag) {
        this.flag = flag;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return "Result{" +
                "flag=" + flag +
                ", code=" + code +
                ", message='" + message + '\'' +
                ", data=" + data +
                '}';
    }
}
