package edu.eci.labinfo.labtodo.controller;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;

import edu.eci.labinfo.labtodo.model.LabToDoExeption;
import edu.eci.labinfo.labtodo.model.Semester;
import edu.eci.labinfo.labtodo.service.PrimeFacesWrapper;
import edu.eci.labinfo.labtodo.service.SemesterService;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.faces.application.FacesMessage;
import jakarta.faces.context.FacesContext;
import jakarta.faces.model.SelectItem;
import lombok.Getter;
import lombok.Setter;

@Component
@ApplicationScoped
@Getter
@Setter
public class SemesterController {

    private List<Semester> semesters;
    private String semesterName;
    private LocalDate startDate;
    private Semester currentSemester;
    private LocalDate endDate;
    private List<String> selectedUsers = new ArrayList<>();

    private final SemesterService semesterService;
    private final PrimeFacesWrapper primeFacesWrapper;

    public SemesterController(SemesterService semesterService, PrimeFacesWrapper primeFacesWrapper) {
        this.semesterService = semesterService;
        this.primeFacesWrapper = primeFacesWrapper;
    }


    public void openNewYear() {
        selectedUsers.clear();
        this.currentSemester = new Semester();
    }

    public void saveSemester() {
        // verificar que la fecha de inicio sea menor a la fecha de fin
        if (startDate.isAfter(endDate)) {
            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_ERROR, LabToDoExeption.INVALID_DATE, null));
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
        semesterName = null;
        startDate = null;
        endDate = null;
        FacesContext.getCurrentInstance().addMessage(null,
                new FacesMessage(FacesMessage.SEVERITY_INFO, message, null));
        primeFacesWrapper.current().ajax().update("form:growl");
    }

    /**
     * Metodo que verifica si hay un semestre activo
     * @return True si hay un semestre activo, de lo contrario False
     */
    public Boolean isThereASemester() {
        return semesterService.getCurrentSemester() != null;
    }

    public List<SelectItem> getSemestersLikeItems() {
        List<SelectItem> semesterItems = new ArrayList<>();
        for (Semester semester : semesterService.getAllSemesters()) {
            semesterItems.add(new SelectItem(semester.getSemesterName()));
        }
        return semesterItems;
    }

}
