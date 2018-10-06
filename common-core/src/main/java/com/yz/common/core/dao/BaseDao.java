package com.yz.common.core.dao;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

/**
 * @author yangzhao
 * @Description
 * @Date create by 11:32 18/1/2
 */
public interface BaseDao<E> {

    public final Logger logger = LoggerFactory.getLogger(BaseDao.class);

    boolean deleteByPrimaryKey(Long id);

    boolean insert(E e);

    boolean insertSelective(E e);

    E selectByPrimaryKey(Long id);

    boolean updateByPrimaryKeySelective(E e);

    boolean updateByPrimaryKey(E e);

    boolean insertList(List<E> eList);

    public boolean deleteByIdList(Long[] ids);

    List<E> select(E e);

    List<E> selectByIdList(List<Long> idList);

}
