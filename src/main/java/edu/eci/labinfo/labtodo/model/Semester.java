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

@Entity
public class Semester {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long periodId;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "semester")
    private List<Task> tasks;

    @Column(name = "name")
    private String semesterName;

    @Column(name = "startDate")
    private LocalDate startDate;

    @Column(name = "endDate")
    private LocalDate endDate;

    public Long getPeriodId() {
        return periodId;
    }

    @Override
    public String toString() {
        return "Semester [periodId=" + periodId + ", startDate=" + startDate + ", endDate=" + endDate + "]";
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((periodId == null) ? 0 : periodId.hashCode());
        result = prime * result + ((startDate == null) ? 0 : startDate.hashCode());
        result = prime * result + ((endDate == null) ? 0 : endDate.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        Semester other = (Semester) obj;
        if (periodId == null) {
            if (other.periodId != null)
                return false;
        } else if (!periodId.equals(other.periodId))
            return false;
        if (startDate == null) {
            if (other.startDate != null)
                return false;
        } else if (!startDate.equals(other.startDate))
            return false;
        if (endDate == null) {
            if (other.endDate != null)
                return false;
        } else if (!endDate.equals(other.endDate))
            return false;
        return true;
    }

    public void setPeriodId(Long periodId) {
        this.periodId = periodId;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }

    public LocalDate getEndDate() {
        return endDate;
    }

    public void setEndDate(LocalDate endDate) {
        this.endDate = endDate;
    }

    public String getSemesterName() {
        return semesterName;
    }

    public void setSemesterName(String semesterName) {
        this.semesterName = semesterName;
    }

}
