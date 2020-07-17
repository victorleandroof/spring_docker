package br.com.abinbev.repository.entity;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;
import org.springframework.security.core.GrantedAuthority;

@Document(collection = "ROLE")
public class RoleEntity implements GrantedAuthority {

    @Id
    private String id;
    @Field("NAME")
    private String name;

    public RoleEntity(String name) {
        this.name = name;
    }

    public RoleEntity() {
    }

    @Override
    public String getAuthority() {
        return this.name;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}