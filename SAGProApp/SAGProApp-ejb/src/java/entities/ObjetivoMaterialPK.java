/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package entities;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.validation.constraints.NotNull;

/**
 *
 * @author Jose
 */
@Embeddable
public class ObjetivoMaterialPK implements Serializable {
    @Basic(optional = false)
    @NotNull
    @Column(name = "COD_OBJETIVO")
    private int codObjetivo;
    @Basic(optional = false)
    @NotNull
    @Column(name = "COD_MATERIAL")
    private int codMaterial;

    public ObjetivoMaterialPK() {
    }

    public ObjetivoMaterialPK(int codObjetivo, int codMaterial) {
        this.codObjetivo = codObjetivo;
        this.codMaterial = codMaterial;
    }

    public int getCodObjetivo() {
        return codObjetivo;
    }

    public void setCodObjetivo(int codObjetivo) {
        this.codObjetivo = codObjetivo;
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
        hash += (int) codObjetivo;
        hash += (int) codMaterial;
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof ObjetivoMaterialPK)) {
            return false;
        }
        ObjetivoMaterialPK other = (ObjetivoMaterialPK) object;
        if (this.codObjetivo != other.codObjetivo) {
            return false;
        }
        if (this.codMaterial != other.codMaterial) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entities.ObjetivoMaterialPK[ codObjetivo=" + codObjetivo + ", codMaterial=" + codMaterial + " ]";
    }
    
}
