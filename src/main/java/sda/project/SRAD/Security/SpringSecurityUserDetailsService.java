package sda.project.SRAD.Security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import sda.project.SRAD.Entities.User;
import sda.project.SRAD.Repositories.UserRepository;



public class SpringSecurityUserDetailsService implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) {
        User u = userRepository.findByUsername(username);
        if (u == null) throw new UsernameNotFoundException("No user " + username + " found.");
        return u;
    }

}