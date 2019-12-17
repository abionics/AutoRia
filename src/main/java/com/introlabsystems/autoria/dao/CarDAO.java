package com.introlabsystems.autoria.dao;

import com.introlabsystems.autoria.model.Car;
import com.introlabsystems.autoria.utils.HibernateSessionFactoryUtil;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.List;

public class CarDAO {
    
    private SessionFactory sessionFactory = HibernateSessionFactoryUtil.getSessionFactory();

    public Car findById(int id) {
        return sessionFactory.openSession().get(Car.class, id);
    }

    public Car findByUrl(String url) {
        Session session = sessionFactory.openSession();
        CriteriaBuilder builder = session.getCriteriaBuilder();
        CriteriaQuery<Car> criteria = builder.createQuery(Car.class);
        Root<Car> root = criteria.from(Car.class);
        criteria.select(root).where(builder.equal(root.get("url"), url));
        Query<Car> query = session.createQuery(criteria);
        Car car = query.getSingleResult();
        session.close();
        return car;
    }

    public void save(Car Car) {
        Session session = sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();
        session.save(Car);
        transaction.commit();
        session.close();
    }

    public void update(Car Car) {
        Session session = sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();
        session.update(Car);
        transaction.commit();
        session.close();
    }

    public void delete(Car Car) {
        Session session = sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();
        session.delete(Car);
        transaction.commit();
        session.close();
    }

    public List<Car> findAll() {
        return (List<Car>) sessionFactory.openSession().createQuery("From Car").list();
    }
}
