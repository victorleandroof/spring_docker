package br.com.abinbev.service;

import br.com.abinbev.domain.Product;

import java.util.List;


public interface ProductService {

    void update(Product product, String id);
    void delete(String id);
    List<Product> findByNameSortedByName(String name);
    Product findById(String id);
    Product save(Product product);

}
