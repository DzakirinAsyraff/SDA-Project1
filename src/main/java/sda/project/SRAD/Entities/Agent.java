package sda.project.SRAD.Entities;

import java.time.LocalDate;

import com.fasterxml.jackson.annotation.JsonBackReference;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.MapsId;
import jakarta.persistence.OneToOne;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import sda.project.SRAD.DTO.RegistrationFormData;
import sda.project.SRAD.Enums.ECountry;
import sda.project.SRAD.Enums.EMartialStatus;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Agent {

    // Id is auto mapped to match User.userId
    @Id
    private Long id;
    
    private String address;
    private ECountry countryOfBirth;
    private LocalDate dateOfBirth;
    private Character gender;
    private EMartialStatus martialStatus;
    private ECountry nationality;
    private String religion;

    private String company;

    @OneToOne(cascade = {CascadeType.ALL})
    @JoinColumn(name = "user", referencedColumnName = "userId")
    @JsonBackReference
    @MapsId
    private User user;
    
    // TODO: Agent application reference


    // Constructor from RegistrationFormData, no need to construct User yourself
    public Agent(RegistrationFormData formData) {
        User u = new User(formData);
        this.user = u;

        this.address = formData.getAddress();
        this.countryOfBirth = formData.getCountryOfBirth();
        this.dateOfBirth = formData.getDateOfBirth();
        this.gender = formData.getGender();
        this.martialStatus = formData.getMartialStatus();
        this.nationality = formData.getNationality();
        this.religion = formData.getReligion();
    }
}