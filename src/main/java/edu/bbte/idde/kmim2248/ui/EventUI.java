package edu.bbte.idde.kmim2248.ui;

import edu.bbte.idde.kmim2248.dao.exception.DaoOperationException;
import edu.bbte.idde.kmim2248.dao.exception.EventAlreadyExistsException;
import edu.bbte.idde.kmim2248.dao.impl.EventJdbcDaoImpl;
import edu.bbte.idde.kmim2248.model.Event;
import edu.bbte.idde.kmim2248.service.EventService;
import edu.bbte.idde.kmim2248.dao.exception.EventNotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.Map;

public class EventUI {
    private final EventService eventService;
    private JTable table;
    private static final Logger logger = LoggerFactory.getLogger(EventJdbcDaoImpl.class);

    public EventUI(EventService eventService) {
        this.eventService = eventService;
    }

    public void createAndShowGUI() throws DaoOperationException {

        JFrame frame = new JFrame("Event Management");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(1000, 800);
        frame.setLayout(new BorderLayout());

        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(0, 2));

        JLabel nameLabel = new JLabel("Name:");
        JTextField nameField = new JTextField();
        JLabel placeLabel = new JLabel("Place:");
        JTextField placeField = new JTextField();
        JLabel dateLabel = new JLabel("Date: (if left empty todays date will be applied)");
        JTextField dateField = new JTextField();
        JLabel onlineLabel = new JLabel("Online:");
        JCheckBox onlineCheckBox = new JCheckBox();
        JLabel durationLabel = new JLabel("Duration (minutes):");
        JTextField durationField = new JTextField();
        JButton saveButton = new JButton("Save Event");
        JButton updateButton = new JButton("Update Event");
        JButton deleteButton = new JButton("Delete Event");

        JPanel emptyPanel = new JPanel();
        JLabel findLabel = new JLabel("Find Event:");
        JTextField findField = new JTextField();
        JButton findButton = new JButton("Find Event");
        JPanel emptyPanel2 = new JPanel();


        panel.add(nameLabel);
        panel.add(nameField);
        panel.add(placeLabel);
        panel.add(placeField);
        panel.add(dateLabel);
        panel.add(dateField);
        panel.add(onlineLabel);
        panel.add(onlineCheckBox);
        panel.add(durationLabel);
        panel.add(durationField);
        panel.add(saveButton);
        panel.add(emptyPanel);
        panel.add(findLabel);
        panel.add(findField);
        panel.add(findButton);
        panel.add(emptyPanel2);
        panel.add(updateButton);
        panel.add(deleteButton);

        frame.add(panel, BorderLayout.CENTER);

        saveButton.addActionListener(e -> save(findField, frame, nameField, placeField, dateField, onlineCheckBox, durationField));

        findButton.addActionListener(e -> find(findField, frame, nameField, placeField, dateField, onlineCheckBox, durationField));

        updateButton.addActionListener(e -> update(nameField, placeField, dateField, onlineCheckBox, durationField, frame));

        deleteButton.addActionListener(e -> delete(nameField, frame));


        frame.add(createScrollPane(), BorderLayout.SOUTH);

        frame.setVisible(true);
    }

    private void save(JTextField findField, JFrame frame, JTextField nameField, JTextField placeField, JTextField dateField, JCheckBox onlineCheckBox, JTextField durationField) {
        if (nameField.getText().isEmpty() || placeField.getText().isEmpty() || durationField.getText().isEmpty()) {

            JOptionPane.showMessageDialog(frame, "Field must be filled!");
        }

        if (dateField.getText().isEmpty()) {
            dateField.setText(LocalDate.now().toString());
        }

        String name = nameField.getText();
        String place = placeField.getText();
        String stringDate = dateField.getText();
        boolean online = onlineCheckBox.isSelected();
        int duration = Integer.parseInt(durationField.getText());


        LocalDate date = null;
        try {
            date = LocalDate.parse(stringDate);
        } catch (DateTimeParseException ex) {
            JOptionPane.showMessageDialog(frame, "Date format not valid");

        }

        Event event = new Event(name, place, date, online, duration);

        nameField.setText("");
        placeField.setText("");
        dateField.setText("");
        onlineCheckBox.setSelected(false);
        durationField.setText("");


        try {
            eventService.createEvent(event);
        } catch (DaoOperationException ex) {
            JOptionPane.showMessageDialog(frame, "Error saving event");
            logger.warn("Error saving event", ex);
            return;
        } catch (EventAlreadyExistsException ex) {
            JOptionPane.showMessageDialog(frame, "Event already exists");
            logger.warn("Event already exists", ex);
            return;
        }
        try {
            refreshList();
        } catch (DaoOperationException ex) {
            JOptionPane.showMessageDialog(frame, "Error refreshing list");
            logger.warn("Error refreshing list", ex);
        }
        JOptionPane.showMessageDialog(frame, "Event saved successfully!");

    }

    private void delete(JTextField nameField, JFrame frame) {
        if (nameField.getText().isEmpty()) {
            JOptionPane.showMessageDialog(frame, "Name field must be filled!");
            return;
        }
        String name = nameField.getText();
        try {
            eventService.deleteEvent(name);
            JOptionPane.showMessageDialog(frame, "Event deleted successfully!");
            refreshList();
        } catch (EventNotFoundException | DaoOperationException eventNotFoundException) {
            JOptionPane.showMessageDialog(frame, "Event not found.");
        }
    }

    private void find(JTextField findField, JFrame frame, JTextField nameField, JTextField placeField, JTextField dateField, JCheckBox onlineCheckBox, JTextField durationField) {
        if (findField.getText().isEmpty()) {
            JOptionPane.showMessageDialog(frame, "Field must be filled!");
            return;
        }
        String name = findField.getText();
        Event event = null;
        try {
            event = eventService.findEventByName(name);
        } catch (EventNotFoundException | DaoOperationException ex) {
            JOptionPane.showMessageDialog(frame, "Event not found!");
            logger.warn("Event not found", ex);

        }
        if (event != null) {
            JOptionPane.showMessageDialog(frame, "Event found");
            nameField.setText(event.getName());
            placeField.setText(event.getPlace());
            String stringDate = event.getDate().toString();
            dateField.setText(stringDate);
            onlineCheckBox.setSelected(event.isOnline());
            durationField.setText(String.valueOf(event.getDuration()));
        }
    }

    private void update(JTextField nameField, JTextField placeField, JTextField dateField, JCheckBox onlineCheckBox, JTextField durationField, JFrame frame) {
        if (nameField.getText().isEmpty() || placeField.getText().isEmpty() || dateField.getText().isEmpty() || durationField.getText().isEmpty()) {
            JOptionPane.showMessageDialog(frame, "All fields must be filled!");
            return;
        }
        String name = nameField.getText();
        String place = placeField.getText();
        String stringDate = dateField.getText();
        boolean online = onlineCheckBox.isSelected();
        int duration = Integer.parseInt(durationField.getText());


        LocalDate date = null;
        try {
            date = LocalDate.parse(stringDate);
        } catch (DateTimeParseException ex) {
            JOptionPane.showMessageDialog(frame, "Date format not valid");
            logger.warn("Date format not valid", ex);
        }

        Event event = new Event(name, place, date, online, duration);

        nameField.setText("");
        placeField.setText("");
        dateField.setText("");
        onlineCheckBox.setSelected(false);
        durationField.setText("");

        try {
            eventService.updateEvent(event);
            JOptionPane.showMessageDialog(frame, "Event updated successfully!");
            refreshList();
        } catch (EventNotFoundException eventNotFoundException) {
            JOptionPane.showMessageDialog(frame, "Event not found.");
        } catch (DaoOperationException ex) {
            JOptionPane.showMessageDialog(frame, "Error updating event");
            logger.warn("Error updating event", ex);
        }
    }

    private JScrollPane createScrollPane() throws DaoOperationException {
        Map<String, Event> events = eventService.getAllEvents();

        String[] columnNames = {"Name", "Place", "Date", "Online", "Duration"};
        DefaultTableModel model = new DefaultTableModel(columnNames, 0);

        for (Event event : events.values()) {
            Object[] row = {event.getName(), event.getPlace(), event.getDate(), event.isOnline(), event.getDuration()};
            model.addRow(row);
        }

        table = new JTable(model);

        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setPreferredSize(new Dimension(500, 200));
        return scrollPane;
    }

    private void refreshList() throws DaoOperationException {
        Map<String, Event> events = eventService.getAllEvents();
        DefaultTableModel model = (DefaultTableModel) table.getModel();
        model.setRowCount(0);
        for (Event event : events.values()) {
            Object[] row = {event.getName(), event.getPlace(), event.getDate(), event.isOnline(), event.getDuration()};
            model.addRow(row);
        }
    }
}
