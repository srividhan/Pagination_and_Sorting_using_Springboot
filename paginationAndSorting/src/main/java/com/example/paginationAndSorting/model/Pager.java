package com.example.paginationAndSorting.model;

import jakarta.persistence.*;

@Entity
@Table(name = "/page")
public class Pager {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private Long id;
    @Column(name = "name")
    private String name;
    @Column(name = "age")
    private int age;
    @Column(name = "password")
    private String password;

    public Pager() {
    }

    public Pager(String name, int age, String password) {
        this.name = name;
        this.age = age;
        this.password = password;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public String toString() {
        return "Page [id=" + id + ", name=" + name + ", age=" + age + ", password=" + password + "]";
    }
}
