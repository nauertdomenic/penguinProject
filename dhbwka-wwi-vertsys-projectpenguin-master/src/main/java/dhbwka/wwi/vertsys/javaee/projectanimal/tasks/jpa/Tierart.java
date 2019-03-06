package dhbwka.wwi.vertsys.javaee.projectanimal.tasks.jpa;

import dhbwka.wwi.vertsys.javaee.projectanimal.common.jpa.User;
import java.io.Serializable;
import java.sql.Date;
import java.sql.Time;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.TableGenerator;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 * Eine zu entdeckte Tierart.
 */
@Entity
@Table(name = "Tierart")
public class Tierart implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.TABLE, generator = "tierart_ids")
    @TableGenerator(name = "tierart_ids", initialValue = 0, allocationSize = 50)
    private long id;

    @ManyToOne
    @NotNull(message = "Die Tierart muss einem Benutzer geordnet werden.")
    private User owner;

    @ManyToOne
    private Category category;

    @Column(length = 50)
    @NotNull(message = "Der Tierartname darf nicht leer sein.")
    @Size(min = 1, max = 50, message = "Der Tierartname muss zwischen ein und 50 Zeichen lang sein.")
    private String tierartname;

    //<editor-fold defaultstate="collapsed" desc="Konstruktoren">
    public Tierart() {
    }

    public Tierart(User owner, Category category, String shortText, String longText, Date dueDate, Time dueTime) {
        this.owner = owner;
        this.category = category;
        this.tierartname = shortText;
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

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public String getTierartname() {
        return tierartname;
    }

    public void setTierartname(String tierartname) {
        this.tierartname = tierartname;
    }
     //</editor-fold>

}
