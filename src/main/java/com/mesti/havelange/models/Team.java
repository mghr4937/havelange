package com.mesti.havelange.models;

import lombok.Data;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.text.WordUtils;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.Size;
import java.util.List;

@Data
@Entity(name = "teams")
public class Team {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "name", nullable = false, unique = true)
    private String name;
    @Column(name = "shortName", nullable = false, unique = true)
    @Size(max = 3)
    private String shortName;
    @Column(name = "city", nullable = false)
    private String city;
    @Column(name = "phone", nullable = false)
    private String phone;
    @Email
    @Column(name = "email", nullable = false)
    private String email;
    private String clubColors;
    private boolean enabled = true;
    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Player> players;

    public void setShortName(String shortName){
        this.shortName = StringUtils.truncate(shortName.toUpperCase(), 3);
    }

    public void setName(String name){
        this.name =  WordUtils.capitalize(name);
    }
}
