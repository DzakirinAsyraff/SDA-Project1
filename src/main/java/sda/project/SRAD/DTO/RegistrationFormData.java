package sda.project.SRAD.DTO;

import java.io.Serializable;
import java.time.LocalDate;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import sda.project.SRAD.Enums.ECountry;
import sda.project.SRAD.Enums.EMartialStatus;
import sda.project.SRAD.Enums.EUserType;


@Getter
@Setter
@NoArgsConstructor
public class RegistrationFormData implements Serializable {
    String username;
    String password;
    String confirmPassword;
    String name;
    String icPassport;
    String email;
    String contactNo;
    EUserType userType;

    String address;
    ECountry countryOfBirth;
    LocalDate dateOfBirth;
    Character gender;
    EMartialStatus martialStatus;
    ECountry nationality;
    String religion;

    // Agent only fields
    String companyName;
}
