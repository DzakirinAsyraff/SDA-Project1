package sda.project.SRAD.Controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/applicant")
public class ApplicantController {
    
    @GetMapping("/")
    public String index() {
        return "ApplicantMenu";
    }
}
