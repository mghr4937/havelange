package com.mesti.havelange.models;

import lombok.Builder;
import lombok.Data;
import org.springframework.validation.annotation.Validated;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.time.LocalDateTime;

@Data
@Builder
@Entity(name = "matches")
@Validated
public class Match {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    @ManyToOne
    @JoinColumn(name = "home_team_id", referencedColumnName = "id")
    private Team homeTeam;

    @NotBlank
    @ManyToOne
    @JoinColumn(name = "away_team_id", referencedColumnName = "id")
    private Team awayTeam;

    @ManyToOne
    @JoinColumn(name = "location_id", referencedColumnName = "id")
    private Location location;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "tournament_id", referencedColumnName = "id")
    private Tournament tournament;

    @ManyToOne
    @JoinColumn(name = "season_id", nullable = false)
    private Season season;

    @Column(name = "start_time")
    @NotBlank
    private LocalDateTime startTime;

    @Column(name = "home_team_score")
    private Integer homeTeamScore;

    @Column(name = "away_team_score")
    private Integer awayTeamScore;

}
