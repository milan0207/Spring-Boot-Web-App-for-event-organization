package edu.bbte.idde.kmim2248;

import edu.bbte.idde.kmim2248.dao.exception.DaoOperationException;
import edu.bbte.idde.kmim2248.service.EventService;
import edu.bbte.idde.kmim2248.ui.EventUI;

public class DesktopApplication {
    public static void main(String[] args) throws DaoOperationException {
        EventService eventService = new EventService();
        EventUI eventUI = new EventUI(eventService);

        eventUI.createAndShowGUI();
    }
}
