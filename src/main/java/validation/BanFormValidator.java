package validation;

import dto.BanForm;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import java.time.LocalDateTime;
import java.util.Objects;

public final class BanFormValidator implements Validator {

    @Override
    public boolean supports(Class<?> aClass) {
        return Objects.equals(aClass, BanForm.class);
    }

    @Override
    public void validate(Object o, Errors errors) {
        if(o instanceof BanForm){
            BanForm banForm = (BanForm) o;
            checkUserId(banForm.getUserId(), errors);
            checkBanTypeId(banForm.getBanTypeId(), errors);
            checkCommentId(banForm.getCommentId(), errors);
            checkDateOfBanning(banForm.getWhenBanned(), errors);
        }
    }

    private void checkUserId(Long userId, Errors errors){
        if(userId == null){
            errors.rejectValue("userId", "User id must not be null");
        }
    }

    private void checkCommentId(Long commentId, Errors errors){
        if(commentId == null){
            errors.rejectValue("commentId", "Comment id must not be null");
        }
    }

    private void checkDateOfBanning(LocalDateTime whenBanned, Errors errors){
        if(whenBanned == null){
            errors.rejectValue("whenBanned", "Date of ban must not be null");
        }
    }

    private void checkBanTypeId(Integer banTypeId, Errors errors){
        if(banTypeId == null){
            errors.rejectValue("banTypeId", "BanType id must not be null");
        }
    }
}
