package br.com.abinbev.mapper;

import br.com.abinbev.controller.request.ProductSaveRequest;
import br.com.abinbev.controller.request.ProductUpdateRequest;
import br.com.abinbev.controller.response.ProductResponse;
import br.com.abinbev.domain.Product;
import br.com.abinbev.repository.entity.ProductEntity;
import org.mapstruct.Mapper;

@Mapper(componentModel="spring")
public interface ProductMapper {
    Product entityToDomain(ProductEntity productEntity);
    ProductEntity domainToEntity(Product product);
    Product requestToDomain(ProductSaveRequest productSaveRequest);
    Product requestToDomain(ProductUpdateRequest productUpdateRequest);
    ProductResponse domainToResponse(Product product);
}
