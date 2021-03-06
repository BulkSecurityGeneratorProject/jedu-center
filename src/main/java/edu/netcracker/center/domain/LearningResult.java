package edu.netcracker.center.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import edu.netcracker.center.domain.enumeration.TypeOfResult;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.time.ZonedDateTime;
import java.util.Objects;

/**
 * A LearningResult.
 */
@Entity
@Table(name = "learning_result")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class LearningResult implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "type", nullable = false)
    private TypeOfResult type;

    @Column(name = "description")
    private String description;

    @NotNull
    @Column(name = "creation_time", nullable = false)
    private ZonedDateTime creationTime;

    @OneToOne(mappedBy = "learningResult")
    @JsonIgnore
    private Student student;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public TypeOfResult getType() {
        return type;
    }

    public void setType(TypeOfResult type) {
        this.type = type;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public ZonedDateTime getCreationTime() {
        return creationTime;
    }

    public void setCreationTime(ZonedDateTime creationTime) {
        this.creationTime = creationTime;
    }

    public Student getStudent() {
        return student;
    }

    public void setStudent(Student student) {
        this.student = student;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        LearningResult learningResult = (LearningResult) o;
        if (learningResult.id == null || id == null) {
            return false;
        }
        return Objects.equals(id, learningResult.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "LearningResult{" +
            "id=" + id +
            ", type='" + type + "'" +
            ", description='" + description + "'" +
            ", creationTime='" + creationTime + "'" +
            '}';
    }
}
