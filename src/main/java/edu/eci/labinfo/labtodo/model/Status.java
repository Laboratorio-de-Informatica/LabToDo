package edu.eci.labinfo.labtodo.model;

/**
 * Enumeración que representa los estados de una tarea.
 * Cada estado tiene un valor asociado que se utiliza para identificarlo.
 */
public enum Status {

    INPROCESS("En Proceso"),
    FINISH("Finalizada"),
    PENDING("Pendiente"),
    REVIEW("Revisión");

    private String value;

    Status(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

    /**
     * Busca un estado de tarea por su valor, insensible a mayúsculas y minúsculas.
     *
     * @param state El estado de la tarea a buscar.
     * @return El estado de tarea correspondiente al valor proporcionado, o null si
     *         no
     *         se encuentra.
     */
    public static Status findByValue(String state) {
        Status response = null;
        for (Status s : Status.values()) {
            if (s.getValue().equalsIgnoreCase(state)) {
                response = s;
                break;
            }
        }
        return response;
    }
}
