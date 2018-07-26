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
public class PonenteEvento implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", updatable = false, nullable = false)
    private Long id;

    @Version
    @Column(name = "version")
    private int version;

    @JoinColumn(
            name = "idPonente",
            referencedColumnName = "id",
            nullable = false)
    @ManyToOne(optional = false, fetch = FetchType.EAGER)
    private Ponente idPonente;

    @JoinColumn(
            name = "idEvento",
            referencedColumnName = "id",
            nullable = false)
    @ManyToOne(optional = false, fetch = FetchType.EAGER)
    private Evento idEvento;

    @Column
    private String ponencia;

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

    public Ponente getIdPonente() {
        return idPonente;
    }

    public void setIdPonente(Ponente idPonente) {
        this.idPonente = idPonente;
    }

    public Evento getIdEvento() {
        return idEvento;
    }

    public void setIdEvento(Evento idEvento) {
        this.idEvento = idEvento;
    }

    public String getPonencia() {
        return ponencia;
    }

    public void setPonencia(String ponencia) {
        this.ponencia = ponencia;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof PonenteEvento)) {
            return false;
        }
        PonenteEvento other = (PonenteEvento) obj;
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
        return "PonenteEvento{" + "id=" + id + ", version=" + version + ", idPonente=" + idPonente + ", ponencia=" + ponencia + ", idEvento=" + idEvento + '}';
    }
}
