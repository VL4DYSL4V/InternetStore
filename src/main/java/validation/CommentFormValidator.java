package validation;

import dto.CommentForm;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import validation.text.TextValidator;

import java.time.LocalDateTime;
import java.util.Objects;
import java.util.regex.Pattern;

@Component
public final class CommentFormValidator implements Validator {

    private static final Pattern WHITESPACE_PATTERN = Pattern.compile("\\s+");
    private final TextValidator htmlInsertionChecker;
    private final TextValidator jsInsertionChecker;

    public CommentFormValidator(TextValidator htmlInsertionChecker, TextValidator jsInsertionChecker) {
        this.htmlInsertionChecker = htmlInsertionChecker;
        this.jsInsertionChecker = jsInsertionChecker;
    }

    @Override
    public boolean supports(Class<?> aClass) {
        return Objects.equals(aClass, CommentForm.class);
    }

    @Override
    public void validate(Object o, Errors errors) {
        if(o instanceof CommentForm){
            CommentForm commentForm = (CommentForm) o;
            checkUserId(commentForm.getUserId(), errors);
            checkCommentText(commentForm.getCommentText(), errors);
            checkItemId(commentForm.getItemId(), errors);
            checkTimeOfPost(commentForm.getTimeOfPost(), errors);
        }
    }

    private void checkUserId(Long userId, Errors errors){
        if(userId == null){
            errors.rejectValue("userId", "User id must not be null");
        }
    }

    private void checkItemId(Long itemId, Errors errors){
        if(itemId == null){
            errors.rejectValue("itemId", "Item id must not be null");
        }
    }

    private void checkTimeOfPost(LocalDateTime timeOfPost, Errors errors){
        if(timeOfPost == null){
            errors.rejectValue("timeOfPost", "Time of post must not be null");
        }
    }

    private void checkCommentText(String commentText, Errors errors){
        if(commentText == null){
            errors.rejectValue("commentText", "Comment text must not be null");
            return;
        }
        if(WHITESPACE_PATTERN.matcher(commentText).matches()){
            errors.rejectValue("commentText", "Comment text must not consist of whitespaces");
        }
        htmlInsertionChecker.validate(commentText, () -> errors.rejectValue("commentText", "Malicious comment text"));
        jsInsertionChecker.validate(commentText, () -> errors.rejectValue("commentText", "Malicious comment text"));
    }
}
