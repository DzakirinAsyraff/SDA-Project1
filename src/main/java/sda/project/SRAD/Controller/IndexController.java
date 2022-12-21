package sda.project.SRAD.Controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/")
public class IndexController {
    
    @GetMapping("/login")
    public String login() {
        return "Login";
    }

    // a post mapping for login that sends form data 
    // to the login controller
    @PostMapping("/login")
    public String login(@RequestParam("username") String username, @RequestParam("password") String password) {
        if (username.equals("staff") && password.equals("1234")) {
            return "redirect:/staff/";
        } else if (username.equals("appl") && password.equals("1234")) {
            return "redirect:/applicant/";
        } else {
            return "Login";
        }
    }

    // error page
    @GetMapping("/error")
    public String error() {
        return "Error";
    }

}
