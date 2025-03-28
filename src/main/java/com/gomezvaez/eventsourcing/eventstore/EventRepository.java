package com.gomezvaez.eventsourcing.eventstore;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EventRepository extends JpaRepository<EventEntity, Long> {
    List<EventEntity> findByAlchemistIdOrderByIdAsc(String alchemistId);

    List<EventEntity> findAllByOrderByIdAsc();
}