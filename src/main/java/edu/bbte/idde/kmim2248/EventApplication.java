package edu.bbte.idde.kmim2248;

import edu.bbte.idde.kmim2248.dao.EventDao;
import edu.bbte.idde.kmim2248.dao.impl.EventDaoImpl;
import edu.bbte.idde.kmim2248.service.EventService;
import edu.bbte.idde.kmim2248.ui.EventUI;

public class EventApplication{
    public static void main(String[] args) {
        EventDao eventDao = new EventDaoImpl();
        EventService eventService = new EventService(eventDao);
        EventUI eventUI = new EventUI(eventService);

        eventUI.createAndShowGUI();
    }
}
