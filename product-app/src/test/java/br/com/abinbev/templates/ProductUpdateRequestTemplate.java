package br.com.abinbev.templates;


import br.com.abinbev.controller.request.ProductSaveRequest;
import br.com.abinbev.controller.request.ProductUpdateRequest;
import br.com.six2six.fixturefactory.Fixture;
import br.com.six2six.fixturefactory.Rule;
import br.com.six2six.fixturefactory.loader.TemplateLoader;

public class ProductUpdateRequestTemplate implements TemplateLoader {


    @Override
    public void load() {
        Fixture.of(ProductUpdateRequest.class).addTemplate("valid", new Rule(){{
            add("name","Corona");
        }}).addTemplate("invalid name",new Rule(){{
            add("name","                    ");
        }});
    }
}
