package com.mesti.havelange.models;

import lombok.Builder;
import lombok.Data;
import org.springframework.validation.annotation.Validated;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.List;

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

    @OneToMany(mappedBy = "season", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Match> matches;

}
