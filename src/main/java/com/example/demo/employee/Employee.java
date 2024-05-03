package com.example.demo.employee;

import javax.persistence.*;

@Entity
@Table(name = "employee")
public class Employee {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column
    private long empId;
    @Column
    private String empName;
    @Column
    private String empLocation;
    @Column
    private String empContact;
}
