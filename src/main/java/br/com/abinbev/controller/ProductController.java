package br.com.abinbev.controller;

import br.com.abinbev.controller.request.ProductSaveRequest;
import br.com.abinbev.controller.response.ProductResponse;
import br.com.abinbev.domain.Product;
import br.com.abinbev.mapper.ProductMapper;
import br.com.abinbev.service.ProductService;
import br.com.abinbev.util.Constants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping(path = "/v1/product")
public class ProductController {

    private ProductService productService;
    private ProductMapper productMapper;


    @Autowired
    public ProductController(ProductService productService, ProductMapper productMapper) {
        this.productService = productService;
        this.productMapper = productMapper;

    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE,produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ProductResponse> save(@RequestBody @Valid ProductSaveRequest productSaveRequest){
        Product product = productMapper.requestToDomain(productSaveRequest);
        Product productSave = productService.save(product);
        ProductResponse productResponse = productMapper.domainToResponse(productSave);
        return ResponseEntity.status(HttpStatus.CREATED).body(productResponse);
    }

    @PutMapping(consumes = MediaType.APPLICATION_JSON_VALUE,produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> update(@RequestBody @Valid ProductSaveRequest productSaveRequest,
                                                   @RequestParam("id")
                                                   @NotBlank(message = Constants.MESSAGE_EMPTY_STRING)  String id){
        Product product = productMapper.requestToDomain(productSaveRequest);
        productService.update(product,id);
        Map<String,Object> bodyResponse = new LinkedHashMap<>();
        bodyResponse.put("message","data successfully changed");
        return ResponseEntity.status(HttpStatus.ACCEPTED).body(bodyResponse);
    }

    @DeleteMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> delete(@RequestParam("id")
                                    @NotBlank(message = Constants.MESSAGE_EMPTY_STRING)  String id){
        productService.delete(id);
        Map<String,Object> bodyResponse = new LinkedHashMap<>();
        bodyResponse.put("message","successfully deleted data");
        return ResponseEntity.status(HttpStatus.ACCEPTED).body(bodyResponse);
    }

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE,params =  { "id" })
    public ResponseEntity<ProductResponse> findById(@RequestParam("id")
                                          @NotBlank(message = Constants.MESSAGE_EMPTY_STRING)  String id){
        Product product = this.productService.findById(id);
        ProductResponse productResponse = this.productMapper.domainToResponse(product);
        return ResponseEntity.status(HttpStatus.FOUND).body(productResponse);
    }

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE,params = { "name" })
    public ResponseEntity<List<ProductResponse>> findByNameSortedByName(@RequestParam("name")
                                                        @NotBlank(message = Constants.MESSAGE_EMPTY_STRING)
                                                        @Size(min = 3,max = 100,message = Constants.MESSAGE_SIZE)
                                                        String name){
        List<Product> products = this.productService.findByNameSortedByName(name);
        List<ProductResponse> productResponses = products.stream()
                .map(productMapper::domainToResponse)
                .collect(Collectors.toList());
        return ResponseEntity.status(HttpStatus.FOUND).body(productResponses);
    }
}
