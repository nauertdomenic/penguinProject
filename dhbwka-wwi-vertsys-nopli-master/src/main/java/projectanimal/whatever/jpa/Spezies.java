package projectanimal.whatever.jpa;

import java.io.Serializable;
import java.util.*;
import javax.persistence.*;
import javax.validation.constraints.*;

/**
 *
 * @author simon
 */
@Entity
@Table(name = "Spezies")
public class Spezies implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.TABLE, generator = "spezies_ids")
    @TableGenerator(name = "spezies_ids", initialValue = 0, allocationSize = 50)
    private long id;

    @Column(length = 30)
    @NotNull(message = "Der Name darf nicht leer sein.")
    @Size(min = 3, max = 30, message = "Der Name muss zwischen drei und 30 Zeichen lang sein.")
    private String name;

    @OneToMany(mappedBy = "spezies", fetch = FetchType.LAZY)
    List<Tierart> tierarten = new ArrayList<>();

    //<editor-fold defaultstate="collapsed" desc="Konstruktoren">
    public Spezies() {
    }

    public Spezies(String name) {
        this.name = name;
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
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Tierart> getTierarten() {
        return tierarten;
    }

    public void setTierarten(List<Tierart> tierarten) {
        this.tierarten = tierarten;
    }
    //</editor-fold>

}
