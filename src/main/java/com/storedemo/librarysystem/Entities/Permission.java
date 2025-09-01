//package com.storedemo.librarysystem.Entities;
//
//import jakarta.persistence.*;
//
//@Entity
//@Table(name = "permissions")
//public class Permission {
//
//    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    @Column(name = "permission_id")
//    private Long id;
//
//    @Column(unique = true, nullable = false)
//    private String name;
//
//    @Column
//    private String description;
//
//    public Permission() {}
//
//    public Permission(String name, String description) {
//        this.name = name;
//        this.description = description;
//    }
//
//    public Long getId() {
//        return id;
//    }
//
//    public void setId(Long id) {
//        this.id = id;
//    }
//
//    public String getName() {
//        return name;
//    }
//
//    public void setName(String name) {
//        this.name = name;
//    }
//
//    public String getDescription() {
//        return description;
//    }
//
//    public void setDescription(String description) {
//        this.description = description;
//    }
//}
