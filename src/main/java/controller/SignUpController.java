package controller;

import dao.user.UserDao;
import dto.SignUpForm;
import entity.User;
import exception.dao.StoreException;
import exception.encryption.EncryptionException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import service.encryption.EncryptionService;
import validation.SignUpFormValidator;

import javax.servlet.http.HttpSession;

@Controller
@RequestMapping("/users")
public final class SignUpController {

    private final UserDao userDao;
    private final EncryptionService encryptionService;
    private final SignUpFormValidator signUpFormValidator;

    @Autowired
    public SignUpController(UserDao userDao,
                            @Qualifier("defaultEncryptionService") EncryptionService encryptionService,
                            SignUpFormValidator signUpFormValidator) {
        this.userDao = userDao;
        this.encryptionService = encryptionService;
        this.signUpFormValidator = signUpFormValidator;
    }

    @InitBinder
    private void initBinder(WebDataBinder webDataBinder) {
        webDataBinder.addValidators(signUpFormValidator);
    }

    @GetMapping
    public String defaultMapping() {
        return "redirect:/users/sign-up";
    }

    @GetMapping("/sign-up")
    public String signUpForm(Model model) {
        model.addAttribute("createUserForm", new SignUpForm());
        return "user/signUp";
    }

    @PostMapping("/sign-up")
    public String signUp(
            @ModelAttribute("createUserForm") @Validated SignUpForm signUpForm,
            BindingResult bindingResult,
            HttpSession httpSession) {
        if (bindingResult.hasErrors()) {
            return "user/signUp";
        }
        User user = new User();
        try {
            user.setName(signUpForm.getName());
            user.setPassword(encryptionService.getEncrypted(signUpForm.getPassword()));
            user.setPhoneNumber(signUpForm.getPhoneNumber());
            user.setEmail(signUpForm.getEmail());
            userDao.save(user);
        } catch (EncryptionException | StoreException e) {
            e.printStackTrace();
            return "user/signUp";
        }
        user.setPassword(null);
        httpSession.setAttribute("user", user);
        return "redirect:/main";
    }


}
