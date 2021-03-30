package validation;

import dto.CountryForm;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import java.util.Objects;
import java.util.regex.Pattern;

public final class CountryFormValidator implements Validator {

    //Pattern for strings, that starts with letters,
    // possibly have spaces followed by at least 2 letters
    // and ending with at least 2 letters
    private static final Pattern COUNTRY_NAME_PATTERN =
            Pattern.compile("^[a-zA-Z]+([ ]?([a-zA-Z]{2,}))*([a-zA-Z]{2,})$");
    private static final int MIN_NAME_LENGTH = 3;
    private static final int MAX_NAME_LENGTH = 255;

    @Override
    public boolean supports(Class<?> aClass) {
        return Objects.equals(aClass, CountryForm.class);
    }

    @Override
    public void validate(Object o, Errors errors) {
        if(o instanceof CountryForm){
            CountryForm countryForm = (CountryForm) o;
            String countryName = countryForm.getCountryName();
            if (countryName == null) {
                errors.rejectValue("countryName", "country name is null");
            }else if(countryName.length() < MIN_NAME_LENGTH || countryName.length() > MAX_NAME_LENGTH){
                errors.rejectValue("countryName",
                        String.format("Name size must be between %d and %d", MIN_NAME_LENGTH, MAX_NAME_LENGTH));
            } else if(!COUNTRY_NAME_PATTERN.matcher(countryName).matches()){
                errors.rejectValue("countryName", "Invalid country name");
            }
        }
    }
}
