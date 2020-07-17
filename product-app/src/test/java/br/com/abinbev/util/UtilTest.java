package br.com.abinbev.util;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

public class UtilTest {

    @Test
    public void  shouldValidateFormatNumerTwoDecimal(){
        BigDecimal value = new BigDecimal("100");
        String valueReal = Util.formatToReal(value);
        Assertions.assertEquals("100,00",valueReal);
    }

    @Test
    public void  shouldValidateFormatNumerThreeDecimal(){
        BigDecimal value = new BigDecimal("1000");
        String valueReal = Util.formatToReal(value);
        Assertions.assertEquals("1.000,00",valueReal);
    }

    @Test
    public void  shouldValidateFormatNumerFourDecimal(){
        BigDecimal value = new BigDecimal("10000");
        String valueReal = Util.formatToReal(value);
        Assertions.assertEquals("10.000,00",valueReal);
    }
}
