package edu.bbte.idde.kmim2248.dao.impl;

import edu.bbte.idde.kmim2248.model.Attendee;
import org.springframework.context.annotation.Profile;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

@Profile("jpa")
public interface AttendeeJpa extends JpaRepository<Attendee, Long>, JpaSpecificationExecutor<Attendee> {
}
