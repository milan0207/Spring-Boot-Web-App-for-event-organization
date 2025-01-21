package edu.bbte.idde.kmim2248.service;

import edu.bbte.idde.kmim2248.dao.EventDao;
import edu.bbte.idde.kmim2248.dao.impl.AttendeeJpa;
import edu.bbte.idde.kmim2248.model.Event;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.scheduling.annotation.Scheduled;

import edu.bbte.idde.kmim2248.model.Attendee;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class MaintenanceTaskService {

    @Autowired
    private EventDao eventDao;

    @Autowired
    private AttendeeJpa attendeeJpa;

    @Scheduled(cron = "0 0 0 * * ?") // Futtatás minden nap ejfelkor
    @Scheduled(fixedRate = 37000) //Teszteles erdekeben minden 30mp
    public void deleteOldEvents() {
        List<Event> oldEvents = eventDao.findAll().stream()
                .filter(event -> event.getDate().isBefore(LocalDate.now().minusYears(10)))
                .collect(Collectors.toList());

        if (!oldEvents.isEmpty()) {
            eventDao.deleteAll(oldEvents);
            System.out.println("TASK: Deleted " + oldEvents.size() + " old events (older than 10 years).");
        }
    }

    @Scheduled(cron = "0 30 0 * * ?") // Futtatás minden nap fel 1 kor
    @Scheduled(fixedRate = 35000) //Teszteles erdekeben minden 30mp
    public void deletePastEventsWithoutAttendees() {
        List<Event> pastEvents = eventDao.findAll().stream()
                .filter(event -> event.getDate().isBefore(LocalDate.now()) && event.getAttendees().isEmpty())
                .collect(Collectors.toList());

        if (!pastEvents.isEmpty()) {
            eventDao.deleteAll(pastEvents);
            System.out.println("TASK: Deleted " + pastEvents.size() + " past events without attendees.");
        }
    }

    @Scheduled(cron = "0 0 1 * * ?") //Minden nap 1 kor
    @Scheduled(fixedRate = 30000) //Teszteles erdekeben minden 30mp
    public void deleteDuplicateAttendees() {
        List<Attendee> attendees = attendeeJpa.findAll();

        Map<String, List<Attendee>> duplicateGroups = attendees.stream()
                .collect(Collectors.groupingBy(Attendee::getEmail));

        List<Attendee> duplicates = duplicateGroups.values().stream()
                .filter(group -> group.size() > 1)
                .flatMap(group -> group.stream().skip(1))
                .collect(Collectors.toList());

        if (!duplicates.isEmpty()) {
            attendeeJpa.deleteAll(duplicates);
            System.out.println("TASK Deleted " + duplicates.size() + " duplicate attendees with the same email.");
        }
    }

}
