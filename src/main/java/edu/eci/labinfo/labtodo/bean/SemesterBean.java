package edu.eci.labinfo.labtodo.bean;

import java.util.List;

import javax.annotation.ManagedBean;
import javax.faces.bean.ApplicationScoped;
import javax.faces.context.FacesContextWrapper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import edu.eci.labinfo.labtodo.data.SemesterRepository;
import edu.eci.labinfo.labtodo.model.Semester;
import edu.eci.labinfo.labtodo.service.PrimeFacesWrapper;
import edu.eci.labinfo.labtodo.service.SemesterService;

@ManagedBean
@Component
@ApplicationScoped
public class SemesterBean {

    @Autowired
    SemesterService semesterService;

    @Autowired
    private static FacesContextWrapper facesContextWrapper;

    @Autowired
    private PrimeFacesWrapper primeFacesWrapper;

    private List<Semester> semesters;

    private Semester currenSemester;

    
    

    
}
