package sda.project.SRAD.Repositories;


import org.springframework.data.jpa.repository.JpaRepository;

import sda.project.SRAD.Entities.User;



public interface UserRepository extends JpaRepository<User, Long> {

    User findByUsername(String username);
}