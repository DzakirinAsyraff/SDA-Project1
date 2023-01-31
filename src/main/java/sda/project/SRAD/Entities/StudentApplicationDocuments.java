package sda.project.SRAD.Entities;


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

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class StudentApplicationDocuments {

    @Id
    private Long id;


    @OneToOne(cascade = {CascadeType.ALL})
    @JoinColumn(name = "studentApplication", referencedColumnName = "id")
    @JsonBackReference
    @MapsId
    private StudentApplication studentApplication;
}