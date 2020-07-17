package br.com.abinbev.repository;

import br.com.abinbev.repository.entity.RoleEntity;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface RoleRepository extends MongoRepository<RoleEntity,String> {
    RoleEntity findByName(String name);
}
