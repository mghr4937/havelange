package com.mesti.havelange.models;

import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;

@Data
@Entity(name = "locations")
public class Location {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NotBlank
    private String name;
    @NotBlank
    private String address;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "tournaments_id")
    private Tournament tournament;

    @Override
    public String toString() {
        return "Location{" +
                "id=" + id +
                ", name='" + name + "'" +
                ", address='" + address + "'" +
                ", tournament=" + tournament.getId() +
                '}';
    }
}

