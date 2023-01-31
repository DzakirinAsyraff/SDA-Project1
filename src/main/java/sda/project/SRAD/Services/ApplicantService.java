package sda.project.SRAD.Services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import lombok.Getter;
import sda.project.SRAD.Entities.Student;
import sda.project.SRAD.Entities.StudentApplication;
import sda.project.SRAD.Entities.User;
import sda.project.SRAD.Repositories.StudentApplicationRepository;
import sda.project.SRAD.Repositories.StudentRepository;

@Service
public class ApplicantService {
    
    @Autowired
    @Getter
    private StudentRepository studentRepository;
    @Autowired
    @Getter
    private StudentApplicationRepository studentApplicationRepository;


    public Student getStudent(User u) {
        return studentRepository.findByUser(u);
    }


    public StudentApplication saveStudentApplication(StudentApplication sa) {
        return studentApplicationRepository.save(sa);
    }
}
