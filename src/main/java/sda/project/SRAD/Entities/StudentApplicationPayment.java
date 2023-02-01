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

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class StudentApplicationPayment {

    @Id
    private Long id;

    private Double amount;
    private LocalDate paymentDate;

    private String directory;
    private String paymentProof;

    @OneToOne(cascade = {CascadeType.ALL})
    @JoinColumn(name = "studentApplication", referencedColumnName = "id")
    @JsonBackReference
    @MapsId
    private StudentApplication studentApplication;
}