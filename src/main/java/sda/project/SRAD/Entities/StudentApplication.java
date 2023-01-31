package sda.project.SRAD.Entities;


import com.fasterxml.jackson.annotation.JsonBackReference;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import sda.project.SRAD.Enums.EFaculty;
import sda.project.SRAD.Enums.EStudentApplicationStatus;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class StudentApplication {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    private EStudentApplicationStatus status;

    private String name;
    private String email;
    private String contact;
    private String address;
    private String city;
    private String state;
    private String zipCode;

    private EFaculty faculty;
    private String programme;

    @OneToOne(cascade = {CascadeType.ALL})
    @JoinColumn(name = "student", referencedColumnName = "user")
    @JsonBackReference
    private Student student;
}