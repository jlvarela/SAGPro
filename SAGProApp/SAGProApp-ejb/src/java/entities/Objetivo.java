/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package entities;

import java.io.Serializable;
import java.util.Collection;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.ManyToMany;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author Jose
 */
@Entity
@Table(name = "objetivo")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Objetivo.findAll", query = "SELECT o FROM Objetivo o"),
    @NamedQuery(name = "Objetivo.findByCodObjetivo", query = "SELECT o FROM Objetivo o WHERE o.codObjetivo = :codObjetivo"),
    @NamedQuery(name = "Objetivo.findByNombreObjetivo", query = "SELECT o FROM Objetivo o WHERE o.nombreObjetivo = :nombreObjetivo"),
    @NamedQuery(name = "Objetivo.findByPrioridadObjetivo", query = "SELECT o FROM Objetivo o WHERE o.prioridadObjetivo = :prioridadObjetivo"),
    @NamedQuery(name = "Objetivo.findByFechaInicial", query = "SELECT o FROM Objetivo o WHERE o.fechaInicial = :fechaInicial"),
    @NamedQuery(name = "Objetivo.findByFechaLimite", query = "SELECT o FROM Objetivo o WHERE o.fechaLimite = :fechaLimite")})
public class Objetivo implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "COD_OBJETIVO")
    private Integer codObjetivo;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 50)
    @Column(name = "NOMBRE_OBJETIVO")
    private String nombreObjetivo;
    @Column(name = "PRIORIDAD_OBJETIVO")
    private Short prioridadObjetivo;
    @Lob
    @Size(max = 65535)
    @Column(name = "DESCRIPCION_OBJETIVO")
    private String descripcionObjetivo;
    @Basic(optional = false)
    @NotNull
    @Column(name = "FECHA_INICIAL")
    @Temporal(TemporalType.TIME)
    private Date fechaInicial;
    @Basic(optional = false)
    @NotNull
    @Column(name = "FECHA_LIMITE")
    @Temporal(TemporalType.TIME)
    private Date fechaLimite;
    @ManyToMany(mappedBy = "objetivoCollection")
    private Collection<Material> materialCollection;

    public Objetivo() {
    }

    public Objetivo(Integer codObjetivo) {
        this.codObjetivo = codObjetivo;
    }

    public Objetivo(Integer codObjetivo, String nombreObjetivo, Date fechaInicial, Date fechaLimite) {
        this.codObjetivo = codObjetivo;
        this.nombreObjetivo = nombreObjetivo;
        this.fechaInicial = fechaInicial;
        this.fechaLimite = fechaLimite;
    }

    public Integer getCodObjetivo() {
        return codObjetivo;
    }

    public void setCodObjetivo(Integer codObjetivo) {
        this.codObjetivo = codObjetivo;
    }

    public String getNombreObjetivo() {
        return nombreObjetivo;
    }

    public void setNombreObjetivo(String nombreObjetivo) {
        this.nombreObjetivo = nombreObjetivo;
    }

    public Short getPrioridadObjetivo() {
        return prioridadObjetivo;
    }

    public void setPrioridadObjetivo(Short prioridadObjetivo) {
        this.prioridadObjetivo = prioridadObjetivo;
    }

    public String getDescripcionObjetivo() {
        return descripcionObjetivo;
    }

    public void setDescripcionObjetivo(String descripcionObjetivo) {
        this.descripcionObjetivo = descripcionObjetivo;
    }

    public Date getFechaInicial() {
        return fechaInicial;
    }

    public void setFechaInicial(Date fechaInicial) {
        this.fechaInicial = fechaInicial;
    }

    public Date getFechaLimite() {
        return fechaLimite;
    }

    public void setFechaLimite(Date fechaLimite) {
        this.fechaLimite = fechaLimite;
    }

    @XmlTransient
    public Collection<Material> getMaterialCollection() {
        return materialCollection;
    }

    public void setMaterialCollection(Collection<Material> materialCollection) {
        this.materialCollection = materialCollection;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (codObjetivo != null ? codObjetivo.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Objetivo)) {
            return false;
        }
        Objetivo other = (Objetivo) object;
        if ((this.codObjetivo == null && other.codObjetivo != null) || (this.codObjetivo != null && !this.codObjetivo.equals(other.codObjetivo))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entities.Objetivo[ codObjetivo=" + codObjetivo + " ]";
    }
    
}