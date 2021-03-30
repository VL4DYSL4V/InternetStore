package validation;

import dto.BanTypeForm;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import java.util.Objects;

public final class BanTypeFormValidator implements Validator {

    private static final int MAX_CAUSE_LENGTH = 255;

    @Override
    public boolean supports(Class<?> aClass) {
        return Objects.equals(aClass, BanTypeForm.class);
    }

    @Override
    public void validate(Object o, Errors errors) {
        if(o instanceof BanTypeForm){
            BanTypeForm banTypeForm = (BanTypeForm) o;
            checkCause(banTypeForm.getCause(), errors);
            checkDurationInDays(banTypeForm.getDurationInDays(), errors);
        }
    }

    private void checkCause(String cause, Errors errors){
        if(cause == null){
            errors.rejectValue("cause", "Cause is null");
        }else if(cause.length() > MAX_CAUSE_LENGTH){
            errors.rejectValue("cause", String.format("Cause length must be less than %d", MAX_CAUSE_LENGTH));
        }
    }

    private void checkDurationInDays(int durationInDays, Errors errors){
        if(durationInDays <= 0){
            errors.rejectValue("durationInDays", "Duration must be positive");
        }
    }
}
