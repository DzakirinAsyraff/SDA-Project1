package sda.project.SRAD.Entities;

import java.time.LocalDate;
import java.util.Collection;
import java.util.Collections;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.fasterxml.jackson.annotation.JsonProperty;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import sda.project.SRAD.DTO.RegistrationFormData;
import sda.project.SRAD.Enums.EUserType;


@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class User implements UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long userId;

    @Column(unique = true)
    private String username;
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private String password;
    private EUserType userType;

    private String name;
    private String email;
    private String contactNo;
    private String icPassport;
    private LocalDate registeredAt;

    
    // Constructor from RegistrationFormData
    public User(RegistrationFormData formData) {
        this.username = formData.getUsername();
        this.password = formData.getPassword();
        this.userType = formData.getUserType();
        this.name = formData.getName();
        this.email = formData.getEmail();
        this.contactNo = formData.getContactNo();
        this.icPassport = formData.getIcPassport();
        this.registeredAt = LocalDate.now();
    }




    // UserDetails interface methods
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return Collections.singletonList( new SimpleGrantedAuthority( this.userType.toString() ) );
    }

    @Override public boolean isAccountNonExpired() { return true; }
    @Override public boolean isAccountNonLocked() { return true; }
    @Override public boolean isCredentialsNonExpired() { return true; }
    @Override public boolean isEnabled() { return true; }
}