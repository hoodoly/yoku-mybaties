package com.yoku.mybatis.dao;

import com.yoku.mybatis.model.PageData;
import org.mybatis.spring.SqlSessionTemplate;
import org.mybatis.spring.support.SqlSessionDaoSupport;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Desc:
 * Author: <a href="xiahj@terminus.io">xiahj</a>
 * Date: 2016/12/7
 */
public abstract class BaseDao<T> extends SqlSessionDaoSupport {

    //1.2.0以上mybatis-spring将SqlSessionDaoSupport中SqlSessionTemplate移除需手动注入
    //否者启动报错: Property 'sqlSessionFactory' or 'sqlSessionTemplate' are required
    @Autowired
    public void setSqlSessionTemplate(SqlSessionTemplate sqlSessionTemplate) {
        super.setSqlSessionTemplate(sqlSessionTemplate);
    }

    public T load(Long id){
        return getSqlSession().selectOne("load", id);
    }

    public Boolean create(T t){
        return getSqlSession().insert("create", t) == 1;
    }

    public PageData<T> page(int pageNo, int pageSize, Map<String, Object> map){
        pageNo = pageNo >0 ? pageNo : 1;
        pageSize = pageSize > 0 ? pageSize : 20;
        int startIndex = (pageNo - 1) * pageSize;
        long total = getSqlSession().selectOne("count", map);
        if (total <= 0){
            return new PageData<T>(0, new ArrayList<T>(0));
        }
        map.put("startIndex", startIndex);
        map.put("pageSize", pageSize);
        List<T> datas = getSqlSession().selectList("page", map);
        return new PageData<T>(total, datas);
    }
}
