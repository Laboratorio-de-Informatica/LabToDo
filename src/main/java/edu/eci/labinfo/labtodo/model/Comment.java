package edu.eci.labinfo.labtodo.model;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.util.Locale;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * Esta es una entidad que representa a un comentario en la base de datos.
 */
@Entity
@Getter
@Setter
@EqualsAndHashCode
@ToString
public class Comment {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long commentId;
    private String description;
    @Column(name = "creationDate")
    private LocalDate creationDate;

    @ManyToOne
    @JoinColumn(name = "task_id", nullable = false)
    private Task task;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User creatorUser;

    public Comment() {
        this.creationDate = LocalDate.now();
    }

    public Comment(Task task, String description) {
        this.description = description;
        this.creationDate = LocalDate.now();
        this.task = task;
    }

    public String getDateText() {
        DateTimeFormatter formatter = DateTimeFormatter.ofLocalizedDate(FormatStyle.FULL)
                .withLocale(new Locale("es", "ES"));
        return creationDate.format(formatter);
    }

}
