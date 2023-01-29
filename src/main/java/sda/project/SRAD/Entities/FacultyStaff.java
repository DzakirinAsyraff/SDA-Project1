package sda.project.SRAD.Entities;

import com.fasterxml.jackson.annotation.JsonBackReference;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import sda.project.SRAD.Enums.EFaculty;



@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class FacultyStaff {
    @Id
    private Long staffId;

    private EFaculty faculty;
    
    @OneToOne(cascade = {CascadeType.ALL})
    @JoinColumn(name = "user", referencedColumnName = "userId")
    @JsonBackReference
    private User user;
}