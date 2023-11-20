package com.selivanov.part1.repository;

import com.selivanov.part1.config.CarMapper;
import com.selivanov.part1.entity.Car;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.List;
import java.util.NoSuchElementException;

@Component
public class CarRepository {
    private SessionFactory sessionFactory;

    public CarRepository() {
    }

    @Autowired
    public CarRepository(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    public List<Car> findAllCars() {
        Session session = null;
        try {
            session = sessionFactory.openSession();
            session.getTransaction().begin();

            List<Car> cars = session.createQuery("from Car", Car.class).getResultList();

            session.getTransaction().commit();
            return cars;
        } catch (Exception ex) {
            if (session != null) {
                session.getTransaction().rollback();
            }
            throw new RuntimeException(ex);
        } finally {
            if (session != null) {
                session.close();
            }
        }
    }

    public Car findCarById(String id) {
        Session session = null;
        try {
            session = sessionFactory.openSession();
            session.getTransaction().begin();

            Car car = session.get(Car.class, id);

            session.getTransaction().commit();
            return car;
        } catch (Exception ex) {
            if (session != null) {
                session.getTransaction().rollback();
            }
            throw new RuntimeException(ex);
        } finally {
            if (session != null) {
                session.close();
            }
        }
    }

    public List<Car> findCarByYear(LocalDate date) {
        Session session = null;
        try {
            session = sessionFactory.openSession();
            session.getTransaction().begin();

            Query query = session.createQuery("from Car where date > :date")
                    .setParameter("date", date);
            List<Car> cars = query.list();

            session.getTransaction().commit();
            return cars;
        } catch (Exception ex) {
            if (session != null) {
                session.getTransaction().rollback();
            }
            throw new RuntimeException(ex);
        } finally {
            if (session != null) {
                session.close();

            }
        }
    }

    public void saveCar(Car car) {
        Session session = null;
        try {
            session = sessionFactory.openSession();
            session.getTransaction().begin();

            session.persist(car);

            session.getTransaction().commit();

        } catch (Exception ex) {
            if (session != null) {
                session.getTransaction().rollback();
            }
            throw new RuntimeException(ex);
        } finally {
            if (session != null) {
                session.close();
            }
        }
    }

    public void updateCar(Car car, String id) {
        Session session = null;
        try {
            session = sessionFactory.openSession();
            session.getTransaction().begin();

            Car updatableCar = session.get(Car.class, id);
            if (updatableCar == null) {
                throw new NoSuchElementException("No such car in table");
            }

            CarMapper.updateDate(updatableCar, car.getDate());
            CarMapper.updatePrice(updatableCar, car.getPrice());
            CarMapper.updateOwner(updatableCar, car.getOwner());
            CarMapper.updateModel(updatableCar, car.getModel());

            session.getTransaction().commit();
        } catch (Exception ex) {
            if (session != null) {
                session.getTransaction().rollback();
            }
            throw new RuntimeException(ex);
        } finally {
            if (session != null) {
                session.close();
            }
        }
    }

    public void deleteCar(String id) {
        Session session = null;
        try {
            session = sessionFactory.openSession();
            session.getTransaction().begin();

            Car deletableCar = session.get(Car.class, id);
            if (deletableCar == null) {
                throw new NoSuchElementException("No such car in table");
            }
            session.remove(deletableCar);

            session.getTransaction().commit();
        } catch (Exception ex) {
            if (session != null) {
                session.getTransaction().rollback();
            }
            throw new RuntimeException(ex);
        } finally {
            if (session != null) {
                session.close();
            }
        }
    }
}
