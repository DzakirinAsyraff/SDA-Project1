package sda.project.SRAD.Services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import lombok.Getter;
import sda.project.SRAD.DTO.RegistrationFormData;
import sda.project.SRAD.Entities.Agent;
import sda.project.SRAD.Entities.Student;
import sda.project.SRAD.Entities.User;
import sda.project.SRAD.Repositories.AgentRepository;
import sda.project.SRAD.Repositories.StudentRepository;
import sda.project.SRAD.Repositories.UserRepository;

@Service
public class AuthService {
    
    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    @Getter
    private UserRepository userRepository;
    @Autowired
    @Getter
    private StudentRepository studentRepository;
    @Autowired
    @Getter
    private AgentRepository agentRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;


    public User getLoggedInUser() {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (principal instanceof User) return (User) principal;

        // principal object is either null or represents anonymous user -
        // neither of which our domain User object can represent - so return null
        return null;
    }


    public void login(String username, String password) {
        Authentication a = new UsernamePasswordAuthenticationToken(username, password);
        a = authenticationManager.authenticate(a);
        SecurityContextHolder.getContext().setAuthentication(a);
    }


    public void registerStudent(RegistrationFormData formData) {
        formData.setPassword( passwordEncoder.encode(formData.getPassword()) );
        Student s = new Student(formData);
        studentRepository.save(s);
    }

    public void registerAgent(RegistrationFormData formData) {
        formData.setPassword( passwordEncoder.encode(formData.getPassword()) );
        Agent a = new Agent(formData);
        agentRepository.save(a);
    }
}
