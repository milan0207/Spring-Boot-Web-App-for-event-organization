package edu.bbte.idde.kmim2248.dao;

import edu.bbte.idde.kmim2248.model.Event;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface EventDao extends JpaRepository<Event, Long>, JpaSpecificationExecutor<Event> {
    Page<Event> findByNameContainingIgnoreCase(String keyword, Pageable pageable);

}
