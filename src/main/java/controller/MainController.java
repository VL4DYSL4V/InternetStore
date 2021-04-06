package controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpSession;

@Controller
@RequestMapping("/main")
public final class MainController {

    @GetMapping
    public String main(HttpSession httpSession){
        if(httpSession.getAttribute("user") == null){
            return "redirect:/users/new";
        }
        return "main/main";
    }
}
