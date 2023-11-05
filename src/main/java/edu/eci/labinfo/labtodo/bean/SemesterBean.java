package edu.eci.labinfo.labtodo.bean;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ApplicationScoped;
import javax.faces.context.FacesContextWrapper;
import javax.faces.model.SelectItem;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import edu.eci.labinfo.labtodo.model.LabToDoExeption;
import edu.eci.labinfo.labtodo.model.Semester;
import edu.eci.labinfo.labtodo.service.PrimeFacesWrapper;
import edu.eci.labinfo.labtodo.service.SemesterService;
import lombok.Getter;
import lombok.Setter;

@Component
@ManagedBean(name = "semesterBean")
@ApplicationScoped
@Getter
@Setter
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

    public void openNewYear() {
        selectedUsers.clear();
        this.currentSemester = new Semester();
    }

    public void saveSemester() {
        // verificar que la fecha de inicio sea menor a la fecha de fin
        if (startDate.isAfter(endDate)) {
            facesContextWrapper.getCurrentInstance().addMessage(null,
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
        facesContextWrapper.getCurrentInstance().addMessage(null,
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
        List<SelectItem> semesterItems = new ArrayList<SelectItem>();
        for (Semester semester : semesterService.getAllSemesters()) {
            semesterItems.add(new SelectItem(semester.getSemesterName()));
        }
        return semesterItems;
    }

}
