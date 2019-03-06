package dhbwka.wwi.vertsys.javaee.projectanimal.tasks.jpa;

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
 * Spezies, die den Tierarten zugeordnet werden können.
 */
@Entity
public class Category implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.TABLE, generator = "category_ids")
    @TableGenerator(name = "category_ids", initialValue = 0, allocationSize = 50)
    private long id;

    @Column(length = 30)
    @NotNull(message = "Der Name darf nicht leer sein.")
    @Size(min = 3, max = 30, message = "Der Name muss zwischen drei und 30 Zeichen lang sein.")
    private String name;

    @OneToMany(mappedBy = "category", fetch = FetchType.LAZY)
    List<Tierart> tierarten = new ArrayList<>();

    //<editor-fold defaultstate="collapsed" desc="Konstruktoren">
    public Category() {
    }

    public Category(String name) {
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
