package com.joao.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Objects;

@Entity
@Table(name = "Employee")
public class Employee {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "fist_name")
    private String fistName;

    @Column(name = "last_name")
    private String lastName;

    @Column(name = "company")
    private String company;


    public Employee() {
    }

    public Employee(String fistName, String lastName, String company) {
        this.fistName = fistName;
        this.lastName = lastName;
        this.company = company;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getFistName() {
        return fistName;
    }

    public void setFistName(String fistName) {
        this.fistName = fistName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company;
    }

    @Override
    public String toString() {
        return "Employee{" +
                "id=" + id +
                ", fistName='" + fistName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", company='" + company + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Employee employee = (Employee) o;
        return Objects.equals(id, employee.id) && Objects.equals(fistName, employee.fistName) && Objects.equals(lastName, employee.lastName) && Objects.equals(company, employee.company);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, fistName, lastName, company);
    }
}
