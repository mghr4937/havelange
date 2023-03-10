package com.mesti.havelange.models;

import java.time.LocalDate;
import java.time.Period;
import javax.persistence.*;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.validation.annotation.Validated;

@Data
@Entity(name = "players")
@Validated
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Player {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NotBlank
    private String name;
    @NotBlank
    private String lastName;
    @JsonFormat
    @NotNull
    private LocalDate dateOfBirth;
    @Min(value = 0)
    private int shirtNumber;
    @ManyToOne
    private Team team;
    private boolean enabled = true;
    private String gender;
    @Size(max = 128)
    private String identityId;

    public int getAge() {
        LocalDate now = LocalDate.now();
        Period period = Period.between(dateOfBirth, now);
        return period.getYears();
    }
}
