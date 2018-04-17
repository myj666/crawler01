package frame.tytm.dao;

import java.io.Serializable;
import java.util.List;

@SuppressWarnings("all")
public interface BaseDao<T, PK extends Serializable> {

    /**
     * 新增
     *
     * @param entity
     */
    void save(T entity);

    /**
     * 根据id删除
     *
     * @param id
     */
    void deleteById(Serializable id);

    /**
     * 更新
     *
     * @param entity
     */
    void update(T entity);

    /**
     * 通过id查找
     *
     * @param id
     * @return 实体
     */
    T findById(Serializable id);

    /**
     * 查找所有
     *
     * @return
     */
    List<T> findAll();

}
