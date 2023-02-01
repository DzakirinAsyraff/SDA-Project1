package sda.project.SRAD.Services;

import java.io.IOException;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.stream.Stream;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import lombok.Getter;
import sda.project.SRAD.Entities.Student;
import sda.project.SRAD.Entities.StudentApplication;
import sda.project.SRAD.Entities.StudentApplicationDocuments;
import sda.project.SRAD.Entities.StudentApplicationPayment;
import sda.project.SRAD.Entities.User;
import sda.project.SRAD.Enums.EStudentApplicationStatus;
import sda.project.SRAD.Repositories.StudentApplicationDocumentsRepository;
import sda.project.SRAD.Repositories.StudentApplicationPaymentRepository;
import sda.project.SRAD.Repositories.StudentApplicationRepository;
import sda.project.SRAD.Repositories.StudentRepository;

@Service
public class ApplicantService {

    private final String DIR_PREFIX = "stud_app/";

    
    @Autowired
    @Getter
    private StudentRepository studentRepository;
    @Autowired
    @Getter
    private StudentApplicationRepository studentApplicationRepository;
    @Autowired
    @Getter
    private StudentApplicationDocumentsRepository studentApplicationDocumentsRepository;
    @Autowired
    @Getter
    private StudentApplicationPaymentRepository studentApplicationPaymentRepository;
    @Autowired
    @Getter
    private FileStorageService fileStorageService;


    public Student getStudent(User u) {
        return studentRepository.findByUser(u);
    }


    public StudentApplication saveStudentApplication(StudentApplication sa) {
        return studentApplicationRepository.save(sa);
    }


    public StudentApplication saveStudentApplicationDocuments(
        StudentApplication sa,
        MultipartFile qualificationTranscript,
        MultipartFile englishCert,
        MultipartFile identity,
        MultipartFile[] otherDocs
    ) throws IOException {
        String directory = DIR_PREFIX + "docs/" + sa.getId();
        StudentApplicationDocuments sad = studentApplicationDocumentsRepository.findById(sa.getId()).orElse(new StudentApplicationDocuments());
        
        // Set application's status to pending payment if it's a draft, else if it is a resubmission, set it to application submitted
        if (sa.getStatus() == EStudentApplicationStatus.DRAFT) sa.setStatus(EStudentApplicationStatus.PENDING_PAYMENT);
        else sa.setStatus(EStudentApplicationStatus.APPLICATION_SUBMITTED);

        // Filter away empty files from otherDocs
        otherDocs = Arrays.stream(otherDocs).filter(d -> {
            return d.getSize() != 0 
                && d.getOriginalFilename() != null;
        }).toArray(MultipartFile[]::new);

        // Store files into directory
        fileStorageService.deleteAll(directory);
        fileStorageService.store(qualificationTranscript, directory);
        fileStorageService.store(englishCert, directory);
        fileStorageService.store(identity, directory);
        for (MultipartFile doc : otherDocs) fileStorageService.store(doc, directory);

        // Set entity properties
        sad.setDirectory(directory);
        sad.setQualificationTranscript(qualificationTranscript.getOriginalFilename());
        sad.setEnglishCert(englishCert.getOriginalFilename());
        sad.setIdentity(identity.getOriginalFilename());
        sad.setOtherDocs( Stream.of(otherDocs).map(MultipartFile::getOriginalFilename).toArray(String[]::new) );
        
        sad.setStudentApplication(sa);
        sa.setStudentApplicationDocuments(sad);

        studentApplicationDocumentsRepository.save(sad);
        return studentApplicationRepository.save(sa);
    } 


    public StudentApplication saveStudentApplicationPayment(
        StudentApplication sa,
        MultipartFile paymentProof
    ) throws IOException {
        String directory = DIR_PREFIX + "payment/" + sa.getId();
        StudentApplicationPayment payment = studentApplicationPaymentRepository.findById(sa.getId()).orElse(new StudentApplicationPayment());
        
        sa.setStatus(EStudentApplicationStatus.APPLICATION_SUBMITTED);

        // Store file into directory
        fileStorageService.deleteAll(directory);
        fileStorageService.store(paymentProof, directory);

        payment.setAmount(20.00);
        payment.setDirectory(directory);
        payment.setPaymentProof(paymentProof.getOriginalFilename());
        payment.setStudentApplication(sa);
        payment.setPaymentDate( LocalDate.now() );

        studentApplicationPaymentRepository.save(payment);
        return studentApplicationRepository.save(sa);
    }
}
