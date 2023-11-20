package com.selivanov.part2.repository;

import com.selivanov.part2.entity.StudentCard;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.function.Consumer;
import java.util.function.Function;
@Component
public class StudentCardRepository {
    private final SessionFactory sessionFactory;

    @Autowired
    public StudentCardRepository(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    public List<StudentCard> findAllStudentCards() {
        Function<Session, List<StudentCard>> getAllStudentCard = (session -> {
            return session.createQuery("from StudentCard", StudentCard.class)
                    .getResultList();
        });

        return executeInTransaction(getAllStudentCard);
    }

    public void saveStudent(StudentCard studentCard) {
        Consumer<Session> saveStudent = (session -> {
            session.persist(studentCard);
        });

        executeInTransaction(saveStudent);
    }

    public void updateStudent(Integer id, StudentCard studentCard) {
        Consumer<Session> updateStudent = (session -> {
            StudentCard retrievedStudentCard = session.get(StudentCard.class, id);
            if (retrievedStudentCard == null) {
                throw new RuntimeException("StudentCard is not found");
            }
            retrievedStudentCard.setParentAddress(studentCard.getParentAddress());
        });

        executeInTransaction(updateStudent);
    }

    private void deleteStudent(Integer id) {
        Consumer<Session> deleteStudent = (session -> {
            session.createMutationQuery("delete from StudentCard where id = :id")
                    .setParameter("id", id)
                    .executeUpdate();
        });

        executeInTransaction(deleteStudent);
    }

    private void executeInTransaction(Consumer<Session> consumer) {
        Function<Session, Void> function = (session -> {
            consumer.accept(session);
            return null;
        });
        executeInTransaction(function);
    }

    private <T> T executeInTransaction(Function<Session, T> function) {
        Session session = null;

        try {
            session = sessionFactory.openSession();

            session.getTransaction().begin();

            T result = function.apply(session);

            session.getTransaction().commit();
            return result;
        } catch (Exception ex) {
            if (session != null) {
                session.getTransaction().rollback(); // 0/4 - success
            }
            throw new RuntimeException(ex);
        } finally {
            if (session != null) {
                session.close();
            }
        }
    }

}
