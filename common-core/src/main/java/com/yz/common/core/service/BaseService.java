package com.yz.common.core.service;

import java.util.List;

/**
 * @author yangzhao
 * @Description
 * @Date create by 11:15 18/1/2
 */
public interface BaseService<E> {

    /**
     * 添加
     *
     * @param e
     * @return
     */
    public boolean insert(E e);

    /**
     * 删除
     *
     * @param id
     * @return
     */
    public boolean deleteById(Long id);

    /**
     * 批量删除
     *
     * @param ids
     * @return
     */
    public boolean deleteByIdList(Long[] ids);

    /**
     * 修改
     *
     * @param e
     * @return
     */
    public boolean update(E e);

    /**
     * 查询一条记录
     *
     * @param e
     * @return
     */
    public E selectOne(E e);

    /**
     * 根据ID查询一条记录
     *
     * @param id
     * @return
     */
    public E selectById(Long id);

    /**
     * 分页查询
     *
     * @param e
     * @return
     */
    public List<E> selectPageList(E e, int pageIndex, int pageSize);

    /**
     * 根据条件查询所有
     * @return
     */
    public List<E> selectList(E e);

    /**
     * 批量添加
     * @param list
     * @return
     */
    public boolean insertList(List<E> list);

    /**
     * 通过ID获取
     * @param idList
     * @return
     */
    public List<E> selectListByIdList(List<Long> idList);
}
