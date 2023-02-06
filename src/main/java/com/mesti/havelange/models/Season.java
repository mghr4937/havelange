package com.mesti.havelange.models;

import lombok.Builder;
import lombok.Data;
import org.springframework.validation.annotation.Validated;

import javax.persistence.*;
import java.time.LocalDate;

@Entity(name = "seasons")
@Data
@Validated
@Builder
public class Season {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "tournament_id", nullable = false)
    private Tournament tournament;

    @Column(name = "start_date")
    private LocalDate startDate;

    @Column(name = "end_date")
    private LocalDate endDate;

    @ManyToOne
    @JoinColumn(name = "match_day_id")
    private MatchDay matchDay;

    @Override
    public String toString() {
        return "Season{" +
                "id=" + id +
                ", tournament=" + tournament.getName() +
                ", startDate=" + startDate +
                ", endDate=" + endDate +
                '}';
    }
}
