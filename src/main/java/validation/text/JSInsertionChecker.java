package validation.text;

import org.springframework.stereotype.Component;

import java.util.regex.Pattern;

@Component("jsInsertionChecker")
public final class JSInsertionChecker implements TextValidator {

    private static final Pattern JS_PATTERN = Pattern.compile(".*<script>.*</script>.*");

    @Override
    public void validate(String s, Runnable rejectionAction) {
        if (JS_PATTERN.matcher(s).matches()) {
            rejectionAction.run();
        }
    }
}
