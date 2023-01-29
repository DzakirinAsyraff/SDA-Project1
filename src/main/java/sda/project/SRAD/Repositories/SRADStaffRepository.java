package sda.project.SRAD.Repositories;


import org.springframework.data.jpa.repository.JpaRepository;

import sda.project.SRAD.Entities.SRADStaff;
import sda.project.SRAD.Entities.User;



public interface SRADStaffRepository extends JpaRepository<SRADStaff, Long> {
    SRADStaff findByUser(User user);
}