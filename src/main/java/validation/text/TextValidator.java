package validation.text;

public interface TextValidator{

    void validate(String s, Runnable rejectionAction);

}
