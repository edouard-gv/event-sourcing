package com.gomezvaez.eventsourcing.eventstore;

import com.gomezvaez.eventsourcing.domain.AlchemistId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EventRepository extends JpaRepository<EventEntity, Long> {
    List<EventEntity> findByAlchemistIdOrderByIdAsc(AlchemistId alchemistId);

    List<EventEntity> findAllByOrderByIdAsc();
}