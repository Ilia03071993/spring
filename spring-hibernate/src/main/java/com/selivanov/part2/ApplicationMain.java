package com.selivanov.part2;

import com.selivanov.part2.config.AppConfig;
import com.selivanov.part2.entity.Profession;
import com.selivanov.part2.entity.Student;
import com.selivanov.part2.entity.StudentCard;
import com.selivanov.part2.entity.Teacher;
import com.selivanov.part2.repository.StudentCardRepository;
import com.selivanov.part2.repository.StudentRepository;
import com.selivanov.part2.repository.TeacherRepository;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.util.List;

public class ApplicationMain {
    public static void main(String[] args) {
        AnnotationConfigApplicationContext context =
                new AnnotationConfigApplicationContext(AppConfig.class);

//        Profession profession = new Profession(null,  "IT");

        Teacher teacher = new Teacher(null, "Linda");
        Student student = new Student(null, "Jake");
        Student student2 = new Student(null, "Oliver");
        StudentCard studentCard = new StudentCard(null, "st. Bolshaya, h.19");
//        StudentRepository studentRepositoryBean = context.getBean(StudentRepository.class);
//        studentRepositoryBean.saveStudent(student);
//        studentRepositoryBean.saveStudent(student2);
//        StudentCardRepository studentCardRepositoryBean = context.getBean(StudentCardRepository.class);
//        studentCardRepositoryBean.saveStudent(studentCard);
//        List<StudentCard> allStudentCards = studentCardRepositoryBean.findAllStudentCards();
//        List<Student> allStudents = studentRepositoryBean.findAllStudents();


//        profession.setStudents(List.of(student,student2));
//        studentRepositoryBean.saveStudent(student);
//        teacher.setStudents(List.of(student,student2));
//        TeacherRepository teacherRepositoryBean = context.getBean(TeacherRepository.class);
//       teacherRepositoryBean.saveTeacher(teacher);

    }
}