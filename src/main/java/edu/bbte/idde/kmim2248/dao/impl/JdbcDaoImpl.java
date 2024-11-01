package edu.bbte.idde.kmim2248.dao.impl;

import edu.bbte.idde.kmim2248.dao.EventDao;
import edu.bbte.idde.kmim2248.dao.exception.DaoOperationException;
import edu.bbte.idde.kmim2248.dao.exception.EventAlreadyExistsException;
import edu.bbte.idde.kmim2248.dao.exception.EventNotFoundException;
import edu.bbte.idde.kmim2248.model.Event;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.*;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;


public class JdbcDaoImpl implements EventDao {

    private static final Logger logger = LoggerFactory.getLogger(JdbcDaoImpl.class);

    private static final String URL = "jdbc:mysql://localhost:3306/eventdb?useSSL=false&serverTimezone=UTC";
    private static final String USERNAME = "root";
    private static final String PASSWORD = "1234";

    public JdbcDaoImpl() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            logger.error("MySQL Driver not found", e);
        }
    }

    @Override
    public void save(Event event) throws DaoOperationException {
        String sql = "INSERT INTO events (name, place, date, online, duration) VALUES (?, ?, ?, ?, ?)";
        try (Connection conn = DriverManager.getConnection(URL, USERNAME, PASSWORD);
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, event.getName());
            stmt.setString(2, event.getPlace());
            stmt.setDate(3, Date.valueOf(event.getDate()));
            stmt.setBoolean(4, event.getOnline());
            stmt.setInt(5, event.getDuration());
            stmt.executeUpdate();
            logger.info("Event saved: {}", event);

        } catch (SQLException e) {
            logger.error("Error saving event", e);
            throw new DaoOperationException("Error saving event", e);
        }

    }

    @Override
    public void update(Event event) throws EventNotFoundException, DaoOperationException{
        String sql = "UPDATE events SET place = ?, date = ?, online = ?, duration = ? WHERE name = ?";
        try (Connection conn = DriverManager.getConnection(URL, USERNAME, PASSWORD);
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, event.getPlace());
            stmt.setDate(2, Date.valueOf(event.getDate()));
            stmt.setBoolean(3, event.getOnline());
            stmt.setInt(4, event.getDuration());
            stmt.setString(5, event.getName());
            int rows = stmt.executeUpdate();
            if (rows == 0) {
                throw new EventNotFoundException("Event not found: " + event.getName());
            }
            logger.info("Event updated: {}", event);

        } catch (SQLException e) {
            logger.error("Error updating event", e);
            throw new DaoOperationException("Error updating event", e);
        }

    }

    @Override
    public void delete(String eventName) throws EventNotFoundException, DaoOperationException{
        String sql = "DELETE FROM events WHERE name = ?";
        try (Connection conn = DriverManager.getConnection(URL, USERNAME, PASSWORD);
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, eventName);
            int rows = stmt.executeUpdate();
            if (rows == 0) {
                throw new EventNotFoundException("Event not found: " + eventName);
            }
            logger.info("Event deleted: {}", eventName);

        } catch (SQLException e) {
            logger.error("Error deleting event", e);
            throw new DaoOperationException("Error deleting event", e);
        }

    }

    @Override
    public Optional<Event> findByName(String eventName) throws EventNotFoundException, DaoOperationException {
        String sql = "SELECT * FROM events WHERE name = ?";
        try (Connection conn = DriverManager.getConnection(URL, USERNAME, PASSWORD);
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, eventName);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    Event event = new Event();
                    event.setName(rs.getString("name"));
                    event.setPlace(rs.getString("place"));
                    event.setDate(rs.getDate("date").toLocalDate());
                    event.setOnline(rs.getBoolean("online"));
                    event.setDuration(rs.getInt("duration"));
                    logger.info("Event found: {}", event);
                    return Optional.of(event);
                } else {
                    throw new EventNotFoundException("Event not found: " + eventName);
                }
            }

        } catch (SQLException e) {
            logger.error("Error finding event", e);
            throw new DaoOperationException("Error finding event", e);
        }
    }

    @Override
    public Map<String, Event> getAllEvents() throws DaoOperationException{
        String sql = "SELECT * FROM events";
        Map<String, Event> events = new HashMap<>();
        try (Connection conn = DriverManager.getConnection(URL, USERNAME, PASSWORD);
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                Event event = new Event();
                event.setName(rs.getString("name"));
                event.setPlace(rs.getString("place"));
                event.setDate(rs.getDate("date").toLocalDate());
                event.setOnline(rs.getBoolean("online"));
                event.setDuration(rs.getInt("duration"));
                logger.info("Event found: {}", event);
                events.put(event.getName(), event);
            }

        } catch (SQLException e) {
            logger.error("Error finding events", e);
            throw new DaoOperationException("Error finding events", e);
        }
        return events;
    }
}
