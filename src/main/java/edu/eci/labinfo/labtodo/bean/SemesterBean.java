package edu.eci.labinfo.labtodo.bean;

import java.util.Date;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.ManagedBean;
import javax.faces.application.FacesMessage;
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
    private String semesterName;
    private LocalDate startDate;
    private Semester currentSemester;
    private LocalDate endDate;
    private List<String> selectedUsers = new ArrayList<>();

    public Semester getCurrentSemester() {
        return currentSemester;
    }

    public void setCurrentSemester(Semester currentSemester) {
        this.currentSemester = currentSemester;
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

    public List<String> getSelectedUsers() {
        return selectedUsers;
    }

    public void setSelectedUsers(List<String> selectedUsers) {
        this.selectedUsers = selectedUsers;
    }

    public List<Semester> getSemesters() {
        return semesters;
    }

    public void setSemesters(List<Semester> semesters) {
        this.semesters = semesters;
    }

    public void openNewYear() {
        selectedUsers.clear();
        this.currentSemester = new Semester();
    }

    public void saveSemester() {
        // verificar que la fecha de inicio sea menor a la fecha de fin
        if (startDate.isAfter(endDate)) {
            facesContextWrapper.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_ERROR, "La fecha de inicio debe ser menor a la fecha de fin", null));
            primeFacesWrapper.current().ajax().update("form:growl");
            return;
        }
        String message = "";
        currentSemester.setSemesterName(semesterName);
        currentSemester.setStartDate(startDate);
        currentSemester.setEndDate(endDate);
        if (currentSemester.getPeriodId() == null) {
            semesterService.addSemester(currentSemester);
            message = "Semestre creado de manera exitosa";
        }else{
            if(semesterService.updateSemester(currentSemester) != null){
                message = "Semestre Actualizado con éxito";
            }else{
                message = "Error al Actualizar";
            }
        }
        facesContextWrapper.getCurrentInstance().addMessage(null,
                new FacesMessage(FacesMessage.SEVERITY_INFO, message, null));
        primeFacesWrapper.current().ajax().update("form:growl");
    }

}
