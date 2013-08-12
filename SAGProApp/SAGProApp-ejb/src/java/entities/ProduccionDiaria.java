/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package entities;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
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
    @NamedQuery(name = "ProduccionDiaria.findByFechaDiariaEstadistica", query = "SELECT p FROM ProduccionDiaria p WHERE p.produccionDiariaPK.fechaDiariaEstadistica = :fechaDiariaEstadistica"),
    @NamedQuery(name = "ProduccionDiaria.findByCodMaterial", query = "SELECT p FROM ProduccionDiaria p WHERE p.produccionDiariaPK.codMaterial = :codMaterial"),
    @NamedQuery(name = "ProduccionDiaria.findByProduccionMaterial", query = "SELECT p FROM ProduccionDiaria p WHERE p.produccionMaterial = :produccionMaterial")})
public class ProduccionDiaria implements Serializable {
    private static final long serialVersionUID = 1L;
    @EmbeddedId
    protected ProduccionDiariaPK produccionDiariaPK;
    @Basic(optional = false)
    @NotNull
    @Column(name = "PRODUCCION_MATERIAL")
    private int produccionMaterial;
    @JoinColumn(name = "COD_MATERIAL", referencedColumnName = "COD_MATERIAL", insertable = false, updatable = false)
    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    private Material material;

    public ProduccionDiaria() {
    }

    public ProduccionDiaria(ProduccionDiariaPK produccionDiariaPK) {
        this.produccionDiariaPK = produccionDiariaPK;
    }

    public ProduccionDiaria(ProduccionDiariaPK produccionDiariaPK, int produccionMaterial) {
        this.produccionDiariaPK = produccionDiariaPK;
        this.produccionMaterial = produccionMaterial;
    }

    public ProduccionDiaria(Date fechaDiariaEstadistica, int codMaterial) {
        this.produccionDiariaPK = new ProduccionDiariaPK(fechaDiariaEstadistica, codMaterial);
    }

    public ProduccionDiariaPK getProduccionDiariaPK() {
        return produccionDiariaPK;
    }

    public void setProduccionDiariaPK(ProduccionDiariaPK produccionDiariaPK) {
        this.produccionDiariaPK = produccionDiariaPK;
    }

    public int getProduccionMaterial() {
        return produccionMaterial;
    }

    public void setProduccionMaterial(int produccionMaterial) {
        this.produccionMaterial = produccionMaterial;
    }

    public Material getMaterial() {
        return material;
    }

    public void setMaterial(Material material) {
        this.material = material;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (produccionDiariaPK != null ? produccionDiariaPK.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof ProduccionDiaria)) {
            return false;
        }
        ProduccionDiaria other = (ProduccionDiaria) object;
        if ((this.produccionDiariaPK == null && other.produccionDiariaPK != null) || (this.produccionDiariaPK != null && !this.produccionDiariaPK.equals(other.produccionDiariaPK))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entities.ProduccionDiaria[ produccionDiariaPK=" + produccionDiariaPK + " ]";
    }
    
}
