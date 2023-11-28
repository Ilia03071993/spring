package com.selivanov;

import com.selivanov.config.JpaConfig;
import com.selivanov.entity.*;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.TypedQuery;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.util.List;

public class Main {
    public static void main(String[] args) {
        ApplicationContext context =
                new AnnotationConfigApplicationContext(JpaConfig.class);

        EntityManagerFactory factory = context.getBean(EntityManagerFactory.class);
        EntityManager entityManager = null;
        try {
            entityManager = factory.createEntityManager();
            entityManager.getTransaction().begin();
            //transient
            //  Person person = new Person(null, "Fill");
            //3.
            //3.1
            //   person.setName("Travis");
            //4.1
            /**
             * Использование метода entityManager.merge()
             * вместо entityManager.persist() для обновления сущности,
             * если она уже существует в Persistence Context.
             * Это позволит избежать создания новой сущности в базе данных и излишних операций.
             */
            //    entityManager.merge(person);
            //3.2
//            Person personChangeName = entityManager.find(Person.class, 1);
//            personChangeName.setName("Lion");
//            entityManager.persist(personChangeName);
            //3.3 - ?
//            String sql = "update Person p SET p.name = :newValue WHERE p.id = :id";
//            TypedQuery<Person> personTypedQuery = entityManager.createQuery(sql, Person.class);
//            personTypedQuery.setParameter("newValue", "Julia");
//            personTypedQuery.setParameter("id", 1);
//            personTypedQuery.executeUpdate();

            //persistent
//            entityManager.persist(person);

//            entityManager.createQuery("",Person.class);
//            Person findPerson1 = entityManager.find(Person.class, 1);
//            System.out.println(findPerson2);

            //removed
            //  Person personRemove = entityManager.find(Person.class, 4);
            //  entityManager.remove(personRemove);

            //    System.out.println("after insert");
            //  Person person1 = entityManager.find(Person.class, 1);
            /**
             * 4.2 Refresh – восстанавливает поля объекта на основании значений из БД.
             */
//            person1.setName("Bob");
//            entityManager.refresh(person1);
            /**
             * 4.3 Использование метода entityManager.detach()
             * для отсоединения сущности от Persistence Context,
             * если она больше не нужна для работы.
             * Это поможет освободить ресурсы и уменьшить нагрузку на Persistence Context.
             */
            // entityManager.detach(personRemove);
            //2.1
//            Person person1 = new Person(null, "Jake");
//            Passport passport1 = new Passport(null, 223799,3614);
//            Person person2 = new Person(null, "Julia");
//            Passport passport2 = new Passport(null, 777999, 6780);
//
//            person1.setPassport(passport1);
//            person2.setPassport(passport2);
//
//            entityManager.persist(person1);
//            entityManager.persist(person2);

            //2.2
//            Person person1 = new Person(1, "Fill");
//            Passport passport1 = new Passport(1, 223400,3614);
//            Person person2 = new Person(2, "Fiona");
//            Passport passport2 = new Passport(2, 777900, 6780);
//
//            passport1.setPerson(person1);
//            passport2.setPerson(person2);
//
//            entityManager.merge(passport1);
//            entityManager.merge(passport2);

            //2.3
//            Passport passport = entityManager.find(Passport.class, 1);
//            entityManager.remove(passport);
            //OR
//            Person person = entityManager.find(Person.class, 2);
//            person.setPassport(null);
//            Passport passport2 = entityManager.find(Passport.class, 2);
//            entityManager.remove(passport2);
            //2.4- ?
//            Person person1 = new Person(null, "Fill");
//            Passport passport1 = new Passport(null, 223400, 3614);
//            Person person2 = new Person(null, "Fiona");
//            Passport passport2 = new Passport(null, 777900, 6780);
//            Shop eldorado = new Shop(null, "Eldorado");
//            Shop mvideo = new Shop(null, "Mvideo");

//            passport1.setPerson(person1);
//            passport2.setPerson(person2);
//            entityManager.persist(passport1);
//            entityManager.persist(passport2);
//            person1.setShops(List.of(eldorado, mvideo));
//            person2.setShops(List.of(mvideo));

//            Person person = entityManager.find(Person.class, 1);
//            entityManager.remove(person);
            //2.4 true
//            Teacher teacher1 = new Teacher(null, "Linda");
//            Teacher teacher2 = new Teacher(null, "Fiona");
//            Student student1 = new Student(null, "Mikel");
//            Student student2 = new Student(null, "Travers");
//
//            student1.setTeachers(List.of(teacher1));
//            student2.setTeachers(List.of(teacher2));
//
//            Student student = entityManager.find(Student.class, 1);
//            entityManager.remove(student);
            //2.5, 2.6
            Employee employee1 = new Employee(null, "Steven");
            Employee employee2 = new Employee(null, "Vlad");
            Employee employee3 = new Employee(null, "Elina");
            Employee employee4 = new Employee(null, "Mark");
            Employee employee5 = new Employee(null, "Lisa");
            Employee employee6 = new Employee(null, "Ivan");
            Department department1 = new Department(null, "IT");
            Department department2 = new Department(null, "HR");
            Department department3 = new Department(null, "New Department");
            department1.setEmployees(List.of(employee1, employee2)); // - ?
            department2.setEmployees(List.of(employee2));
            department3.setEmployees(List.of(employee1, employee2));
//            entityManager.persist(department1);
//            entityManager.persist(department2);
//            Department department = entityManager.find(Department.class, 6);
//            entityManager.remove(department);
//            entityManager.merge(department3);
            Department department = entityManager.find(Department.class, 5);
            department.setEmployees(List.of(employee6));

            entityManager.persist(department);
            entityManager.refresh(department);

            entityManager.getTransaction().commit();

            //detached

        } catch (Exception ex) {
            if (entityManager != null) {
                entityManager.getTransaction().rollback();
            }
            throw new RuntimeException(ex);
        } finally {
            if (entityManager != null) {
                entityManager.close();
            }
        }
    }
}