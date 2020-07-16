package br.com.abinbev.controller.request;

import br.com.abinbev.util.Constants;
import com.fasterxml.jackson.annotation.JsonProperty;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

public class ProductUpdateRequest {

    @NotBlank(message = Constants.MESSAGE_EMPTY_STRING)
    @Size(min = 3,max = 100,message = Constants.MESSAGE_SIZE)
    @JsonProperty(value = "name")
    private String name;


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
