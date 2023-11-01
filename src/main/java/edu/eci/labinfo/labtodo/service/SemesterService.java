package edu.eci.labinfo.labtodo.service;


import java.util.List;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.*;
import edu.eci.labinfo.labtodo.data.SemesterRepository;
import edu.eci.labinfo.labtodo.model.Semester;

@Service
public class SemesterService {
    @Autowired
    private final SemesterRepository semesterRepository;

    public SemesterService(SemesterRepository semesterRepository){
        this.semesterRepository = semesterRepository;
    }
    public Semester addSemester(Semester semester){
        return semesterRepository.save(semester);
    }

    public Semester getSemester(Long semesterId){
        return semesterRepository.findById(semesterId).get();
    }

    public List<Semester> getAllSemesters(){
        return semesterRepository.findAll();
    }

    @Transactional(propagation = Propagation.REQUIRED)
    public Semester updateSemester(Semester semester){
        if(semesterRepository.existsById(semester.getPeriodId())){
            return semesterRepository.save(semester);
        }
        return null;
    }

    public void deleteTask(Long semesterId){
        semesterRepository.deleteById(semesterId);
    }

    public void deleteAllSemesters(){
        semesterRepository.deleteAll();
    }


}
