package edu.bbte.idde.kmim2248.dao;

import edu.bbte.idde.kmim2248.model.Attendee;
import edu.bbte.idde.kmim2248.model.Event;
import edu.bbte.idde.kmim2248.service.dto.AttendeeFilterDTO;
import edu.bbte.idde.kmim2248.service.dto.EventFilterDTO;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import org.springframework.data.jpa.domain.Specification;

import java.util.Locale;

public class EventSpecification {

    public static Specification<Event> filterEvent(EventFilterDTO filterDTO) {
        return (Root<Event> root, CriteriaQuery<?> query, CriteriaBuilder cb) -> {
            Predicate predicate = cb.conjunction();

            if (filterDTO.getName() != null) {
                predicate = cb.and(predicate, cb.like(cb.lower(root.get("name")),
                        "%" + filterDTO.getName().toLowerCase(Locale.ROOT) + "%"));
            }
            if (filterDTO.getPlace() != null) {
                predicate = cb.and(predicate, cb.like(cb.lower(root.get("place")),
                        "%" + filterDTO.getPlace().toLowerCase(Locale.ROOT) + "%"));
            }
            if (filterDTO.getOnline() != null) {
                predicate = cb.and(predicate, cb.equal(root.get("online"), filterDTO.getOnline()));
            }
            if (filterDTO.getMinDate() != null) {
                predicate = cb.and(predicate, cb.greaterThanOrEqualTo(root.get("date"), filterDTO.getMinDate()));
            }
            if (filterDTO.getMaxDate() != null) {
                predicate = cb.and(predicate, cb.lessThanOrEqualTo(root.get("date"), filterDTO.getMaxDate()));
            }
            if (filterDTO.getMinDuration() != null) {
                predicate = cb.and(predicate, cb.greaterThanOrEqualTo(root.get("duration"),
                        filterDTO.getMinDuration()));
            }
            if (filterDTO.getMaxDuration() != null) {
                predicate = cb.and(predicate, cb.lessThanOrEqualTo(root.get("duration"), filterDTO.getMaxDuration()));
            }

            return predicate;
        };
    }

    public static Specification<Attendee> filterAttendee(AttendeeFilterDTO filterDTO) {
        return (Root<Attendee> root, CriteriaQuery<?> query, CriteriaBuilder cb) -> {
            Predicate predicate = cb.conjunction();

            if (filterDTO.getName() != null) {
                predicate = cb.and(predicate, cb.like(cb.lower(root.get("name")), "%"
                        + filterDTO.getName().toLowerCase(Locale.ROOT) + "%"));

            }

            if (filterDTO.getEmail() != null) {
                predicate = cb.and(predicate, cb.like(cb.lower(root.get("email")), "%@"
                        + filterDTO.getEmail().toLowerCase(Locale.ROOT) + "%"));
            }

            return predicate;
        };
    }

}