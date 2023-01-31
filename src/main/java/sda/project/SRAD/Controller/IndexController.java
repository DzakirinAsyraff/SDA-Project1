package sda.project.SRAD.Controller;

import java.io.IOException;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import sda.project.SRAD.DTO.RegistrationFormData;
import sda.project.SRAD.Entities.User;
import sda.project.SRAD.Enums.ECountry;
import sda.project.SRAD.Enums.EMartialStatus;
import sda.project.SRAD.Enums.EUserType;
import sda.project.SRAD.Services.AuthService;
import sda.project.SRAD.Utils.AlertUtil;

@Controller
@RequestMapping("/")
public class IndexController {

    @Autowired
    private AuthService authService;



    
    @GetMapping("/")
    public String index(
        RedirectAttributes redirAttr,
        HttpServletResponse response
    ) throws IOException {
        User u = authService.getLoggedInUser();

        if (u == null) {
            AlertUtil.alertInfo(redirAttr, "Please login to continue");
            return "redirect:/login";
        }

        if (u.getUserType() == EUserType.SRADSTAFF) return "redirect:/staff";
        else return "redirect:/applicant";
    }


    // Login GET handlers. Redirect if already logged in
    @GetMapping("login")
    public String loginGet(
        HttpServletRequest request
    ) {
        User u = authService.getLoggedInUser();
        if (u != null) return "redirect:/";

        return "Misc/Login";
    }


    // Login POST handler. The redirection will be performed by SpringSecurityConfig.java
    @PostMapping("login")
    public void loginPost(
        HttpServletRequest request,
        RedirectAttributes redirAttr
    ) {
        String username = request.getParameter("username");
        String password = request.getParameter("password");
        authService.login(username, password);
    }


    // Login failure redirect
    @GetMapping("login-failure")
    public String loginFailure(
        RedirectAttributes redirAttr
    ) {
        AlertUtil.alertDanger(redirAttr, "Invalid username or password");
        return "redirect:/login";
    }


    // Logout success redirect
    @GetMapping("logout-success")
    public String logoutSuccess(
        RedirectAttributes redirAttr
    ) {
        AlertUtil.alertSuccess(redirAttr, "Logged out successfully");
        return "redirect:/login";
    }



    // Register GET handler. Redirect if already logged in
    @GetMapping(path = { "register", "register1", "register2" })
    public String registerGet(
        HttpServletRequest request
    ) {
        User u = authService.getLoggedInUser();
        if (u != null) return "redirect:/";

        return "Misc/Register";
    }




    // Register POST handler - Part 1
    @PostMapping("register1")
    public String registerPost1(
        RegistrationFormData formData,
        Model model,
        RedirectAttributes redirAttr
    ) {
        // Check password and confirm password match
        if (!formData.getPassword().equals(formData.getConfirmPassword())) {
            AlertUtil.alertDanger(redirAttr, "Passwords do not match");
            return "redirect:/register";
        }

        // Check if username is already taken
        User u = authService.getUserRepository().findByUsername(formData.getUsername());
        if (u != null) {
            AlertUtil.alertDanger(redirAttr, "Username already taken");
            return "redirect:/register";
        }

        model.addAttribute("form", formData);
        return "Misc/Register2";
    }


    // Register POST handler - Part 2
    @PostMapping("register2")
    public String registerPost2(
        RegistrationFormData formData,
        Model model
    ) {
        model.addAttribute("form", formData);
        model.addAttribute("countries", ECountry.values());
        model.addAttribute("martialStatuses", EMartialStatus.values());
        return "Misc/Register3";
    }


    // Register POST handler - Part 3
    @PostMapping("register3")
    public String registerPost3(
        RedirectAttributes redirAttr,
        RegistrationFormData formData
    ) {
        try {
            authService.registerStudent(formData);
            AlertUtil.alertSuccess(redirAttr, "Registration successful. Please login");
            return "redirect:/login";
        } catch (Exception e) {
            AlertUtil.alertDanger(redirAttr, "Registration failed: " + e.getMessage());
            return "redirect:/register";
        }
    }







    // Testing route to check authentication status, return user as JSON if logged in
    @GetMapping("who_am_i")
    public ResponseEntity<Object> whoAmIRoute() {
        User u = authService.getLoggedInUser();
        return ResponseEntity.ok( u );
    }
}
