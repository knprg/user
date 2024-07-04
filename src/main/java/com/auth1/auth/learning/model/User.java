package com.auth1.auth.learning.model;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.ManyToMany;
import lombok.Getter;
import lombok.Setter;
import lombok.AllArgsConstructor;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Getter
@Setter
public class User extends BaseModel{
    private String name;
    private String email;
    private String password;
    @ManyToMany(fetch= FetchType.EAGER)
    //private Set<Role> roles = new HashSet<>();
    private List<Role> roles;
    private boolean isEmailVerified;
    public User(){
        //this.roles = new ArrayList<>();
    }
    public User(String name, String email, String password) {
        this.name = name;
        this.email = email;
        this.password = password;
        this.isEmailVerified=false;
        this.roles=new ArrayList<>();
    }



}
