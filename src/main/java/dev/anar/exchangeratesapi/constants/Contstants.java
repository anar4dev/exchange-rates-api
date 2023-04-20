package dev.anar.exchangeratesapi.constants;

import java.time.format.DateTimeFormatter;

public class Contstants {

    public static final String DATE_PATTERN = "dd.MM.yyyy";
    public static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern(DATE_PATTERN);
    public static final String FILE_TYPE_XML = ".xml";
    public static final String CBAR_API_BASE_URL = "https://www.cbar.az/currencies/";
    public static final String CURRENCIES_NODE = "Xarici valyutalar";

}
