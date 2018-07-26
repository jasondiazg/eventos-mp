package mp.eventos.model;

import javax.persistence.Entity;
import java.io.Serializable;
import java.util.Date;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Id;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Column;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Version;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author JasonDiazG
 */

@Entity
@XmlRootElement
public class Evento implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", updatable = false, nullable = false)
    private Long id;

    @Version
    @Column(name = "version")
    private int version;

    @Column
    private String nombre;

    @Temporal(TemporalType.TIMESTAMP)
    protected Date fechaEvento;

    @Column
    private String lugar;

    @Column
    private boolean proximoAEjecutarse;

    @OneToMany(mappedBy = "idEvento", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    protected List<PonenteEvento> ponentes;

    @OneToMany(mappedBy = "idEvento", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    protected List<AsistenteEvento> asistentes;

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

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public Date getFechaEvento() {
        return fechaEvento;
    }

    public void setFechaEvento(Date fechaEvento) {
        this.fechaEvento = fechaEvento;
    }

    public String getLugar() {
        return lugar;
    }

    public void setLugar(String lugar) {
        this.lugar = lugar;
    }

    public List<PonenteEvento> getPonentes() {
        return ponentes;
    }

    public void setPonentes(List<PonenteEvento> ponentes) {
        this.ponentes = ponentes;
    }

    public List<AsistenteEvento> getAsistentes() {
        return asistentes;
    }

    public void setAsistentes(List<AsistenteEvento> asistentes) {
        this.asistentes = asistentes;
    }

    public boolean isProximoAEjecutarse() {
        return proximoAEjecutarse;
    }

    public void setProximoAEjecutarse(boolean proximoAEjecutarse) {
        this.proximoAEjecutarse = proximoAEjecutarse;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof Evento)) {
            return false;
        }
        Evento other = (Evento) obj;
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
        return "Evento{" + "id=" + id + ", version=" + version + ", nombre=" + nombre + ", fechaEvento=" + fechaEvento + ", lugar=" + lugar + ", proximoAEjecutarse=" + proximoAEjecutarse + '}';
    }
}
