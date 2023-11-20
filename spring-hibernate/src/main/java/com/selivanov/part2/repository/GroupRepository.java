package com.selivanov.part2.repository;

import com.selivanov.part2.entity.Group;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.function.Consumer;
import java.util.function.Function;

@Component
public class GroupRepository {
    private final SessionFactory sessionFactory;

    @Autowired
    public GroupRepository(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    public List<Group> findAllGroups() {
        Function<Session, List<Group>> getAllGroups = (session -> {
            return session.createQuery("from Group", Group.class)
                    .getResultList();
        });

        return executeInTransaction(getAllGroups);
    }

    public void saveGroup(Group group) {
        Consumer<Session> saveGroup = (session -> {
            session.persist(group);
        });
        executeInTransaction(saveGroup);
    }

    public void updateGroup(Integer id, Group group) {
        Consumer<Session> updateGroup = (session -> {
            Group retrievedGroup = session.get(Group.class, id);
            if (retrievedGroup == null) {
                throw new RuntimeException("Group is not found");
            }

            retrievedGroup.setName(group.getName());
        });
        executeInTransaction(updateGroup);
    }

    private void deleteGroup(Integer id) {
        Consumer<Session> deleteGroup = (session -> {
            session.createMutationQuery("delete from Group where id = :id")
                    .setParameter("id", id)
                    .executeUpdate();
        });

        executeInTransaction(deleteGroup);
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