package com.selivanov.part2.repository;

import com.selivanov.part2.entity.Teacher;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.function.Consumer;
import java.util.function.Function;

@Component
public class TeacherRepository {
    private final SessionFactory sessionFactory;

    @Autowired
    public TeacherRepository(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    public List<Teacher> findAllTeachers() {
        Function<Session, List<Teacher>> getAllStudents = (session -> {
            return session.createQuery("from Teacher", Teacher.class)
                    .getResultList();
        });

        return executeInTransaction(getAllStudents);
    }

    public void saveTeacher(Teacher teacher) {
        Consumer<Session> saveStudent = (session -> {
            session.persist(teacher);
        });

        executeInTransaction(saveStudent);
    }

    public void updateTeacher(Integer id, Teacher teacher) {
        Consumer<Session> updateStudent = (session -> {
            Teacher retrievedStudent = session.get(Teacher.class, id);
            if (retrievedStudent == null) {
                throw new RuntimeException("Teacher is not found");
            }
            retrievedStudent.setName(teacher.getName());
        });

        executeInTransaction(updateStudent);
    }

    private void deleteTeacher(Integer id) {
        Consumer<Session> deleteStudent = (session -> {
            session.createMutationQuery("delete from Teacher where id = :id")
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
