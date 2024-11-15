package com.medialibrary.controller;

import static com.medialibrary.constants.Messages.ERROR;
import static com.medialibrary.constants.Messages.SUCCESS;

import com.medialibrary.exception.UserAlreadyExistsException;
import com.medialibrary.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class UserController {

  public final UserService userService;

  public UserController(UserService userService) {
    this.userService = userService;
  }

  @GetMapping("/register")
  public String register() {
    return "register";
  }

  @PostMapping("/register")
  public String saveUser(Model model, @RequestParam String username,
      @RequestParam String password) {
    try {
      String result = userService.saveUser(username, password);
      model.addAttribute(SUCCESS, result);
    } catch (UserAlreadyExistsException e) {
      model.addAttribute(ERROR, e.getMessage());
      return "register";
    }
    return "redirect:/login?success";
  }

  @GetMapping("/login")
  public String login() {
    return "login";
  }
}
