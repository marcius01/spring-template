package com.skullprogrammer.project.model;

import com.skullprogrammer.project.utility.StringToListConverter;

import javax.persistence.*;
import java.util.List;

@Entity(name = "userEntity")
public class UserEntity {

    private Long id;
    private String username;
    private String password;
    private List<String> roles;

    @Id
    @GeneratedValue(strategy=GenerationType.TABLE, generator = "table_generator")
    @TableGenerator(name="table_generator", table="hibernate_sequences", pkColumnName="sequence_name",valueColumnName="next_val",pkColumnValue="policyAction",initialValue=1,allocationSize=1)
//    @GenericGenerator(name = "table_generator", strategy = "native")
//    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "table_generator")
//    @GenericGenerator(name = "table_generator", strategy = "native")
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Column(unique = true)
    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Column(name = "roles")
    @Convert(converter = StringToListConverter.class)
    public List<String> getRoles() {
        return roles;
    }

    public void setRoles(List<String> roles) {
        this.roles = roles;
    }
}
