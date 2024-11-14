package edu.bbte.idde.kmim2248.dao.impl;

import edu.bbte.idde.kmim2248.dao.EventDao;
import edu.bbte.idde.kmim2248.dao.exception.DaoOperationException;
import edu.bbte.idde.kmim2248.dao.exception.EventNotFoundException;
import edu.bbte.idde.kmim2248.dao.impl.datasource.DataSource;
import edu.bbte.idde.kmim2248.model.Event;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.*;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;


public class EventJdbcDaoImpl implements EventDao {

    private static final Logger logger = LoggerFactory.getLogger(EventJdbcDaoImpl.class);

    public EventJdbcDaoImpl() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            logger.error("MySQL Driver not found", e);
        }
    }

    @Override
    public void save(Event event) throws DaoOperationException {
        String sql = "INSERT INTO events (name, place, date, online, duration) VALUES (?, ?, ?, ?, ?)";
        try (Connection conn = DataSource.getConnection(); PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, event.getName());
            stmt.setString(2, event.getPlace());
            stmt.setDate(3, Date.valueOf(event.getDate()));
            stmt.setBoolean(4, event.isOnline());
            stmt.setInt(5, event.getDuration());
            stmt.executeUpdate();
            logger.info("Event saved: {}", event);

        } catch (SQLException e) {
            logger.error("Error saving event", e);
            throw new DaoOperationException("Error saving event", e);
        }

    }

    @Override
    public void update(Event event) throws EventNotFoundException, DaoOperationException {
        String sql = "UPDATE events SET name = ?, place = ?, date = ?, online = ?, duration = ? WHERE id = ?";
        try (Connection conn = DataSource.getConnection(); PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, event.getName());
            stmt.setString(2, event.getPlace());
            stmt.setDate(3, Date.valueOf(event.getDate()));
            stmt.setBoolean(4, event.isOnline());
            stmt.setInt(5, event.getDuration());
            stmt.setInt(6, event.getId());
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
    public void delete(int id) throws EventNotFoundException, DaoOperationException {
        String sql = "DELETE FROM events WHERE id = ?";
        try (Connection conn = DataSource.getConnection(); PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, id);
            int rows = stmt.executeUpdate();
            logger.error("Rows: {}", rows);
            if (rows == 0) {
                throw new EventNotFoundException("Event not found: " + id);
            }
            logger.info("Event deleted: {}", id);

        } catch (SQLException e) {
            logger.error("Error deleting event", e);
            throw new DaoOperationException("Error deleting event", e);
        }

    }

    @Override
    public Event findById(int id) throws EventNotFoundException, DaoOperationException {
        String sql = "SELECT * FROM events WHERE id = ?";
        try (Connection conn = DataSource.getConnection(); PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, id);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    Event event = new Event(rs.getString("name"), rs.getString("place"),
                            rs.getDate("date").toLocalDate(), rs.getBoolean("online"), rs.getInt("duration"));
                    event.setId(rs.getInt("id"));
                    logger.info("Event found: {}", event);
                    return event;
                } else {
                    throw new EventNotFoundException("Event not found: " + id);
                }
            }

        } catch (SQLException e) {
            logger.error("Error finding event", e);
            throw new DaoOperationException("Error finding event", e);
        }
    }

    @Override
    public Event findByName(String Name) throws EventNotFoundException, DaoOperationException {
        String sql = "SELECT * FROM events WHERE name = ?";
        try (Connection conn = DataSource.getConnection(); PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, Name);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    Event event = new Event(rs.getString("name"), rs.getString("place"),
                            rs.getDate("date").toLocalDate(), rs.getBoolean("online"), rs.getInt("duration"));
                    event.setId(rs.getInt("id"));
                    logger.info("Event found: {}", event);
                    return event;
                } else {
                    throw new EventNotFoundException("Event not found: " + Name);
                }
            }

        } catch (SQLException e) {
            logger.error("Error finding event", e);
            throw new DaoOperationException("Error finding event", e);
        }
    }
    @Override
    public boolean existsByName(String eventName) throws DaoOperationException {
        String sql = "SELECT * FROM events WHERE name = ?";
        try (Connection conn = DataSource.getConnection(); PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, eventName);
            try (ResultSet rs = stmt.executeQuery()) {
                return rs.next();
            }

        } catch (SQLException e) {
            logger.error("Error checking event existence", e);
            throw new DaoOperationException("Error checking event existence", e);
        }
    }

    @Override
    public Map<Integer, Event> getAllEvents() throws DaoOperationException {
        String sql = "SELECT * FROM events";
        Map<Integer, Event> events = new ConcurrentHashMap<>();
        try (Connection conn = DataSource.getConnection(); Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                Event event = new Event(rs.getString("name"), rs.getString("place"),
                        rs.getDate("date").toLocalDate(), rs.getBoolean("online"), rs.getInt("duration"));
                event.setId(rs.getInt("id"));
                logger.info("Event found: {}", event);
                events.put(event.getId(), event);
            }

        } catch (SQLException e) {
            logger.error("Error finding events", e);
            throw new DaoOperationException("Error finding events", e);
        }
        return events;
    }
    @Override
    public boolean existsById(int id) throws DaoOperationException {
        String sql = "SELECT * FROM events WHERE id = ?";
        try (Connection conn = DataSource.getConnection(); PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, id);
            try (ResultSet rs = stmt.executeQuery()) {
                return rs.next();
            }

        } catch (SQLException e) {
            logger.error("Error checking event existence", e);
            throw new DaoOperationException("Error checking event existence", e);
        }
    }
}
