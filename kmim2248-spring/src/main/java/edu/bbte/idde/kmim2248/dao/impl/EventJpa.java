package edu.bbte.idde.kmim2248.dao.impl;

import edu.bbte.idde.kmim2248.dao.EventDao;
import edu.bbte.idde.kmim2248.model.Event;
import org.springframework.context.annotation.Profile;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.Optional;

@Repository
@Profile("jpa")
public interface EventJpa extends JpaRepository<Event, Long>, EventDao {

    @Override
    Optional<Event> findByName(String name);

    @Override
    Collection<Event> findByNameContainingIgnoreCase(String keyword);
}
