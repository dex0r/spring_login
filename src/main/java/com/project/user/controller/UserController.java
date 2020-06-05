package com.project.user.controller;

import com.project.user.entity.ConfirmationToken;
import com.project.user.entity.User;
import com.project.user.service.ConfirmationTokenService;
import com.project.user.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Optional;

@Controller
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private ConfirmationTokenService confirmationTokenService;

    @GetMapping("/sign-in")
    public String signIn(){
        return "sign-in";
    }

    @PostMapping("/profile")
    public String onSignIn(){
        System.out.println("asdada");
        //model.addAttribute("details", user);
        return "welcome";
    }

    @GetMapping("/sign-up")
    public String signUp(Model model){
        model.addAttribute("user", new User());
        return "sign-up";
    }

    @PostMapping("/sign-up")
    public String signUpUser(User user){
        System.out.println("User Password: " + user.getPassword());
        userService.singUpUser(user);
        return "redirect:/sign-in";
    }

    @GetMapping("/sign-up/confirm")
    public String confirmMail(@RequestParam("token") String token){
        Optional<ConfirmationToken> optionalConfirmationToken = confirmationTokenService.findConfirmationTokenByToken(token);

        optionalConfirmationToken.ifPresent(userService::confirmUser);

        return "/sign-in";
    }
}
