package com.skullprogrammer.project.model;

import lombok.Data;

//@Entity
@Data
public class Role {

//    @Id
//    @GeneratedValue(strategy = GenerationType.TABLE)
    private Long id;
    private String name;
}
