package ru.mvp.database.entities;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.Objects;

@Entity
@Table(name = "entity_log", schema = "public", catalog = "eslbase")
public class EntityLog {
    private int id;
    private Timestamp time;
    private String name;
    private String source;
    private String type;
    private String event;

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Basic
    @Column(name = "time")
    public Timestamp getTime() {
        return time;
    }

    public void setTime(Timestamp time) {
        this.time = time;
    }

    @Basic
    @Column(name = "name")
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Basic
    @Column(name = "source")
    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    @Basic
    @Column(name = "type")
    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    @Basic
    @Column(name = "event")
    public String getEvent() {
        return event;
    }

    public void setEvent(String event) {
        this.event = event;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        EntityLog entityLog = (EntityLog) o;
        return id == entityLog.id &&
                Objects.equals(time, entityLog.time) &&
                Objects.equals(name, entityLog.name) &&
                Objects.equals(source, entityLog.source) &&
                Objects.equals(type, entityLog.type) &&
                Objects.equals(event, entityLog.event);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, time, name, source, type, event);
    }
}
