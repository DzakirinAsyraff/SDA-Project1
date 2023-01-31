package sda.project.SRAD.Repositories;


import org.springframework.data.jpa.repository.JpaRepository;

import sda.project.SRAD.Entities.StudentApplication;
import sda.project.SRAD.Entities.Student;



public interface StudentApplicationRepository extends JpaRepository<StudentApplication, Long> {
    StudentApplication findByStudent(Student student);
}