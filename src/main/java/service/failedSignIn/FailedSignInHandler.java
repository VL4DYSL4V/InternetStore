package service.failedSignIn;

import java.time.LocalTime;

public interface FailedSignInHandler {

    LocalTime addAttempt(String userName);

    void reactOnSuccess(String userName);

    LocalTime whenCanRetry(String userName);
}
