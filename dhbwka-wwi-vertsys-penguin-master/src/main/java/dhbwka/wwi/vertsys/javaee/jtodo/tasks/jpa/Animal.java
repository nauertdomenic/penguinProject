/*
 * Copyright © 2018 Dennis Schulmeister-Zimolong
 * 
 * E-Mail: dhbw@windows3.de
 * Webseite: https://www.wpvs.de/
 * 
 * Dieser Quellcode ist lizenziert unter einer
 * Creative Commons Namensnennung 4.0 International Lizenz.
 */
package dhbwka.wwi.vertsys.javaee.jtodo.tasks.jpa;

import dhbwka.wwi.vertsys.javaee.jtodo.common.jpa.User;
import java.io.Serializable;
import java.sql.Date;
import java.sql.Time;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.TableGenerator;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 * Eine zu erledigende Aufgabe.
 */
@Entity
public class Animal implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.TABLE, generator = "animal_ids")
    @TableGenerator(name = "animal_ids", initialValue = 0, allocationSize = 50)
    private long id;

    @ManyToOne
    @NotNull(message = "Das Tier muss einem Benutzer geordnet werden.")
    private User owner;

    @ManyToOne
    private Species species;

    @Column(length = 50)
    @Size(min = 3, max = 50, message = "Die Tierart muss zwischen 3 und 50 Zeichen lang sein.")
    private String name;

    private long avgWeight;
    
    private long avgSize;

    @NotNull(message = "Das Datum darf nicht leer sein.")
    private Date dueDate;

    @NotNull(message = "Die Uhrzeit darf nicht leer sein.")
    private Time dueTime;

    //<editor-fold defaultstate="collapsed" desc="Konstruktoren">
    public Animal() {
    }

    public Animal(User owner, Species species, String name, long avgWeihgt, long avgSize, Date dueDate, Time dueTime) {
        this.owner = owner;
        this.species = species;
        this.name = name;
        this.avgWeight = avgWeihgt;
        this.avgSize = avgSize;
        this.dueDate = dueDate;
        this.dueTime = dueTime;
    }
    //</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="Setter und Getter">
    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public User getOwner() {
        return owner;
    }

    public void setOwner(User owner) {
        this.owner = owner;
    }

    public Species getSpecies() {
        return species;
    }

    public void setSpecies(Species species) {
        this.species = species;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public long getAvgWeight() {
        return avgWeight;
    }

    public void setAvgWeight(long avgWeight) {
        this.avgWeight = avgWeight;
    }
    
     public long getAvgSize() {
        return avgWeight;
    }

    public void setAvgSize(long avgSize) {
        this.avgSize = avgSize;
    }

    public Date getDueDate() {
        return dueDate;
    }

    public void setDueDate(Date dueDate) {
        this.dueDate = dueDate;
    }

    public Time getDueTime() {
        return dueTime;
    }

    public void setDueTime(Time dueTime) {
        this.dueTime = dueTime;
    }
    //</editor-fold>

}
