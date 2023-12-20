package edu.eci.labinfo.labtodo.model;

/**
 * Enumeración que representa los temas de una tarea.
 * Cada estado tiene un valor asociado que se utiliza para identificarlo.
 */
public enum TopicTask {

    MANTENIMIENTO("Mantenimiento"),
    DESARROLLODESOFTWARE("Desarrollo de Software"),
    ESTADISTICAS("Estadisticas"),
    ENCUESTAS("Encuestas"),
    DESARROLLOWEB("Desarrollo Web"),
    REDES("Redes");

    private String value;

    TopicTask(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

    /**
     * Busca un tipo de tarea por su tema, insensible a mayúsculas y minúsculas.
     *
     * @param type El valor de la tarea a buscar.
     * @return El tipo de tarea correspondiente al valor proporcionado, o null si no
     *         se encuentra.
     */
    public static TopicTask findByValue(String topicTask) {
        TopicTask response = null;
        for (TopicTask t : TopicTask.values()) {
            if (t.getValue().equalsIgnoreCase(topicTask)) {
                response = t;
                break;
            }
        }
        return response;
    }
    
}
