package br.com.abinbev.repository;

import br.com.abinbev.repository.entity.UserEntity;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface UserRepository  extends MongoRepository<UserEntity,String> {
    UserEntity findByUsername(String username);
}
