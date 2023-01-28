package sda.project.SRAD;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import sda.project.SRAD.Entities.User;
import sda.project.SRAD.Enums.EUserType;
import sda.project.SRAD.Repositories.UserRepository;



// Runs some logic once the application is started. Eg: Insert root user for testing
@Component
public class ApplicationInit implements ApplicationRunner {

    private UserRepository userRepository;
    private PasswordEncoder passwordEncoder;

    @Autowired
    public ApplicationInit(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }


    public void run(ApplicationArguments args) {
        User student = new User();
        student.setUsername("student");
        student.setPassword( passwordEncoder.encode("student") );
        student.setUserType( EUserType.STUDENT );

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

        userRepository.saveAll( List.of(student, sradstaff, facultystaff, agent) );
    }
}