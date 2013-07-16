/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package entities;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;

/**
 *
 * @author Jose
 */
@Embeddable
public class ProduccionDiariaPK implements Serializable {
    @Basic(optional = false)
    @NotNull
    @Column(name = "FECHA_DIARIA_ESTADISTICA")
    @Temporal(TemporalType.DATE)
    private Date fechaDiariaEstadistica;
    @Basic(optional = false)
    @NotNull
    @Column(name = "COD_MATERIAL")
    private int codMaterial;

    public ProduccionDiariaPK() {
    }

    public ProduccionDiariaPK(Date fechaDiariaEstadistica, int codMaterial) {
        this.fechaDiariaEstadistica = fechaDiariaEstadistica;
        this.codMaterial = codMaterial;
    }

    public Date getFechaDiariaEstadistica() {
        return fechaDiariaEstadistica;
    }

    public void setFechaDiariaEstadistica(Date fechaDiariaEstadistica) {
        this.fechaDiariaEstadistica = fechaDiariaEstadistica;
    }

    public int getCodMaterial() {
        return codMaterial;
    }

    public void setCodMaterial(int codMaterial) {
        this.codMaterial = codMaterial;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (fechaDiariaEstadistica != null ? fechaDiariaEstadistica.hashCode() : 0);
        hash += (int) codMaterial;
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof ProduccionDiariaPK)) {
            return false;
        }
        ProduccionDiariaPK other = (ProduccionDiariaPK) object;
        if ((this.fechaDiariaEstadistica == null && other.fechaDiariaEstadistica != null) || (this.fechaDiariaEstadistica != null && !this.fechaDiariaEstadistica.equals(other.fechaDiariaEstadistica))) {
            return false;
        }
        if (this.codMaterial != other.codMaterial) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entities.ProduccionDiariaPK[ fechaDiariaEstadistica=" + fechaDiariaEstadistica + ", codMaterial=" + codMaterial + " ]";
    }
    
}
