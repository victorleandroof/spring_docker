package br.com.abinbev.controller.response;

import br.com.abinbev.util.MoneySerializer;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import java.math.BigDecimal;

public class ProductResponse {

    @JsonProperty(value = "id")
    private String id;
    @JsonProperty(value = "name")
    private String name;
    @JsonProperty(value = "description")
    private String description;
    @JsonProperty(value = "price")
    @JsonSerialize(using = MoneySerializer.class)
    private BigDecimal price;
    @JsonProperty(value = "brand")
    private String brand;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

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
