package edu.eci.labinfo.labtodo.model;

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
