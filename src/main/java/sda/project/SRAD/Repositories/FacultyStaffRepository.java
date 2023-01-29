package sda.project.SRAD.Repositories;


import org.springframework.data.jpa.repository.JpaRepository;

import sda.project.SRAD.Entities.FacultyStaff;
import sda.project.SRAD.Entities.User;



public interface FacultyStaffRepository extends JpaRepository<FacultyStaff, Long> {
    FacultyStaff findByUser(User user);
}