package br.com.abinbev.repository.entity;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.util.List;

@Document(collection = "USER")
public class UserEntity {

    @Field(value = "ID")
    @Id
    private String id;
    @Field(value = "USERNAME")
    private String username;
    @Field(value = "PASSWORD")
    private String password;
    @DBRef
    private List<RoleEntity> roleEntityList;

    public UserEntity() {
    }

    public UserEntity(String username) {
        super();
        this.username = username;
    }
    public UserEntity(UserEntity user) {
        super();
        this.username = user.getUsername();
        this.password = user.getPassword();
        this.roleEntityList = user.getRoleEntityList();
        this.id = user.getId();
    }
    public UserEntity(String username,String password, List<RoleEntity> roles) {
        super();
        this.username = username;
        this.roleEntityList = roles;
        this.password = password;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

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

    public List<RoleEntity> getRoleEntityList() {
        return roleEntityList;
    }

    public void setRoleEntityList(List<RoleEntity> roleEntityList) {
        this.roleEntityList = roleEntityList;
    }
}
