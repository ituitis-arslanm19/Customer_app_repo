package com.example.demo.model;

import lombok.*;


import javax.persistence.*;
import java.util.ArrayList;
import java.util.Collection;

import static javax.persistence.GenerationType.AUTO;

@Table
@Data
@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
public class Customer {

    @Id
    @GeneratedValue(strategy = AUTO)
    private Integer Id;
    private String name;
    private String username;
    private String password;

    @ManyToMany()
    private Collection<Role> roles=new ArrayList<>();


   // public Customer(String name){this.name=name;};
}
