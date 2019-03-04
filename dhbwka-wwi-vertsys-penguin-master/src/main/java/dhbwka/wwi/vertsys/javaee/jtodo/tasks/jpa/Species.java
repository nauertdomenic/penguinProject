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

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.TableGenerator;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 * Kategorien, die den Aufgaben zugeordnet werden können.
 */
@Entity
public class Species implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.TABLE, generator = "species_ids")
    @TableGenerator(name = "species_ids", initialValue = 0, allocationSize = 50)
    private long id;

    @Column(length = 50)
    @NotNull(message = "Die Species darf nicht leer sein.")
    @Size(min = 3, max = 50, message = "Die Species muss zwischen 3 und 50 Zeichen lang sein.")
    private String species;

    @OneToMany(mappedBy = "species", fetch = FetchType.LAZY)
    List<Animal> animals = new ArrayList<>();

    //<editor-fold defaultstate="collapsed" desc="Konstruktoren">
    public Species() {
    }

    public Species(String name) {
        this.species = name;
    }
    //</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="Setter und Getter">
    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return species;
    }

    public void setName(String name) {
        this.species = name;
    }

    public List<Animal> getAnimals() {
        return animals;
    }

    public void setAnimals(List<Animal> animals) {
        this.animals = animals;
    }
    //</editor-fold>

}
