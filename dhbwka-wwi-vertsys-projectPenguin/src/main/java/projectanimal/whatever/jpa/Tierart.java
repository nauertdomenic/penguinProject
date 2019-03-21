package projectanimal.whatever.jpa;

import dhbwka.wwi.vertsys.javaee.projectanimal.common.jpa.User;
import java.io.Serializable;
import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 *
 * @author simon
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
    private Spezies spezies;

    @Column(length = 50)
    @NotNull(message = "Der Tierartname darf nicht leer sein.")
    @Size(min = 1, max = 50, message = "Der Tierartname muss zwischen ein und 50 Zeichen lang sein.")
    private String tierartname;
    
    @Enumerated(EnumType.STRING)
    @NotNull
    private TierartStatus status = TierartStatus.ENTDECKT;

    //<editor-fold defaultstate="collapsed" desc="Konstruktoren">
    public Tierart() {
    }

    public Tierart(User owner, Spezies spezies, String tierartname) {
        this.owner = owner;
        this.spezies = spezies;
        this.tierartname = tierartname;
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

    public Spezies getSpezies() {
        return spezies;
    }

    public void setSpezies(Spezies spezies) {
        this.spezies = spezies;
    }

    public String getTierartname() {
        return tierartname;
    }

    public void setTierartname(String tierartname) {
        this.tierartname = tierartname;
    }
    
    public TierartStatus getStatus() {
        return status;
    }

    public void setStatus(TierartStatus status) {
        this.status = status;
    }
    
    //</editor-fold>

    

}
