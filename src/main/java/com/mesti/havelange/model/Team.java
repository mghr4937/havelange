package com.mesti.havelange.model;

import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.Email;

@Data
@Entity(name = "teams")
public class Team {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "name", nullable = false, unique = true)
    private String name;
    @Column(name = "shortName", nullable = false, unique = true)
    private String shortName;
    @Column(name = "city", nullable = false)
    private String city;
    @Column(name = "phone", nullable = false)
    private String phone;
    @Email
    @Column(name = "email", nullable = false)
    private String email;
    private String clubColors;

//    private List<Player> squad;
}
