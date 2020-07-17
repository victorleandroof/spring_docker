package br.com.abinbev.repository;

import br.com.abinbev.repository.entity.ProductEntity;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductRepository extends MongoRepository<ProductEntity,String> {
        List<ProductEntity> findByNameOrderByNameAsc(String name);
}
