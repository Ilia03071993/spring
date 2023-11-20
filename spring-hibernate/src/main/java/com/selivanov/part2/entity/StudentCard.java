package com.selivanov.part2.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "students_cards")
public class StudentCard {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column(name = "parent_address", nullable = false)
    private String parentAddress;
    @OneToOne(mappedBy = "studentCard", cascade = CascadeType.PERSIST)
    private Student student;
    public StudentCard(){}
    public StudentCard(Integer id, String parentAddress) {
        this.id = id;
        this.parentAddress = parentAddress;
    }

    public Student getStudent() {
        return student;
    }

    public void setStudent(Student student) {
        student.setStudentCard(this);
        this.student = student;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getParentAddress() {
        return parentAddress;
    }

    public void setParentAddress(String parentAddress) {
        this.parentAddress = parentAddress;
    }

    @Override
    public String toString() {
        return "StudentCard{" +
                "id=" + id +
                ", parentAddress='" + parentAddress + '\'' +
                '}';
    }
}