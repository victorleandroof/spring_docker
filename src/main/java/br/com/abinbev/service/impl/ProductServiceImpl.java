package br.com.abinbev.service.impl;

import br.com.abinbev.domain.*;
import br.com.abinbev.exceptions.DataNotFoundException;
import br.com.abinbev.mapper.ProductMapper;
import br.com.abinbev.repository.ProductRepository;
import br.com.abinbev.repository.entity.ProductEntity;
import br.com.abinbev.service.ProductService;
import br.com.abinbev.util.Constants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ProductServiceImpl implements ProductService {

    private ProductRepository productRepository;
    private ProductMapper productMapper;

    @Autowired
    public ProductServiceImpl(ProductRepository productRepository, ProductMapper productMapper) {
        this.productRepository = productRepository;
        this.productMapper = productMapper;
    }

    @Override
    public void update(Product product, String id) {
        Product productFound = this.findById(id);
        ProductEntity productEntity = productMapper.domainToEntity(productFound);
        productEntity.setName(product.getName());
        this.productRepository.save(productEntity);
    }

    @Override
    public void delete(String id) {
        Product productFound = this.findById(id);
        this.productRepository.deleteById(productFound.getId());
    }

    @Override
    public List<Product> findByNameSortedByName(String name) {
        List<ProductEntity> productEntities = this.productRepository.findByNameOrderByNameAsc(name);
        if(productEntities.isEmpty()){
            throw  new DataNotFoundException(Constants.MESSAGE_NOT_FOUND);
        }
        List<Product> products = productEntities.stream()
                                                .map(productMapper::entityToDomain)
                                                .collect(Collectors.toList());
        return products;
    }

    @Override
    public Product findById(String id) {
        Optional<ProductEntity> productEntityOptional = this.productRepository.findById(id);
        if(!productEntityOptional.isPresent()){
            throw  new DataNotFoundException(Constants.MESSAGE_NOT_FOUND);
        }
        Product product = productMapper.entityToDomain(productEntityOptional.get());
        return product;
    }

    @Override
    public Product save(Product product) {
        ProductEntity productEntity = this.productMapper.domainToEntity(product);
        ProductEntity productEntitySaved = this.productRepository.save(productEntity);
        Product productSaved = this.productMapper.entityToDomain(productEntitySaved);
        return productSaved;
    }
}
