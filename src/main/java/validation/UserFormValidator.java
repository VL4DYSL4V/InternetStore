package validation;

import dto.UserForm;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import java.util.Objects;
import java.util.regex.Pattern;

@Component
public final class UserFormValidator implements Validator {

    private static final Pattern USER_NAME_PATTERN =
            Pattern.compile("^[a-zA-Z0-9]([a-zA-Z0-9]|[_]|[-]){2,}");
    private static final Pattern EMAIL_PATTERN =
            Pattern.compile("^[\\\\w!#$%&’*+/=?`{|}~^-]+(?:\\\\.[\\\\w!#$%&’*+/=?`{|}~^-]+)*@(?:[a-zA-Z0-9-]+\\\\.)+[a-zA-Z]{2,6}$");
    //            ^                 # start-of-string
//            (?=.*[0-9])       # a digit must occur at least once
//            (?=.*[a-z])       # a lower case letter must occur at least once
//            (?=.*[A-Z])       # an upper case letter must occur at least once
//            (?=.*[@#$%^&+=])  # a special character must occur at least once
//            (?=\S+$)          # no whitespace allowed in the entire string
//            .{8,}             # anything, at least eight places though
//            $                 # end-of-string
    private static final Pattern PASSWORD_PATTERN =
            Pattern.compile("^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=])(?=\\S+$).{8,}$");

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
        return Objects.equals(aClass, UserForm.class);
    }

    @Override
    public void validate(Object o, Errors errors) {
        if (o instanceof UserForm) {
            UserForm userForm = (UserForm) o;
            checkName(userForm.getName(), errors);
            checkEmail(userForm.getEmail(), errors);
            checkPassword(userForm.getPassword(), userForm.getPasswordMatch(), errors);
            checkPhoneNumber(userForm.getPhoneNumber(), errors);
        }
    }

    private void checkName(String name, Errors errors) {
        if (name == null) {
            errors.rejectValue("name", "name is null");
        } else if (name.length() < MIN_NAME_LENGTH || name.length() > MAX_NAME_LENGTH) {
            errors.rejectValue("name",
                    String.format("Name size must be between %d and %d", MIN_NAME_LENGTH, MAX_NAME_LENGTH));
        } else if (!USER_NAME_PATTERN.matcher(name).matches()) {
            errors.rejectValue("name", "Invalid name");
        }
    }

    private void checkEmail(String email, Errors errors) {
        if (email == null) {
            errors.rejectValue("email", "Email must not be null");
        } else if (email.length() > MAX_EMAIL_LENGTH) {
            errors.rejectValue("email", String.format("Email must be shorter than %d", MAX_EMAIL_LENGTH));
        } else if (!EMAIL_PATTERN.matcher(email).matches()) {
            errors.rejectValue("email", "Invalid email");
        }
    }

    private void checkPassword(String password, String passwordMatch, Errors errors) {
        if (password == null) {
            errors.rejectValue("password", "Password is null");
        } else if (password.length() < MIN_PASSWORD_LENGTH || password.length() > MAX_PASSWORD_LENGTH) {
            errors.rejectValue("password", String.format("Password length must be between %d and %d",
                    MIN_PASSWORD_LENGTH, MAX_PASSWORD_LENGTH));
        } else if (!PASSWORD_PATTERN.matcher(password).matches()) {
            errors.rejectValue("password", "Password is too weak");
        } else if (passwordMatch == null) {
            errors.rejectValue("passwordMatch", "Repeat password");
        } else if (!Objects.equals(password, passwordMatch)) {
            errors.rejectValue("passwordMatch", "Passwords must match");
        }
    }

    private void checkPhoneNumber(String phoneNumber, Errors errors) {
        if (phoneNumber == null) {
            errors.rejectValue("phoneNumber", "Phone number is null");
        } else if (phoneNumber.length() < MIN_PHONE_LENGTH || phoneNumber.length() > MAX_PHONE_LENGTH) {
            errors.rejectValue("phoneNumber", String.format("Phone number length must be between %d and %d",
                    MIN_PHONE_LENGTH, MAX_PHONE_LENGTH));
        } else if (!PHONE_NUMBER_PATTERN.matcher(phoneNumber).matches()) {
            errors.rejectValue("phoneNumber", "Invalid phone number");
        }
    }
}
