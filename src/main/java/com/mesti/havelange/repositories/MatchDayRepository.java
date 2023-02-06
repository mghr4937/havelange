package com.mesti.havelange.repositories;

import com.mesti.havelange.models.MatchDay;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MatchDayRepository extends JpaRepository<MatchDay, Long> {
}
