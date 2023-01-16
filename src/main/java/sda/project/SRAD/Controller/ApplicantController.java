package sda.project.SRAD.Controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/applicant")
public class ApplicantController {
    
    @GetMapping("/")
    public String index() {
        return "Applicant/ApplicantMenu";
    }

    @GetMapping("/apply/form")
    public String apply() {
        return "Applicant/StudentRegistration";
    }

    @GetMapping("/apply/pay")
    public String pay() {
        return "Applicant/Payment";
    }

    @GetMapping("/apply/upload")
    public String upload() {
        return "Applicant/UploadDocuments";
    }

    @GetMapping("/view-initiated")
    public String viewInitiated() {
        return "Applicant/ViewInitiated";
    }

    @GetMapping("/view-status")
    public String viewStatus() {
        return "Applicant/ViewApplicationStat";
    }

    @GetMapping("/become-agents")
    public String becomeAgents() {
        return "Applicant/BecomeAgents";
    }
}
