package validation.text;

import org.springframework.stereotype.Component;

@Component
public final class HtmlInsertionChecker implements TextValidator {

    //    private static final Pattern HTML_PATTERN = Pattern.compile("");

    @Override
    public void validate(String s, Runnable rejectionAction) {

    }
}
