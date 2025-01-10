package edu.bbte.idde.kmim2248.dao.impl;

import edu.bbte.idde.kmim2248.model.Attendee;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AttendeeJpa extends JpaRepository<Attendee, Long> {
}
