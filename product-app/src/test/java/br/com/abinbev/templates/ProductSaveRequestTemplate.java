package br.com.abinbev.templates;


import br.com.abinbev.controller.request.ProductSaveRequest;
import br.com.abinbev.domain.Product;
import br.com.six2six.fixturefactory.Fixture;
import br.com.six2six.fixturefactory.Rule;
import br.com.six2six.fixturefactory.loader.TemplateLoader;

public class ProductSaveRequestTemplate implements TemplateLoader {


    @Override
    public void load() {
        Fixture.of(ProductSaveRequest.class).addTemplate("valid", new Rule(){{
            add("name","Corona");
            add("description","Corona is not just a beer, it's a " +
                                             "lifestyle. An invitation to disconnect " +
                                             "from the routine and enjoy " +
                                             "life more outside, on the beach " +
                                             "and enjoying a sunset with friends.");
            add("price","4.99");
            add("brand","ambev");
        }}).addTemplate("invalid", new Rule(){{
            add("name","       ");
            add("description","");
            add("price","-4.99");
            add("brand","A");
        }});
    }
}
