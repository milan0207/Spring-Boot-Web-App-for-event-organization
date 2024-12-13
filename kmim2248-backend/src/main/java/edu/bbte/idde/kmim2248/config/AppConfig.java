package edu.bbte.idde.kmim2248.config;

public class AppConfig {
    private String profile;
    private DatabaseConfig database;

    // Getters and Setters
    public String getProfile() {
        return profile;
    }

    public void setProfile(String profile) {
        this.profile = profile;
    }

    public DatabaseConfig getDatabase() {
        return database;
    }

    public void setDatabase(DatabaseConfig database) {
        this.database = database;
    }
}
