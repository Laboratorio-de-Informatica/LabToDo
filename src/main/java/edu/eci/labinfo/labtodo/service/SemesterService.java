package edu.eci.labinfo.labtodo.service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.stereotype.Service;
import edu.eci.labinfo.labtodo.data.SemesterRepository;
import edu.eci.labinfo.labtodo.model.Semester;

@Service
public class SemesterService {

    @Autowired
    private SemesterRepository semesterRepository;

    public Semester addSemester(Semester semester) {
        return semesterRepository.save(semester);
    }

    public Semester getSemester(Long semesterId) {
        return semesterRepository.findById(semesterId).get();
    }

    public Semester getSemesterByName(String semesterName) {
        Optional<Semester> semester = semesterRepository.findBySemesterName(semesterName);
        if (semester.isPresent()) {
            return semester.get();
        }
        return null;
    }

    public Semester getCurrentSemester() {
        LocalDate date = LocalDate.now();
        Optional<Semester> semester = semesterRepository.findByStartDateAndEndDate(date);
        if (semester.isPresent()) {
            return semester.get();
        }
        return null;
    }

    public List<Semester> getAllSemesters() {
        return semesterRepository.findAll();
    }

    @Transactional(propagation = Propagation.REQUIRED)
    public Semester updateSemester(Semester semester) {
        if (semesterRepository.existsById(semester.getPeriodId())) {
            return semesterRepository.save(semester);
        }
        return null;
    }

    public void deleteTask(Long semesterId) {
        semesterRepository.deleteById(semesterId);
    }

    public void deleteAllSemesters() {
        semesterRepository.deleteAll();
    }

}
