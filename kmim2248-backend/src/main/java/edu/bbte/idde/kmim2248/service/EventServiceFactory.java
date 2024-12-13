package edu.bbte.idde.kmim2248.service;

public class EventServiceFactory {
    private static EventService eventService;

    public static synchronized EventService getEventService() {
        if (eventService != null) {
            return eventService;
        }

        eventService = new EventService();
        return eventService;
    }
}
