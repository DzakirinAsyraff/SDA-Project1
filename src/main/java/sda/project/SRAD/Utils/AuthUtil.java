package sda.project.SRAD.Utils;

import org.springframework.security.core.context.SecurityContextHolder;

import sda.project.SRAD.Entities.User;

public class AuthUtil {
    
    public static User getLoggedInUser() {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (principal instanceof User) return (User) principal;

        // principal object is either null or represents anonymous user -
        // neither of which our domain User object can represent - so return null
        return null;
    }
}
