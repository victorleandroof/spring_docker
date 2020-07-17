package br.com.abinbev.util;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class ConstantsTest {

    @Test
    public void shouldValidateConstants(){
        Constants constants = new Constants();
        Assertions.assertEquals(Constants.MESSAGE_NOT_FOUND,constants.MESSAGE_NOT_FOUND);
        Assertions.assertEquals(Constants.MESSAGE_NULL_OBJECT,constants.MESSAGE_NULL_OBJECT);
        Assertions.assertEquals(Constants.MESSAGE_EMPTY_STRING,constants.MESSAGE_EMPTY_STRING);
        Assertions.assertEquals(Constants.MESSAGE_NUMBER_NEGATIVE_ZERO,constants.MESSAGE_NUMBER_NEGATIVE_ZERO);
        Assertions.assertEquals(Constants.MESSAGE_SIZE,constants.MESSAGE_SIZE);
    }
}
