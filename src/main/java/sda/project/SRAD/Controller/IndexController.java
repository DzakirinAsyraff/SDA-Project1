package sda.project.SRAD.Controller;

import java.io.IOException;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import jakarta.servlet.http.HttpServletResponse;

@Controller
@RequestMapping("/")
public class IndexController {
    
    @GetMapping("/")
    public void index(HttpServletResponse response) throws IOException {
        response.sendRedirect("/login");
    }

    @GetMapping("login")
    public String login() {
        return "Misc/Login";
    }

    // error page
    @GetMapping("/error")
    public String error() {
        return "Misc/Error";
    }


    @PostMapping("login")
    public String login(
        @RequestParam("username") String username, 
        @RequestParam("password") String password
    ) {
        if (username.equals("staff") && password.equals("1234")) {
            return "redirect:/staff/";
        } else if (username.equals("appl") && password.equals("1234")) {
            return "redirect:/applicant/";
        } else {
            return "Misc/Login";
        }
    }
}
