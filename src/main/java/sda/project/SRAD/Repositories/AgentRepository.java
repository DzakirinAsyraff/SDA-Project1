package sda.project.SRAD.Repositories;


import org.springframework.data.jpa.repository.JpaRepository;

import sda.project.SRAD.Entities.Agent;
import sda.project.SRAD.Entities.User;



public interface AgentRepository extends JpaRepository<Agent, Long> {
    Agent findByUser(User user);
}