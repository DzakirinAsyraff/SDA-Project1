package sda.project.SRAD.Repositories;


import org.springframework.data.jpa.repository.JpaRepository;

import sda.project.SRAD.Entities.Student;
import sda.project.SRAD.Entities.User;



public interface StudentRepository extends JpaRepository<Student, Long> {
    Student findByUser(User user);
}