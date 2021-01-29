package validator;

import constraint.CurrencyConstraint;
import entity.Currency;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.regex.Pattern;

public final class CurrencyValidator implements
        ConstraintValidator<CurrencyConstraint, Currency> {

    private static final Pattern currencyNamePattern = Pattern.compile("[A-Z]{3}");

    @Override
    public boolean isValid(Currency currency,
                           ConstraintValidatorContext constraintValidatorContext) {
        if(currency == null){
            return false;
        }
        String currencyName = currency.getCurrencyName();
        if(currencyName == null){
            return false;
        }
        return currency.getId() > 0 &&currencyNamePattern.matcher(currencyName).matches();
    }
}
