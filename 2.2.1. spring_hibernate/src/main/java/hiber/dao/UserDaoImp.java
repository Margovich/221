package hiber.dao;

import hiber.model.User;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.persistence.NoResultException;
import javax.persistence.TypedQuery;
import java.util.List;

@Repository
public class UserDaoImp implements UserDao {

    @Autowired
    private SessionFactory sessionFactory;

    @Override
    public void add(User user) {
        sessionFactory.getCurrentSession().save(user);
        }


    @Override
    @SuppressWarnings("unchecked")
    public List<User> listUsers() {
        TypedQuery<User> query = sessionFactory.getCurrentSession().createQuery("SELECT us FROM User us " + "LEFT JOIN FETCH us.car");
        return query.getResultList();
    }

    @Override
    public User getUser(String model, int series) {
        User result;
        try {
            TypedQuery<User> query = sessionFactory.getCurrentSession().createQuery("SELECT us FROM User us JOIN FETCH us.car с WHERE с.model = :Model AND с.series = :Series", User.class);
            query.setParameter("Model",model);
            query.setParameter("Series",series);
            result = query.getSingleResult();
            return result;
        }catch (NoResultException ignored) {
        }
        return null;
    }
}
