package org.example.controllers.mvc;

import org.example.mappers.UserMapper;
import org.example.models.dto.requstdto.UserRegistrationDTO;
import org.example.models.dto.requstdto.UserUpdateDTO;
import org.example.models.entity.UserEntity;
import org.example.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class UserMvcController extends BaseMvcController {
    @Autowired
    private UserService userService;

    @Autowired
    private UserMapper userMapper;

    @GetMapping("/profile")
    public String showProfile(Model model) {
        UserEntity user = userService.getCurrentUser();
        if (user == null) {
            return "redirect:/login";
        }
        model.addAttribute("user", userMapper.toUserDTO(user));
        addCommonAttributes(model);
        return "profile";
    }

    @GetMapping("/profile/edit")
    public String showEditProfile(Model model) {
        UserEntity user = userService.getCurrentUser();
        if (user == null) {
            return "redirect:/login";
        }
        model.addAttribute("user", userMapper.toUserDTO(user));
        addCommonAttributes(model);
        return "edit-profile";
    }

    @PostMapping("/profile/edit")
    public String updateProfile(@ModelAttribute("user") UserUpdateDTO dto) {
        userService.updateCurrentUser(dto);
        return "redirect:/profile";
    }

    @GetMapping("/register")
    public String showRegistrationForm(Model model) {
        model.addAttribute("user", new UserRegistrationDTO("", "", "", "", "", "", "", ""));
        return "register";
    }

    @PostMapping("/register")
    public String registerUser(@ModelAttribute("user") UserRegistrationDTO dto) {
        userService.registerUser(dto);
        return "redirect:/login";
    }

    @GetMapping("/login")
    public String showLoginForm(Model model,
                                @RequestParam(value = "error", required = false) String error,
                                @RequestParam(value = "logout", required = false) String logout) {
        if (error != null) model.addAttribute("error", "Invalid username or password");
        if (logout != null) model.addAttribute("message", "You have been logged out successfully");
        addCommonAttributes(model);
        return "login";
    }
}