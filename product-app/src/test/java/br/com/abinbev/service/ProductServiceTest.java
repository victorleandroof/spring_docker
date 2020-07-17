package br.com.abinbev.service;


import br.com.abinbev.domain.Product;
import br.com.abinbev.exceptions.DataNotFoundException;
import br.com.abinbev.mapper.ProductMapper;
import br.com.abinbev.repository.ProductRepository;
import br.com.abinbev.repository.entity.ProductEntity;
import br.com.abinbev.service.impl.ProductServiceImpl;
import br.com.six2six.fixturefactory.Fixture;
import br.com.six2six.fixturefactory.loader.FixtureFactoryLoader;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mapstruct.factory.Mappers;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

@ExtendWith(MockitoExtension.class)
public class ProductServiceTest {

    @Mock
    private ProductRepository productRepository;
    @Spy
    private ProductMapper productMapper = Mappers.getMapper(ProductMapper.class);

    @InjectMocks
    private ProductServiceImpl productService;

    @BeforeEach
    public void setup(){
        MockitoAnnotations.initMocks(this);
        FixtureFactoryLoader.loadTemplates("br.com.abinbev.templates");
    }

    @Test
    public void whenSaveProductItShouldReturnProduct(){
        Product product = Fixture.from(Product.class).gimme("valid");
        ProductEntity productEntitySave = Fixture.from(ProductEntity.class).gimme("valid");
        Mockito.when(productRepository.save(Mockito.any())).thenReturn(productEntitySave);
        Product productSave = productService.save(product);
        Assertions.assertEquals(productSave.getName(),productEntitySave.getName());
        Assertions.assertEquals(productSave.getBrand(),productEntitySave.getBrand());
        Assertions.assertEquals(productSave.getDescription(),productEntitySave.getDescription());
        Assertions.assertEquals(productSave.getPrice(),productEntitySave.getPrice());
        Assertions.assertEquals(productSave.getId(),productEntitySave.getId());
        Mockito.verify(productRepository,Mockito.times(1)).save(Mockito.any());
    }

    @Test
    public void whenUpdateProductItShouldNotReturnError(){
        Product product = Fixture.from(Product.class).gimme("valid");
        String id = "c4ca4238a0b923820dcc509a6f75849b";
        ProductEntity productEntityUpdate = Fixture.from(ProductEntity.class).gimme("valid");
        Mockito.when(productRepository.findById(Mockito.anyString())).thenReturn(Optional.of(productEntityUpdate));
        Mockito.when(productRepository.save(Mockito.any())).thenReturn(productEntityUpdate);
        productService.update(product,id);
        Mockito.verify(productRepository,Mockito.times(1)).save(Mockito.any());
    }

    @Test
    public void whenFindByIdValidItShouldReturnProduct(){
        String id = "c4ca4238a0b923820dcc509a6f75849b";
        ProductEntity productEntityFound = Fixture.from(ProductEntity.class).gimme("valid");
        Mockito.when(productRepository.findById(Mockito.anyString())).thenReturn(Optional.of(productEntityFound));
        Product productFound  = productService.findById(id);
        Assertions.assertEquals(productFound.getName(),productEntityFound.getName());
        Assertions.assertEquals(productFound.getBrand(),productEntityFound.getBrand());
        Assertions.assertEquals(productFound.getDescription(),productEntityFound.getDescription());
        Assertions.assertEquals(productFound.getPrice(),productEntityFound.getPrice());
        Assertions.assertEquals(productFound.getId(),productEntityFound.getId());
        Mockito.verify(productRepository,Mockito.times(1)).findById(Mockito.anyString());
    }

    @Test
    public void whenFindByNameValidItShouldReturnProducts(){
        String id = "c4ca4238a0b923820dcc509a6f75849b";
        List<ProductEntity> productEntityList = Fixture.from(ProductEntity.class).gimme(1,"valid");
        Mockito.when(productRepository.findByNameOrderByNameAsc(Mockito.anyString())).thenReturn(productEntityList);
        List<Product> productList  = productService.findByNameSortedByName(id);
        Assertions.assertEquals(productList.get(0).getName(),productEntityList.get(0).getName());
        Assertions.assertEquals(productList.get(0).getBrand(),productEntityList.get(0).getBrand());
        Assertions.assertEquals(productList.get(0).getDescription(),productEntityList.get(0).getDescription());
        Assertions.assertEquals(productList.get(0).getPrice(),productEntityList.get(0).getPrice());
        Assertions.assertEquals(productList.get(0).getId(),productEntityList.get(0).getId());
        Mockito.verify(productRepository,Mockito.times(1)).findByNameOrderByNameAsc(Mockito.anyString());
    }

    @Test
    public void whenFindProductByIdInvalidItShouldReturnError(){
        String id = "c4ca42";
        Mockito.when(productRepository.findById(Mockito.anyString())).thenReturn(Optional.empty());
        Assertions.assertThrows(DataNotFoundException.class,()->{
            productService.findById(id);
        });
    }

    @Test
    public void whenDeleteProductItShouldNotReturnError(){
        String id = "c4ca4238a0b923820dcc509a6f75849b";
        ProductEntity productEntityFound = Fixture.from(ProductEntity.class).gimme("valid");
        Mockito.when(productRepository.findById(Mockito.anyString())).thenReturn(Optional.of(productEntityFound));
        Mockito.doNothing().when(productRepository).deleteById(Mockito.anyString());
        productService.delete(id);
        Mockito.verify(productRepository,Mockito.times(1)).deleteById(Mockito.anyString());
    }

}
