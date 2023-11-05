package edu.eci.labinfo.labtodo.model;

import java.time.LocalDate;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@EqualsAndHashCode
public class Semester {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long periodId;
    private String semesterName;
    private LocalDate startDate;
    private LocalDate endDate;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "semester")
    private List<Task> tasks;

    public Semester() {}

    @Override
    public String toString() {
        return "Semester [periodId=" + periodId + ", startDate=" + startDate + ", endDate=" + endDate + "]";
    }

}
