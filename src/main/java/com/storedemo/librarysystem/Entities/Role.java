//package com.storedemo.librarysystem.Entities;
//
//import jakarta.persistence.*;
//
//import java.util.HashSet;
//import java.util.Set;
//
//@Entity
//@Table(name = "roles")
//public class Role {
//
//    @Id
//    @Column(name = "role_id")
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    private Long id;
//
//    @Column
//    private String name;
//
//    @Column
//    private String description;
//
//    @ManyToMany
//    @JoinTable(name = "role_permissions",
//            joinColumns = @JoinColumn(name = "role_id"),
//            inverseJoinColumns = @JoinColumn (name = "permission_id")
//    )
//    private Set<Permission> permissions = new HashSet<>();
//
//    public Role(){}
//
//    public Role(String name, String description) {
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
//
//    public Set<Permission> getPermissions() {
//        return permissions;
//    }
//
//    public void setPermissions(Set<Permission> permissions) {
//        this.permissions = permissions;
//    }
//}
