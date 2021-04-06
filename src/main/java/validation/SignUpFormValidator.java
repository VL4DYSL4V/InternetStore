package validation;

import dto.SignUpForm;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import java.util.Objects;
import java.util.regex.Pattern;

@Component
public final class SignUpFormValidator implements Validator {

    private static final Pattern USER_NAME_PATTERN =
            Pattern.compile("^[a-zA-Z0-9]([a-zA-Z0-9]|[_]|[-]){2,}");
    private static final Pattern EMAIL_PATTERN =
            Pattern.compile("[^@ \\t\\r\\n]+@[^@ \\t\\r\\n]+\\.[^@ \\t\\r\\n]+");
    private static final Pattern PASSWORD_PATTERN =
            Pattern.compile("^(?=.*?[A-Z])(?=.*?[a-z])(?=.*?[0-9])(?=.*?[#?!@$ %^&*-]).{8,}$");

    private static final Pattern PHONE_NUMBER_PATTERN =
            Pattern.compile("[0-9]+");

    private static final int MIN_NAME_LENGTH = 3;
    private static final int MAX_NAME_LENGTH = 60;
    private static final int MIN_PASSWORD_LENGTH = 8;
    private static final int MAX_PASSWORD_LENGTH = 35;
    private static final int MIN_PHONE_LENGTH = 8;
    private static final int MAX_PHONE_LENGTH = 14;
    private static final int MAX_EMAIL_LENGTH = 255;

    @Override
    public boolean supports(Class<?> aClass) {
        return Objects.equals(aClass, SignUpForm.class);
    }

    @Override
    public void validate(Object o, Errors errors) {
        if (o instanceof SignUpForm) {
            SignUpForm signUpForm = (SignUpForm) o;
            checkName(signUpForm.getName(), errors);
            checkEmail(signUpForm.getEmail(), errors);
            checkPassword(signUpForm.getPassword(), signUpForm.getPasswordMatch(), errors);
            checkPhoneNumber(signUpForm.getPhoneNumber(), errors);
        }
    }

    private void checkName(String name, Errors errors) {
        if (name.length() < MIN_NAME_LENGTH) {
            errors.rejectValue("name", "user.validation.name.tooShort", "Name is too short");
        } else if (name.length() > MAX_NAME_LENGTH) {
            errors.rejectValue("name", "user.validation.name.tooLong", "Name is too long");
        } else if (!USER_NAME_PATTERN.matcher(name).matches()) {
            errors.rejectValue("name", "user.validation.name.invalid", "Invalid name");
        }
    }

    private void checkEmail(String email, Errors errors) {
        if (email.length() > MAX_EMAIL_LENGTH) {
            errors.rejectValue("email", "user.validation.email.tooLong", "Email is too long");
        } else if (!EMAIL_PATTERN.matcher(email).matches()) {
            errors.rejectValue("email", "user.validation.email.invalid", "Invalid email");
        }
    }

    private void checkPassword(String password, String passwordMatch, Errors errors) {
        if (password.length() < MIN_PASSWORD_LENGTH) {
            errors.rejectValue("password", "user.validation.password.tooShort", "Password is too short");
        } else if (password.length() > MAX_PASSWORD_LENGTH) {
            errors.rejectValue("password", "user.validation.password.tooLong", "Password is too long");
        } else if (!PASSWORD_PATTERN.matcher(password).matches()) {
            errors.rejectValue("password", "user.validation.password.tooWeak", "Password is too weak");
        } else if (passwordMatch.isEmpty()) {
            errors.rejectValue("passwordMatch", "user.validation.passwordMatch.repeat", "Repeat password");
        } else if (!Objects.equals(password, passwordMatch)) {
            errors.rejectValue("passwordMatch", "user.validation.passwordMatch.mismatch", "Passwords must match");
        }
    }

    private void checkPhoneNumber(String phoneNumber, Errors errors) {
        if (phoneNumber.length() < MIN_PHONE_LENGTH) {
            errors.rejectValue("phoneNumber", "user.validation.phoneNumber.tooShort", "Phone number is too short");
        } else if(phoneNumber.length() > MAX_PHONE_LENGTH){
            errors.rejectValue("phoneNumber", "user.validation.phoneNumber.tooLong", "Phone number is too long");
        } else if (!PHONE_NUMBER_PATTERN.matcher(phoneNumber).matches()) {
            errors.rejectValue("phoneNumber", "user.validation.phoneNumber.invalid", "Invalid phone number");
        }
    }
}
