package edu.netcracker.center.domain;

import edu.netcracker.center.domain.enumeration.UniversityEnumeration;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.envers.Audited;
import org.hibernate.envers.NotAudited;
import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.NotEmpty;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.Objects;

/**
 * A Student.
 */
@Entity
@Audited
@Table(name = "student")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Student implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @NotEmpty
    @Column(name = "last_name", nullable = false)
    private String lastName;

    @Column(name = "first_name")
    private String firstName;

    @Column(name = "middle_name")
    private String middleName;

    @NotEmpty
    @Column(name = "email", nullable = false)
    @Email
    private String email;

    @Column(name = "phone")
    private String phone;

    @Enumerated(EnumType.STRING)
    @Column(name = "university")
    private UniversityEnumeration university;

    @Column(name = "specialty")
    private String specialty;

    @Column(name = "faculty")
    private String faculty;

    @Column(name = "course")
    private String course;

    @NotNull
    @Column(name = "is_active", nullable = false)
    private Boolean isActive;

    @NotNull
    @Column(name = "got_job", nullable = false)
    private Boolean gotJob;

    @Column(name = "comment")
    private String comment;

    @OneToOne(cascade = CascadeType.REMOVE, orphanRemoval = true)
    @NotAudited
    private User user;

    @ManyToOne
    @JoinColumn(name = "group_of_student_id")
    @NotAudited
    private GroupOfStudent groupOfStudent;

    @ManyToOne
    @JoinColumn(name = "curator_id")
    @NotAudited
    private Curator curator;

    @OneToOne
    @NotAudited
    private Form form;

    @OneToOne(cascade = CascadeType.REMOVE, orphanRemoval = true)
    @NotAudited
    private LearningResult learningResult;

    @ManyToOne
    @NotAudited
    @JoinColumn(name = "students_set_id")
    private StudentsSet studentsSet;

    @ManyToOne
    @NotAudited
    @NotNull
    @JoinColumn(name = "learning_type_id")
    private LearningType learningType;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getMiddleName() {
        return middleName;
    }

    public void setMiddleName(String middleName) {
        this.middleName = middleName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public UniversityEnumeration getUniversity() {
        return university;
    }

    public void setUniversity(UniversityEnumeration university) {
        this.university = university;
    }

    public String getSpecialty() {
        return specialty;
    }

    public void setSpecialty(String specialty) {
        this.specialty = specialty;
    }

    public String getFaculty() {
        return faculty;
    }

    public void setFaculty(String faculty) {
        this.faculty = faculty;
    }

    public String getCourse() {
        return course;
    }

    public void setCourse(String course) {
        this.course = course;
    }

    public Boolean getIsActive() {
        return isActive;
    }

    public void setIsActive(Boolean isActive) {
        this.isActive = isActive;
    }

    public Boolean getGotJob() {
        return gotJob;
    }

    public void setGotJob(Boolean gotJob) {
        this.gotJob = gotJob;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public GroupOfStudent getGroupOfStudent() {
        return groupOfStudent;
    }

    public void setGroupOfStudent(GroupOfStudent groupOfStudent) {
        this.groupOfStudent = groupOfStudent;
    }

    public Curator getCurator() {
        return curator;
    }

    public void setCurator(Curator curator) {
        this.curator = curator;
    }

    public Form getForm() {
        return form;
    }

    public void setForm(Form form) {
        this.form = form;
    }

    public LearningResult getLearningResult() {
        return learningResult;
    }

    public void setLearningResult(LearningResult learningResult) {
        this.learningResult = learningResult;
    }

    public StudentsSet getStudentsSet() {
        return studentsSet;
    }

    public void setStudentsSet(StudentsSet studentsSet) {
        this.studentsSet = studentsSet;
    }

    public LearningType getLearningType() {
        return learningType;
    }

    public void setLearningType(LearningType learningType) {
        this.learningType = learningType;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Student student = (Student) o;
        if (student.id == null || id == null) {
            return false;
        }
        return Objects.equals(id, student.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "Student{" +
            "id=" + id +
            ", lastName='" + lastName + "'" +
            ", firstName='" + firstName + "'" +
            ", middleName='" + middleName + "'" +
            ", email='" + email + "'" +
            ", phone='" + phone + "'" +
            ", university='" + university + "'" +
            ", specialty='" + specialty + "'" +
            ", faculty='" + faculty + "'" +
            ", course='" + course + "'" +
            ", isActive='" + isActive + "'" +
            ", gotJob='" + gotJob + "'" +
            ", comment='" + comment + "'" +
            '}';
    }
}
