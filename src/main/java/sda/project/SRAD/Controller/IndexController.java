package sda.project.SRAD.Controller;

import java.io.IOException;
import java.time.LocalDate;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import sda.project.SRAD.Entities.User;
import sda.project.SRAD.Enums.EUserType;
import sda.project.SRAD.Repositories.UserRepository;
import sda.project.SRAD.Utils.AlertUtil;
import sda.project.SRAD.Utils.AuthUtil;

@Controller
@RequestMapping("/")
public class IndexController {

    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;



    
    @GetMapping("/")
    public String index(
        RedirectAttributes redirAttr,
        HttpServletResponse response
    ) throws IOException {
        User u = AuthUtil.getLoggedInUser();

        if (u == null) {
            AlertUtil.alertInfo(redirAttr, "Please login to continue");
            return "redirect:/login";
        }

        if (u.getUserType() == EUserType.SRADSTAFF) return "redirect:/staff/";
        else return "redirect:/student/";
    }


    // Login GET handlers. Redirect if already logged in
    @GetMapping("login")
    public String loginGet(
        HttpServletRequest request
    ) {
        User u = AuthUtil.getLoggedInUser();
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
        Authentication a = new UsernamePasswordAuthenticationToken(username, password);

        a = authenticationManager.authenticate(a);
        SecurityContextHolder.getContext().setAuthentication(a);
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
    @GetMapping("register")
    public String registerGet(
        HttpServletRequest request
    ) {
        User u = AuthUtil.getLoggedInUser();
        if (u != null) return "redirect:/";

        return "Misc/Register";
    }


    // Register POST handler
    @PostMapping("register")
    public String registerPost(
        HttpServletRequest request,
        RedirectAttributes redirAttr
    ) {
        String username = request.getParameter("username");
        String password = request.getParameter("password");
        String name = request.getParameter("name");
        String icPassport = request.getParameter("ic");
        String email = request.getParameter("email");
        String contactNo = request.getParameter("contact");
        EUserType userType = EUserType.valueOf( request.getParameter("role") );

        User u = new User();
        u.setUsername(username);
        u.setPassword( passwordEncoder.encode(password) );
        u.setName(name);
        u.setIcPassport(icPassport);
        u.setEmail(email);
        u.setContactNo(contactNo);
        u.setUserType(userType);
        u.setRegisteredAt( LocalDate.now() );

        try {
            userRepository.save(u);
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
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        if ( !(principal instanceof UserDetails) )
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Not logged in");
        return ResponseEntity.ok( principal );
    }
}
