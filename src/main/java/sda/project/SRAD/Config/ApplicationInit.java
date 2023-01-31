package sda.project.SRAD.Config;

import java.io.IOException;
import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import sda.project.SRAD.Entities.Student;
import sda.project.SRAD.Entities.User;
import sda.project.SRAD.Enums.ECountry;
import sda.project.SRAD.Enums.EMartialStatus;
import sda.project.SRAD.Enums.EUserType;
import sda.project.SRAD.Repositories.StudentRepository;
import sda.project.SRAD.Repositories.UserRepository;
import sda.project.SRAD.Services.FileStorageService;



// Runs some logic once the application is started. Eg: Insert root user for testing
@Component
public class ApplicationInit implements ApplicationRunner {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private StudentRepository studentRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private FileStorageService fileStorageService;


    public void run(ApplicationArguments args) throws IOException {
        // Create the uploads folder if it doesn't exist
        fileStorageService.init();

        // Create test users
        addTestStudent();

        User sradstaff = new User();
        sradstaff.setUsername("sradstaff");
        sradstaff.setPassword( passwordEncoder.encode("sradstaff") );
        sradstaff.setUserType( EUserType.SRADSTAFF );

        User facultystaff = new User();
        facultystaff.setUsername("facultystaff");
        facultystaff.setPassword( passwordEncoder.encode("facultystaff") );
        facultystaff.setUserType( EUserType.FACULTYSTAFF );

        User agent = new User();
        agent.setUsername("agent");
        agent.setPassword( passwordEncoder.encode("agent") );
        agent.setUserType( EUserType.AGENT );

        userRepository.saveAll( List.of(sradstaff, facultystaff, agent) );
    }



    private void addTestStudent() {
        User u = new User();
        u.setUsername("student");
        u.setPassword( passwordEncoder.encode("student") );
        u.setUserType( EUserType.STUDENT );
        u.setContactNo("011-12345678");
        u.setEmail("student@email.com");
        u.setIcPassport("010203-01-1234");
        u.setName("Mr.Student");
        u.setRegisteredAt( LocalDate.now() );

        Student s = new Student();
        s.setUser(u);
        s.setAddress("123, Jalan 1, Taman 1, 12345, Kuala Lumpur");
        s.setCountryOfBirth(ECountry.MALAYSIA);
        s.setDateOfBirth(LocalDate.of(2001, 1, 1));
        s.setGender('M');
        s.setMartialStatus(EMartialStatus.SINGLE);
        s.setNationality(ECountry.MALAYSIA);
        s.setReligion("Islam");

        studentRepository.save(s);
    }
}