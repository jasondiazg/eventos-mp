package mp.eventos.model;

import javax.persistence.Entity;
import java.io.Serializable;
import javax.persistence.Id;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Column;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Version;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author JasonDiazG
 */
@Entity
@XmlRootElement
public class AsistenteEvento implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", updatable = false, nullable = false)
    private Long id;

    @Version
    @Column(name = "version")
    private int version;

    @Column(name = "idAsistente")
    @JoinColumn(
            name = "idAsistente",
            referencedColumnName = "id",
            nullable = false)
    @ManyToOne(optional = false, fetch = FetchType.EAGER)
    private Asistente idAsistente;

    @Column(name = "idEvento")
    @JoinColumn(
            name = "idEvento",
            referencedColumnName = "id",
            nullable = false)
    @ManyToOne(optional = false, fetch = FetchType.EAGER)
    private Evento idEvento;

    public Long getId() {
        return this.id;
    }

    public void setId(final Long id) {
        this.id = id;
    }

    public int getVersion() {
        return this.version;
    }

    public void setVersion(final int version) {
        this.version = version;
    }

    public Asistente getIdAsistente() {
        return idAsistente;
    }

    public void setIdAsistente(Asistente idAsistente) {
        this.idAsistente = idAsistente;
    }

    public Evento getIdEvento() {
        return idEvento;
    }

    public void setIdEvento(Evento idEvento) {
        this.idEvento = idEvento;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof AsistenteEvento)) {
            return false;
        }
        AsistenteEvento other = (AsistenteEvento) obj;
        if (id != null) {
            if (!id.equals(other.id)) {
                return false;
            }
        }
        return true;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((id == null) ? 0 : id.hashCode());
        return result;
    }

    @Override
    public String toString() {
        return "AsistenteEvento{" + "id=" + id + ", version=" + version + ", idAsistente=" + idAsistente + ", idEvento=" + idEvento + '}';
    }
}
