package com.yz.common.core.service;

import com.yz.common.core.dao.BaseDao;
import org.springframework.util.CollectionUtils;

import java.util.List;

/**
 * @author yangzhao
 * @Description
 * @Date create by 11:29 18/1/2
 */
public abstract class BaseServiceManager<E, DAO extends BaseDao> implements BaseService<E>{

    public DAO dao;

    public DAO getDao() {
        return dao;
    }

    public abstract void setDao(DAO dao);

    /**
     * 添加
     *
     * @param e
     * @return
     */
    @Override
    public boolean insert(E e) {
        if(e==null){
            throw new NullPointerException();
        }
        return dao.insertSelective(e);
    }

    /**
     * 批量添加
     * @param list
     * @return
     */
	@Override
    public boolean insertList(List<E> list) {
        boolean b = dao.insertList(list);
        return b;
	}

    /**
     * 删除
     *
     * @param id
     * @return
     */
    @Override
    public boolean deleteById(Long id) {
        return dao.deleteByPrimaryKey(id);
    }

    @Override
    public boolean deleteByIdList(Long[] ids) {
        boolean b = dao.deleteByIdList(ids);
        return b;
    }

    /**
     * 修改
     *
     * @param e
     * @return
     */
    @Override
    public boolean update(E e) {
        if(e==null){
            throw new NullPointerException();
        }
        return dao.updateByPrimaryKeySelective(e);
    }

    /**
     * 查询一条记录
     *
     * @param e
     * @return
     */
    @Override
    public E selectOne(E e) {
        List<E> select = dao.select(e);
        if (CollectionUtils.isEmpty(select)){
            return null;
        }
        return select.get(0);
    }

    /**
     * 分页查询
     *
     * @param e
     * @return
     */
    @Override
    public abstract List<E> selectPageList(E e, int pageIndex, int pageSize);

    @Override
    public List<E> selectList(E e) {
        return dao.select(e);
    }

    @Override
    public E selectById(Long id) {
        E o = (E) dao.selectByPrimaryKey(id);
        return o;
    }

    @Override
    public List<E> selectListByIdList(List<Long> idList) {
        return dao.selectByIdList(idList);
    }
}
