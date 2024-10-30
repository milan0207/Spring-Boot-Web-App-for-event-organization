package edu.bbte.idde.kmim2248;

import edu.bbte.idde.kmim2248.dao.EventDao;
import edu.bbte.idde.kmim2248.dao.exception.DaoOperationException;
import edu.bbte.idde.kmim2248.dao.impl.InMemDaoImpl;
import edu.bbte.idde.kmim2248.service.EventService;
import edu.bbte.idde.kmim2248.ui.EventUI;

public class EventApplication{
    public static void main(String[] args) throws DaoOperationException {
        EventService eventService = new EventService("mysql"); //"inmemory" or "mysql"
        EventUI eventUI = new EventUI(eventService);

        eventUI.createAndShowGUI();
    }
}
