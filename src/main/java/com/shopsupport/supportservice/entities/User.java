package com.shopsupport.supportservice.entities;

import lombok.Data;
import javax.persistence.*;
import java.util.List;


@Entity
@Table(name = "USERS")
@Data
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "USER_ID")
    private int userId;

    @Column(name = "USERNAME")
    private String username;

    private String login;

    @Column(name = "PASSWORD")
    private String password;

    @Column(name = "EMAIL_ADDR")
    private String emailAddr;




    public String getRole() {
        if(roleId ==1)return "ADMIN";
        else if(roleId == 2) return "USER";
        else return null;
    }




    public void setRoleId(int roleId) {
        this.roleId = roleId;
    }

    @Column(name = "ROLE_ID")
    private int roleId;

    public List<String> getAuthorities() {
        if(roleId ==1)return List.of("ADMIN");
        else if(roleId == 2) return List.of("USER");
        else return null;
    }
}

