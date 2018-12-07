package com.clsolutions.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Field;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.DBRef;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.Objects;

/**
 * A TimeTask.
 */
@Document(collection = "time_task")
public class TimeTask implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    private String id;

    @NotNull
    @Field("start")
    private LocalDate start;

    @Field("stop")
    private LocalDate stop;

    @Field("note")
    private String note;

    @DBRef
    @Field("user")
    @JsonIgnoreProperties("")
    private User user;

    @DBRef
    @Field("project")
    @JsonIgnoreProperties("")
    private Project project;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public LocalDate getStart() {
        return start;
    }

    public TimeTask start(LocalDate start) {
        this.start = start;
        return this;
    }

    public void setStart(LocalDate start) {
        this.start = start;
    }

    public LocalDate getStop() {
        return stop;
    }

    public TimeTask stop(LocalDate stop) {
        this.stop = stop;
        return this;
    }

    public void setStop(LocalDate stop) {
        this.stop = stop;
    }

    public String getNote() {
        return note;
    }

    public TimeTask note(String note) {
        this.note = note;
        return this;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public User getUser() {
        return user;
    }

    public TimeTask user(User user) {
        this.user = user;
        return this;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Project getProject() {
        return project;
    }

    public TimeTask project(Project project) {
        this.project = project;
        return this;
    }

    public void setProject(Project project) {
        this.project = project;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        TimeTask timeTask = (TimeTask) o;
        if (timeTask.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), timeTask.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "TimeTask{" +
            "id=" + getId() +
            ", start='" + getStart() + "'" +
            ", stop='" + getStop() + "'" +
            ", note='" + getNote() + "'" +
            "}";
    }
}
