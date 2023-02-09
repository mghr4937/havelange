package com.mesti.havelange.models;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
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

