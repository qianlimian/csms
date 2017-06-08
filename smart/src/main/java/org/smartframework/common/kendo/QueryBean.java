package org.smartframework.common.kendo;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import org.smartframework.utils.helper.JsonHelper;
import org.smartframework.utils.helper.StringHelper;

import javax.persistence.TypedQuery;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @description kendoGrid的高级查询参数
 * @author zhaochuanfeng
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class QueryBean {

    /**
     * 当前页数
     */
    private int page = 1;

    /**
     * 每页显示个数
     */
    private int pageSize = 30;

    /**
     * 偏移量 pageSize*(page-1)
     */
    private int skip = 0;

    /**
     * the same as pageSize
     */
    private int take = 30;

    /**
     * 总记录数
     */
    private int total = 0;

    /**
     * 过滤条件
     */
    private LogicFilter filter;

    /**
     * 排序条件
     */
    private List<Sort> sort = new ArrayList<Sort>();

    /**
     * 构造函数
     */
    public QueryBean(int pageSize) {
        this.pageSize = pageSize;
    }

    public QueryBean(int page, int pageSize) {
        this.page = page > 0 ? page : 1;
        this.pageSize = pageSize;
        this.skip = (page - 1) * this.pageSize;
    }

    public QueryBean(int page, int pageSize, int skip) {
        this.page = page > 0 ? page : 1;
        this.pageSize = pageSize;
        this.skip = skip;
    }

    /**
     * @RequestParam注入需要的构造函数的支持
     */
    public QueryBean() {// jackson调用该构造方法构造新的bean对象
    }

    public QueryBean(String json) {// @RequestParam注解调用该构造函数将json字符串参数传入
    }

    // 名称固定的静态方法，转换json字符串为对象
    public static QueryBean valueOf(String json) throws JsonParseException, JsonMappingException, IOException {
        QueryBean qb = JsonHelper.getBean(json, QueryBean.class);
        return qb;
    }


    /**
     * 获取分页查询结果
     * @param query 查询结果
     * @return 分页查询结果
     */
    public <T> List<T> getResultList(TypedQuery<T> query) {
    	//总记录数
        this.setTotal(query.getResultList().size());
        //查询结果
        List<T> resultList = query.setMaxResults(this.pageSize).setFirstResult(this.skip).getResultList();
        return resultList;
    }

    /**
     * 获取过滤参数
     */
    public Map<String, Object> getFilterMap() {
        LogicFilter lf = this.filter;
        if (lf == null) {
            return null;
        }

        List<Filter> filters = lf.getFilters();
        //[]用于存储查询语句段
        String[] keys = new String[filters.size()];
        //{}用于映射查询参数值
        Map<String, Object> values = new HashMap<String, Object>();

        for (int i = 0; i < filters.size(); i++) {
            Filter filter = filters.get(i);
            if (!StringHelper.isAllEmpty(filter.getField(), filter.getOperator())) {
                keys[i] = filter.getField() + " " + filter.getSymbolOperator() + " :" + filter.getSymbolField();
                values.put(filter.getSymbolField(), filter.getSymbolValue());
            }
        }

        Map<String, Object> map = new HashMap<String, Object>();
        map.put("keys", String.join(" and ", keys));
        map.put("values", values);
        return map;
    }

    /**
     * 获取排序参数
     */
    public String getSortString () {
        List<Sort> sorts = this.sort;
        if (sorts == null || sorts.isEmpty()) {
            return null;
        }

        String[] sortArr = new String[sorts.size()];
        for (int i = 0; i < sorts.size(); i++) {
            Sort sort = sorts.get(i);
            sortArr[i] = sort.getField() + " " + sort.getDir();
        }
        return String.join(",", sortArr);
    }

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    public int getSkip() {
        return skip;
    }

    public void setSkip(int skip) {
        this.skip = skip;
    }

    public int getTake() {
        return take;
    }

    public void setTake(int take) {
        this.take = take;
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public LogicFilter getFilter() {
        return filter;
    }

    public void setFilter(LogicFilter filter) {
        this.filter = filter;
    }

    public List<Sort> getSort() {
        return sort;
    }

    public void setSort(List<Sort> sort) {
        this.sort = sort;
    }
}
