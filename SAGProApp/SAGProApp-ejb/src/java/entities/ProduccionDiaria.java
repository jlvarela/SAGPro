/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package entities;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Jose
 */
@Entity
@Table(name = "produccion_diaria")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "ProduccionDiaria.findAll", query = "SELECT p FROM ProduccionDiaria p"),
    @NamedQuery(name = "ProduccionDiaria.findByFechaDiariaEstadistica", query = "SELECT p FROM ProduccionDiaria p WHERE p.fechaDiariaEstadistica = :fechaDiariaEstadistica"),
    @NamedQuery(name = "ProduccionDiaria.findByProduccionMaterial", query = "SELECT p FROM ProduccionDiaria p WHERE p.produccionMaterial = :produccionMaterial")})
public class ProduccionDiaria implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "FECHA_DIARIA_ESTADISTICA")
    @Temporal(TemporalType.TIME)
    private Date fechaDiariaEstadistica;
    @Basic(optional = false)
    @NotNull
    @Column(name = "PRODUCCION_MATERIAL")
    private int produccionMaterial;
    @JoinColumn(name = "COD_MATERIAL", referencedColumnName = "COD_MATERIAL")
    @ManyToOne(optional = false)
    private Material codMaterial;

    public ProduccionDiaria() {
    }

    public ProduccionDiaria(Date fechaDiariaEstadistica) {
        this.fechaDiariaEstadistica = fechaDiariaEstadistica;
    }

    public ProduccionDiaria(Date fechaDiariaEstadistica, int produccionMaterial) {
        this.fechaDiariaEstadistica = fechaDiariaEstadistica;
        this.produccionMaterial = produccionMaterial;
    }

    public Date getFechaDiariaEstadistica() {
        return fechaDiariaEstadistica;
    }

    public void setFechaDiariaEstadistica(Date fechaDiariaEstadistica) {
        this.fechaDiariaEstadistica = fechaDiariaEstadistica;
    }

    public int getProduccionMaterial() {
        return produccionMaterial;
    }

    public void setProduccionMaterial(int produccionMaterial) {
        this.produccionMaterial = produccionMaterial;
    }

    public Material getCodMaterial() {
        return codMaterial;
    }

    public void setCodMaterial(Material codMaterial) {
        this.codMaterial = codMaterial;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (fechaDiariaEstadistica != null ? fechaDiariaEstadistica.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof ProduccionDiaria)) {
            return false;
        }
        ProduccionDiaria other = (ProduccionDiaria) object;
        if ((this.fechaDiariaEstadistica == null && other.fechaDiariaEstadistica != null) || (this.fechaDiariaEstadistica != null && !this.fechaDiariaEstadistica.equals(other.fechaDiariaEstadistica))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entities.ProduccionDiaria[ fechaDiariaEstadistica=" + fechaDiariaEstadistica + " ]";
    }
    
}
