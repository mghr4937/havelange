package com.mesti.havelange.models;

import lombok.Builder;
import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Data
@Builder
@Entity(name = "matchdays")
public class MatchDay {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "season_id", referencedColumnName = "id", nullable = false)
    private Season season;

    @OneToMany(mappedBy = "matchDay", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Match> matches;

    @NotNull
    @Column(name = "match_date")
    private LocalDate matchDate;
}
