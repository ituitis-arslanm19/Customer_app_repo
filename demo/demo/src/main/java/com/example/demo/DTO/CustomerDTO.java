package com.example.demo.DTO;

import com.example.demo.model.Role;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;


import java.util.ArrayList;
import java.util.Collection;

@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode

public class CustomerDTO {


    private Integer Id;
    private String name;
    private String username;
    private String password;



    private Collection<Role> roles=new ArrayList<>();


}
