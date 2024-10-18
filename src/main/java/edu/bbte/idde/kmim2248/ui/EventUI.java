package edu.bbte.idde.kmim2248.ui;

import edu.bbte.idde.kmim2248.exception.FieldsEmptyException;
import edu.bbte.idde.kmim2248.model.Event;
import edu.bbte.idde.kmim2248.service.EventService;
import edu.bbte.idde.kmim2248.exception.EventNotFoundException;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.Map;

public class EventUI {
    private final EventService eventService;
    private JTable table;

    public EventUI(EventService eventService) {
        this.eventService = eventService;
    }

    public void createAndShowGUI() {
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

        JPanel emptyPanel = new JPanel();
        JLabel findLabel = new JLabel("Find Event:");
        JTextField findField = new JTextField();
        JButton findButton = new JButton("Find Event");
        JPanel emptyPanel2 = new JPanel();
        JButton updateButton = new JButton("Update Event");
        JButton deleteButton = new JButton("Delete Event");

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

        saveButton.addActionListener(e -> {

            if (nameField.getText().isEmpty() || placeField.getText().isEmpty() || durationField.getText().isEmpty()) {

                try {
                    JOptionPane.showMessageDialog(frame, "Field must be filled!");
                    throw new FieldsEmptyException("Field must be filled!");
                } catch (FieldsEmptyException ex) {
                    throw new RuntimeException(ex);
                }
            }

            if(dateField.getText().isEmpty()){
                dateField.setText(java.time.LocalDate.now().toString());
            }

            String name = nameField.getText();
            String place = placeField.getText();
            String date = dateField.getText();
            boolean online = onlineCheckBox.isSelected();
            int duration = Integer.parseInt(durationField.getText());

            Event event = new Event();
            event.setName(name);
            event.setPlace(place);
            event.setDate(date);
            event.setOnline(online);
            event.setDuration(duration);

            nameField.setText("");
            placeField.setText("");
            dateField.setText("");
            onlineCheckBox.setSelected(false);
            durationField.setText("");


            eventService.createEvent(event);

            refreshList();
            JOptionPane.showMessageDialog(frame, "Event saved successfully!");
        });

        findButton.addActionListener(e -> {
            if (findField.getText().isEmpty()) {
                try {
                    JOptionPane.showMessageDialog(frame, "Field must be filled!");
                    throw new FieldsEmptyException("Field must be filled!");
                } catch (FieldsEmptyException ex) {
                    throw new RuntimeException(ex);
                }
            }
            String name = findField.getText();
            Event event ;
            try {
                event = eventService.findEventByName(name);
            } catch (EventNotFoundException ex) {
                JOptionPane.showMessageDialog(frame, "Event not found!");
                throw new RuntimeException(ex);

            }
            if (event != null) {
                JOptionPane.showMessageDialog(frame,
                        "Event found");
                nameField.setText(event.getName());
                placeField.setText(event.getPlace());
                dateField.setText(event.getDate());
                onlineCheckBox.setSelected(event.getOnline());
                durationField.setText(String.valueOf(event.getDuration()));
            }
        });

        updateButton.addActionListener(e -> {
            if (nameField.getText().isEmpty() || placeField.getText().isEmpty() || dateField.getText().isEmpty() || durationField.getText().isEmpty()) {
                JOptionPane.showMessageDialog(frame, "All fields must be filled!");
                return;
            }
            String name = nameField.getText();
            String place = placeField.getText();
            String date = dateField.getText();
            boolean online = onlineCheckBox.isSelected();
            int duration = Integer.parseInt(durationField.getText());

            Event event = new Event();
            event.setName(name);
            event.setPlace(place);
            event.setDate(date);
            event.setOnline(online);
            event.setDuration(duration);

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
            }
        });

        deleteButton.addActionListener(e -> {
            if (nameField.getText().isEmpty()) {
                JOptionPane.showMessageDialog(frame, "Name field must be filled!");
                return;
            }
            String name = nameField.getText();
            try {
                eventService.deleteEvent(name);
                JOptionPane.showMessageDialog(frame, "Event deleted successfully!");
                refreshList();
            } catch (EventNotFoundException eventNotFoundException) {
                JOptionPane.showMessageDialog(frame, "Event not found.");
            }
        });

        Map<String, Event> events = eventService.getAllEvents();

        String[] columnNames = {"Name", "Place", "Date", "Online", "Duration"};
        DefaultTableModel model = new DefaultTableModel(columnNames, 0);

        for (Event event : events.values()) {
            Object[] row = {event.getName(), event.getPlace(), event.getDate(), event.getOnline(), event.getDuration()};
            model.addRow(row);
        }

        table = new JTable(model);

        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setPreferredSize(new Dimension(500, 200));
        frame.add(scrollPane, BorderLayout.SOUTH);

        frame.setVisible(true);
    }

    private void refreshList() {
        Map<String, Event> events = eventService.getAllEvents();
        DefaultTableModel model = (DefaultTableModel) table.getModel();
        model.setRowCount(0);
        for (Event event : events.values()) {
            Object[] row = {event.getName(), event.getPlace(), event.getDate(), event.getOnline(), event.getDuration()};
            model.addRow(row);
        }
    }
}
