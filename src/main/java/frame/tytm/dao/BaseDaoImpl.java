package frame.tytm.dao;

import org.hibernate.FlushMode;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.orm.hibernate5.support.HibernateDaoSupport;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.io.Serializable;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.List;

/**
 * DATA ACCESS OBJECT
 *
 * @param <T>
 * @param <PK>
 */
@SuppressWarnings("all")
@Repository
public class BaseDaoImpl<T, PK extends Serializable> extends HibernateDaoSupport implements BaseDao<T, PK> {
    /**
     * 创建一个Class的对象来获取泛型的class
     */
    private Class<T> clazz;

    /**
     * 获取泛型的Class对象
     */
    /*public BaseDaoImpl() {
        ParameterizedType pt = (ParameterizedType) this.getClass().getGenericSuperclass();
        clazz = (Class<T>) pt.getActualTypeArguments()[0];
    }*/
    public BaseDaoImpl() {
        //第一步，得到当前运行类的class
        Class clazz = this.getClass();
        //第二步，得到运行类的父类的参数化类型
        Type type = clazz.getGenericSuperclass();
        ParameterizedType pType = (ParameterizedType) type;
        //第三步，得到实际类型参数
        Type[] types = pType.getActualTypeArguments();
        Class tClass = (Class) types[0];
        this.clazz = tClass;
    }

    public BaseDaoImpl(Class<T> clazz) {
        this.clazz = clazz;
    }

    /**
     * 按照类型自动注入SessionFactory; 在实例化的时候,Spring按照形参的类型自动注入
     *
     * @param sessionFactory
     */
    @Resource(name = "sessionFactory")
    public void setSuperSessionFactory(SessionFactory sessionFactory) {
        super.setSessionFactory(sessionFactory);
    }

    /**
     * 获取session
     *
     * @return session
     */
    public Session getSession() {
        Session currentSession = this.getHibernateTemplate().getSessionFactory().getCurrentSession();
        return currentSession;
    }

    @Override
    public void save(T entity) {
        this.getHibernateTemplate().save(entity);
    }

    @Override
    public void deleteById(Serializable id) {
        this.getHibernateTemplate().delete(findById(id));
    }

    @Override
    public void update(T entity) {
        this.getHibernateTemplate().update(entity);
    }

    @Override
    public T findById(Serializable id) {
        T t = getHibernateTemplate().get(clazz, id);
        return t;
    }

    @Override
    public List<T> findAll() {
        List<T> ts = (List<T>) this.getHibernateTemplate().find("from " + clazz.getSimpleName());
        return ts;
    }

    /**
     * 使用下面方法需要取消HibernateDaoSupport的继承
     */
    /*private HibernateTemplate hibernateTemplate;

    public HibernateTemplate getHibernateTemplate() {
        return hibernateTemplate;
    }

    public void setHibernateTemplate(HibernateTemplate hibernateTemplate) {
        this.hibernateTemplate = hibernateTemplate;
    }*/

}
