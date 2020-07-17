package br.com.abinbev.controller;

import br.com.abinbev.controller.request.ProductSaveRequest;
import br.com.abinbev.controller.request.ProductUpdateRequest;
import br.com.abinbev.controller.response.ProductResponse;
import br.com.abinbev.domain.Product;
import br.com.abinbev.exceptions.DataNotFoundException;
import br.com.abinbev.mapper.ProductMapper;
import br.com.abinbev.service.ProductService;
import br.com.abinbev.util.Constants;
import br.com.six2six.fixturefactory.Fixture;
import br.com.six2six.fixturefactory.loader.FixtureFactoryLoader;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mapstruct.factory.Mappers;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.List;

import static org.hamcrest.Matchers.hasSize;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@WebMvcTest(ProductController.class)
public class ProductControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private ProductService productService;

    @MockBean
    private ProductMapper productMapper;

    @BeforeEach
    public void setup(){
        MockitoAnnotations.initMocks(this);
        FixtureFactoryLoader.loadTemplates("br.com.abinbev.templates");
    }


    @Test
    void whenFindByIdValidThenReturnProduct() throws Exception {
        String id = "c4ca4238a0b923820dcc509a6f75849b";
        Product product = Fixture.from(Product.class).gimme("valid");
        ProductResponse productResponse = Fixture.from(ProductResponse.class).gimme("valid");
        Mockito.when(productService.findById(Mockito.anyString())).thenReturn(product);
        Mockito.when(productMapper.domainToResponse(Mockito.any())).thenReturn(productResponse);
        mockMvc.perform( MockMvcRequestBuilders
                .get(String.format("/v1/product?id=%s",id))
                .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isFound())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").value(product.getId()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.name").value(product.getName()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.description").value(product.getDescription()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.price").value("4,99"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.brand").value(product.getBrand()));
        Mockito.verify(productService,Mockito.times(1)).findById(Mockito.anyString());
        Mockito.verify(productMapper,Mockito.times(1)).domainToResponse(Mockito.any());
    }

    @Test
    void whenFindByIdInvalidThenReturnError() throws Exception {
        String id = "c4ca4238a0b";
        Mockito.when(productService.findById(Mockito.anyString())).thenThrow(DataNotFoundException.class);
        mockMvc.perform( MockMvcRequestBuilders
                .get(String.format("/v1/product?id=%s",id))
                .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isNotFound())
                .andExpect(MockMvcResultMatchers.jsonPath("$.message").value(Constants.MESSAGE_NOT_FOUND));
    }


    @Test
    void whenFindByNameValidThenReturnProduct() throws Exception {
        String name = "Corona";
        List<Product> products = Fixture.from(Product.class).gimme(1,"valid");
        ProductResponse productResponse = Fixture.from(ProductResponse.class).gimme("valid");
        Mockito.when(productService.findByNameSortedByName(Mockito.anyString())).thenReturn(products);
        Mockito.when(productMapper.domainToResponse(Mockito.any())).thenReturn(productResponse);
        mockMvc.perform( MockMvcRequestBuilders
                .get(String.format("/v1/product?name=%s",name))
                .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isFound())
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].id").value(products.get(0).getId()))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].name").value(products.get(0).getName()))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].description").value(products.get(0).getDescription()))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].price").value("4,99"))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].brand").value(products.get(0).getBrand()));
        Mockito.verify(productService,Mockito.times(1)).findByNameSortedByName(Mockito.anyString());
        Mockito.verify(productMapper,Mockito.times(1)).domainToResponse(Mockito.any());
    }

    @Test
    void whenDeleteProductThenReturnSucess() throws Exception {
        String id = "c4ca4238a0b";
        Mockito.doNothing().when(productService).delete(Mockito.anyString());
        mockMvc.perform( MockMvcRequestBuilders
                .delete(String.format("/v1/product?id=%s",id))
                .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isAccepted())
                .andExpect(MockMvcResultMatchers.jsonPath("$.message").value("successfully deleted data"));
        Mockito.verify(productService,Mockito.times(1)).delete(Mockito.anyString());
    }

    @Test
    void whenUpdateProductThenReturnSucess() throws Exception {
        String id = "c4ca4238a0b923820dcc509a6f75849b";
        ProductUpdateRequest productUpdateRequest = Fixture.from(ProductUpdateRequest.class).gimme("valid");
        Mockito.doNothing().when(productService).update(Mockito.any(),Mockito.anyString());
        String requestContent = objectMapper.writeValueAsString(productUpdateRequest);
        mockMvc.perform( MockMvcRequestBuilders
                .put(String.format("/v1/product?id=%s",id))
                .content(requestContent)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isAccepted())
                .andExpect(MockMvcResultMatchers.jsonPath("$.message").value("data successfully changed"));
        Mockito.verify(productService,Mockito.times(1)).update(Mockito.any(),Mockito.anyString());
        Mockito.verify(productMapper,Mockito.times(1)).requestToDomain(Mockito.any(ProductUpdateRequest.class));
    }

    @Test
    void whenUpdateProductInvalidThenReturnEror() throws Exception {
        String id = "c4ca4238a0b923820dcc509a6f75849b";
        ProductUpdateRequest productUpdateRequest = Fixture.from(ProductUpdateRequest.class).gimme("invalid name");
        Mockito.doNothing().when(productService).update(Mockito.any(),Mockito.anyString());
        String requestContent = objectMapper.writeValueAsString(productUpdateRequest);
        mockMvc.perform( MockMvcRequestBuilders
                .put(String.format("/v1/product?id=%s",id))
                .content(requestContent)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(MockMvcResultMatchers.jsonPath("$.errors[0].field").value("name"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.errors[0].field_errors[0]").value("can't be empty"));
    }

    @Test
    void whenSaveProductThenReturnSucess() throws Exception {
        Product productSave = Fixture.from(Product.class).gimme("valid");
        ProductSaveRequest productSaveRequest = Fixture.from(ProductSaveRequest.class).gimme("valid");
        ProductResponse productResponse = Fixture.from(ProductResponse.class).gimme("valid");
        Mockito.when(productService.save(Mockito.any())).thenReturn(productSave);
        Mockito.when(productMapper.domainToResponse(Mockito.any())).thenReturn(productResponse);
        String requestContent = objectMapper.writeValueAsString(productSaveRequest);
        mockMvc.perform( MockMvcRequestBuilders
                .post("/v1/product")
                .content(requestContent)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isCreated())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").value(productSave.getId()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.name").value(productSave.getName()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.description").value(productSave.getDescription()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.price").value("4,99"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.brand").value(productSave.getBrand()));
        Mockito.verify(productService,Mockito.times(1)).save(Mockito.any());
        Mockito.verify(productMapper,Mockito.times(1)).requestToDomain(Mockito.any(ProductSaveRequest.class));
        Mockito.verify(productMapper,Mockito.times(1)).domainToResponse(Mockito.any());
    }

    @Test
    void whenSaveProductInvalidThenReturnError() throws Exception {
        ProductSaveRequest productSaveRequest = Fixture.from(ProductSaveRequest.class).gimme("invalid");
        String requestContent = objectMapper.writeValueAsString(productSaveRequest);
        mockMvc.perform( MockMvcRequestBuilders
                .post("/v1/product")
                .content(requestContent)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(MockMvcResultMatchers.jsonPath("$.errors").value(hasSize(5)));
    }
}
