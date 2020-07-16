package br.com.abinbev.controller.request;

import br.com.abinbev.util.Constants;
import com.fasterxml.jackson.annotation.JsonProperty;

import javax.validation.constraints.*;
import java.math.BigDecimal;


public class ProductSaveRequest {

    @NotBlank(message = Constants.MESSAGE_EMPTY_STRING)
    @Size(min = 3,max = 100,message = Constants.MESSAGE_SIZE)
    @JsonProperty(value = "name")
    private String name;
    @NotBlank(message = Constants.MESSAGE_EMPTY_STRING)
    @Size(min = 20,max = 200,message = Constants.MESSAGE_SIZE)
    @JsonProperty(value = "description")
    private String description;
    @NotNull(message = Constants.MESSAGE_NULL_OBJECT)
    @Positive(message = Constants.MESSAGE_NUMBER_NEGATIVE_ZERO)
    @JsonProperty(value = "price")
    private BigDecimal price;
    @NotBlank(message = Constants.MESSAGE_EMPTY_STRING)
    @Size(min = 3, max = 100, message = Constants.MESSAGE_SIZE)
    @JsonProperty(value = "brand")
    private String brand;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }
}
