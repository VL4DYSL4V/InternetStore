package controller;

import dao.user.UserDao;
import dto.SignInForm;
import entity.User;
import exception.dao.FetchException;
import exception.encryption.EncryptionException;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import service.encryption.EncryptionService;
import service.failedSignIn.FailedSignInHandler;

import javax.servlet.http.HttpSession;
import java.time.LocalTime;
import java.util.Objects;

@Controller
@RequestMapping("/users")
public final class SignInController {

    private final FailedSignInHandler failedSignInHandler;
    private final UserDao userDao;
    private final EncryptionService encryptionService;

    public SignInController(FailedSignInHandler failedSignInHandler,
                            UserDao userDao,
                            @Qualifier("defaultEncryptionService") EncryptionService encryptionService) {
        this.failedSignInHandler = failedSignInHandler;
        this.userDao = userDao;
        this.encryptionService = encryptionService;
    }

    @GetMapping("/sign-in")
    public String signInForm(Model model){
        model.addAttribute("signInForm", new SignInForm());
        return "user/signIn";
    }

    @PostMapping("/sign-in")
    public String signIn(@ModelAttribute("signInForm") SignInForm signInForm,
                         BindingResult bindingResult,
                         HttpSession httpSession){
        User user;
        try{
            user = userDao.getByName(signInForm.getName());
            if(user == null){
                bindingResult.rejectValue("name", "user.signIn.noSuchUser", "No such user");
                return "user/signIn";
            }
        } catch (FetchException e) {
            e.printStackTrace();
            return "user/signIn";
        }
        LocalTime timeBeforeRetry = failedSignInHandler.whenCanRetry(signInForm.getName());
        if(reject(timeBeforeRetry, bindingResult, httpSession)){
            return "user/signIn";
        }
        if(handleSignIn(user, signInForm.getPassword(), bindingResult, httpSession)){
            return "redirect:/main";
        }else{
            return "user/signIn";
        }
    }

    /** Return true if value was rejected, else - false;
     * */
    private boolean reject(LocalTime timeBeforeRetry, BindingResult bindingResult, HttpSession httpSession){
        if(timeBeforeRetry != null) {
            bindingResult.rejectValue("password", "user.signIn.haveToWait", "Wait for: ");
            httpSession.setAttribute("timeBeforeRetry", timeBeforeRetry);
            return true;
        }
        return false;
    }

    /** Return true if signing in was successful, else - false
     * */
    private boolean handleSignIn(User user, String passwordInForm,
                                 BindingResult bindingResult, HttpSession httpSession){
        try {
            String encryptedPasswordInput = encryptionService.getEncrypted(passwordInForm);
            if(Objects.equals(encryptedPasswordInput, user.getPassword())){
                user.setPassword(null);
                httpSession.setAttribute("user", user);
                failedSignInHandler.reactOnSuccess(user.getName());
                return true;
            }else{
                bindingResult.rejectValue("password", "user.signIn.incorrectPassword",
                        "Incorrect password");
                LocalTime timeBeforeRetry = failedSignInHandler.addAttempt(user.getName());
                reject(timeBeforeRetry, bindingResult, httpSession);
            }
        } catch (EncryptionException e) {
            e.printStackTrace();
        }
        return false;
    }

}
