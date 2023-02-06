package com.mesti.havelange.services;

import com.mesti.havelange.models.Match;
import com.mesti.havelange.models.MatchDay;
import com.mesti.havelange.models.Team;
import com.mesti.havelange.repositories.MatchDayRepository;
import com.mesti.havelange.repositories.MatchRepository;
import com.mesti.havelange.repositories.SeasonRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;

import javax.persistence.EntityNotFoundException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@Transactional
@Validated
@RequiredArgsConstructor
@Slf4j
public class FixtureService {
    private final SeasonRepository seasonRepository;
    private final MatchRepository matchRepository;
    private  final MatchDayRepository matchDayRepository;

    public void generateLigueFixture(Long seasonId, LocalDateTime startTime, int daysBetweenMatches, boolean generateTwoRounds) {
        var season = seasonRepository.findById(seasonId)
                .orElseThrow(() -> new EntityNotFoundException("Season with id " + seasonId + " not found"));

        List<Team> teams = season.getTournament().getTeams();
        log.info("Found {} teams for tournament with id {}", teams.size(), seasonId);
        int numberOfTeams = teams.size();

        // Si no hay suficientes equipos para formar un torneo, no generar el fixture
        if (numberOfTeams < 2) {
            throw new IllegalArgumentException("Not enough teams to generate fixture");
        }

        int numberOfMatches = (teams.size() * (teams.size() - 1) / 2) * (generateTwoRounds ? 2 : 1);
        log.info("Number of matches to be generated: {}", numberOfMatches);

        Map<LocalDateTime, MatchDay> matchDays = new HashMap<>();
        int round = 1;
        while (round <= (generateTwoRounds ? 2 : 1)) {
            for (int i = 0; i < numberOfTeams; i++) {
                for (int j = i + 1; j < numberOfTeams; j++) {
                    if (i == j) {
                        continue;
                    }

                    var match = Match.builder()
                            .homeTeam(teams.get(i))
                            .awayTeam(teams.get(j))
                            .startTime(startTime)
                            .tournament(season.getTournament())
                            .build();
                    log.debug("Generated match: {}", match);

                    LocalDateTime date = startTime.toLocalDate().atTime(17,00);
                    if (!matchDays.containsKey(date)) {
                        var matchDay = MatchDay.builder().season(season).matchDate(LocalDate.from(date)).matches(new ArrayList<>()).build();
                        matchDay = matchDayRepository.save(matchDay);
                        matchDays.put(date, matchDay);
                    }
                    matchRepository.save(match);
                    log.info("Saved {} match to the database", match);
                    matchDays.get(date).getMatches().add(match);
                }
                startTime = startTime.plusDays(daysBetweenMatches);
            }
            round++;
        }

        matchDayRepository.saveAll(matchDays.values());
        log.info("Saved {} match days to the database", matchDays.values().size());
    }
}
