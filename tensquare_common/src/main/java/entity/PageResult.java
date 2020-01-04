package entity;

import java.io.Serializable;
import java.util.List;

/**
 * 带有分页的结果集封装
 * @author 黑马程序员
 * @Company http://www.ithiema.com
 */
public class PageResult<E> implements Serializable{

    private long total;//总记录数（不是总页数）
    private List<E> list;//带有分页的结果集

    public long getTotal() {
        return total;
    }

    public void setTotal(long total) {
        this.total = total;
    }

    public List<E> getList() {
        return list;
    }

    public void setList(List<E> list) {
        this.list = list;
    }

    public PageResult(){

    }

    public PageResult(long total, List<E> list) {
        this.total = total;
        this.list = list;
    }
}
