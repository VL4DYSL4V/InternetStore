package validation;

import dto.CurrencyForm;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import java.util.Objects;
import java.util.regex.Pattern;

@Component
public final class CurrencyFormValidator implements Validator {

    private static final Pattern CURRENCY_NAME_PATTERN = Pattern.compile("[A-Z]");
    private static final int NAME_LENGTH = 3;

    @Override
    public boolean supports(Class<?> aClass) {
        return Objects.equals(aClass, CurrencyForm.class);
    }

    @Override
    public void validate(Object o, Errors errors) {
        if(o instanceof CurrencyForm){
            CurrencyForm currencyForm = (CurrencyForm) o;
            String currencyName = currencyForm.getCurrencyName();
            if(currencyName.length() != NAME_LENGTH){
                errors.rejectValue("currencyName", "wrongCurrencyNameLength", "Name length must be 3");
            } else if(! CURRENCY_NAME_PATTERN.matcher(currencyName).matches()){
                errors.rejectValue("currencyName", "invalidCurrencyName", "Invalid name");
            }
        }

    }

}
