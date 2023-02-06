package com.mesti.havelange.models;

import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.util.ArrayList;
import java.util.List;

@Data
@Entity(name = "tournaments")
public class Tournament {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NotBlank
    private String name;
    @NotBlank
    private String country;
    @NotBlank
    private String city;
    @ElementCollection
    private List<Location> locations;
    @ManyToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE}, fetch = FetchType.LAZY)
    private List<Team> teams;
    private boolean enabled = true;
    @OneToMany(mappedBy = "tournament")
    private List<Season> seasons = new ArrayList<>();


    public void removeTeam(Team team) {
        this.teams.remove(team);
    }

    @Override
    public String toString() {
        return "Tournament{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", country='" + country + '\'' +
                ", city='" + city + '\'' +
                ", locations=" + locations +
                ", teams=" + teams +
                ", enabled=" + enabled +
                '}';
    }
}