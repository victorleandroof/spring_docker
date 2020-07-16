package br.com.abinbev.util;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.Locale;

public class Util {

    public static  String formatToReal(BigDecimal valor){
        Locale Local = new Locale("pt","BR");
        DecimalFormat df = new DecimalFormat("#,##0.00", new DecimalFormatSymbols(Local));
        return df.format(valor);
    }



}
