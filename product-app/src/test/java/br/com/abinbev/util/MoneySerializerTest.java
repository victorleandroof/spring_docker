package br.com.abinbev.util;

import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializerProvider;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.io.StringWriter;
import java.io.Writer;
import java.math.BigDecimal;

public class MoneySerializerTest {

    private ObjectMapper mapper;
    private MoneySerializer moneySerializer;
    private Writer jsonWriter;
    private JsonGenerator jsonGenerator;
    private SerializerProvider serializerProvider;

    @BeforeEach
    public void setup() throws IOException {
        mapper = new ObjectMapper();
        moneySerializer = new MoneySerializer();
        jsonWriter = new StringWriter();
        jsonGenerator = new JsonFactory().createGenerator(jsonWriter);
        serializerProvider = mapper.getSerializerProvider();
    }

    @Test
    public void shouldValidateSerializer() throws IOException {
        BigDecimal value = new BigDecimal("100");
        moneySerializer.serialize(value,jsonGenerator,serializerProvider);
        jsonGenerator.flush();
        Assertions.assertEquals("\"100,00\"",jsonWriter.toString());
    }

}
