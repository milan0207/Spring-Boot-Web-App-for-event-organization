package edu.bbte.idde.kmim2248.model;

public class BaseEntity {
    protected int id;

    public BaseEntity(int id) {
        this.id = id;
    }

    public BaseEntity() {
    }
    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }

}
