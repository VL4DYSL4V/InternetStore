package validation.text;

import org.springframework.validation.Errors;

import java.util.Objects;

public class HtmlInsertionChecker implements TextValidator {

    //    private static final Pattern HTML_PATTERN = Pattern.compile("");

    @Override
    public boolean supports(Class<?> aClass) {
        return Objects.equals(aClass, String.class);
    }

    @Override
    public void validate(Object o, Errors errors) {

    }

}
