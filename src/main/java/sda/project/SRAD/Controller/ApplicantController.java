package sda.project.SRAD.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.websocket.server.PathParam;
import sda.project.SRAD.Entities.Student;
import sda.project.SRAD.Entities.StudentApplication;
import sda.project.SRAD.Entities.User;
import sda.project.SRAD.Enums.EFaculty;
import sda.project.SRAD.Enums.EStudentApplicationStatus;
import sda.project.SRAD.Enums.EUserType;
import sda.project.SRAD.Services.ApplicantService;
import sda.project.SRAD.Utils.AlertUtil;

@Controller
@RequestMapping("/applicant")
@Secured({ EUserType.STUDENT_STR })
public class ApplicantController {
    
    @Autowired
    private ApplicantService applicantService;


    @GetMapping("")
    public String index(HttpServletRequest request) {
        return "Applicant/ApplicantMenu";
    }


    // Apply to be UTM Student - GET
    @GetMapping("apply/form")
    public String apply(
        HttpServletRequest request
    ) {
        Student s = applicantService.getStudent( (User) request.getAttribute("user") );
        request.setAttribute("student", s);
        request.setAttribute("faculties", EFaculty.values());
        return "Applicant/StudentRegistrationForm";
    }

    // Apply to be UTM Student - POST
    @PostMapping("apply/form")
    public String applyPost(
        HttpServletRequest request,
        StudentApplication sa
    ) {
        Student s = applicantService.getStudent( (User) request.getAttribute("user") );
        sa.setStudent(s);
        sa.setStatus(EStudentApplicationStatus.DRAFT);
        sa = applicantService.saveStudentApplication(sa);
        return "redirect:/applicant/apply/docs/" + sa.getId();
    }

    // Apply to be UTM Student - Document Upload - GET
    @GetMapping("apply/docs/{id}")
    public String docs(
        HttpServletRequest request,
        RedirectAttributes redirAttr,
        @PathVariable Long id
    ) {
        StudentApplication sa = applicantService.getStudentApplicationRepository().findById(id).orElse(null);
        User u = (User) request.getAttribute("user");

        // Check if application exists
        if (sa == null) {
            AlertUtil.alertDanger(redirAttr, "Application not found");
            return "redirect:/applicant";
        }

        // Check if the student is the owner of the application
        if ( !(sa.getStudent().getId().equals( u.getUserId() ) ) ) {
            AlertUtil.alertDanger(redirAttr, "You are not authorized to view this page");
            return "redirect:/applicant";
        }

        request.setAttribute("studentApplication", sa);
        return "Applicant/UploadDocuments";
    }


    // Apply to be UTM Student - Document Upload - POST
    @PostMapping("apply/docs/{id}")
    public String docsPost(
        HttpServletRequest request,
        RedirectAttributes redirAttr,
        @PathVariable Long id,
        @RequestParam("qualificationTranscript") MultipartFile qualificationTranscript,
        @RequestParam("englishCert") MultipartFile englishCert,
        @RequestParam("identity") MultipartFile identity,
        @RequestParam("otherDocs") MultipartFile[] otherDocs
    ) {
        StudentApplication sa = applicantService.getStudentApplicationRepository().findById(id).orElse(null);
        User u = (User) request.getAttribute("user");

        // Check if application exists
        if (sa == null) {
            AlertUtil.alertDanger(redirAttr, "Application not found");
            return "redirect:/applicant";
        }

        // Check if the student is the owner of the application
        if ( !(sa.getStudent().getId().equals( u.getUserId() ) ) ) {
            AlertUtil.alertDanger(redirAttr, "You are not authorized to view this page");
            return "redirect:/applicant";
        }


        return "redirect:/applicant/apply/docs/" + sa.getId();
    }




    @GetMapping("apply/pay")
    public String pay() {
        return "Applicant/Payment";
    }

    @GetMapping("apply/upload")
    public String upload() {
        return "Applicant/UploadDocuments";
    }

    @GetMapping("view-initiated")
    public String viewInitiated() {
        return "Applicant/ViewInitiated";
    }

    @GetMapping("view-status")
    public String viewStatus() {
        return "Applicant/ViewApplicationStat";
    }

    @GetMapping("become-agents")
    public String becomeAgents() {
        return "Applicant/BecomeAgents";
    }
}
