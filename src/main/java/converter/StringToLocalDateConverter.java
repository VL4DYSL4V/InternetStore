package converter;

import org.springframework.core.convert.converter.Converter;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;

public final class StringToLocalDateConverter implements Converter<String, LocalDate> {

    private static final DateTimeFormatter formatter = new DateTimeFormatterBuilder()
            .appendOptional( DateTimeFormatter.ofPattern( "yyyy-MM-dd" ) )
            .optionalStart().appendPattern( "dd/MM/yyyy" ).optionalEnd()
            .toFormatter();

    @Override
    public LocalDate convert(String date) {
        return LocalDate.parse(date, formatter);
    }
}
