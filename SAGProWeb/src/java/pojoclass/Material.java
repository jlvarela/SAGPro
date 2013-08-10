/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package pojoclass;

import java.io.Serializable;
import java.util.Objects;

/**
 *
 * @author Jose
 */
public class Material implements Serializable {
    
    private Integer codMaterial;
    private String nombreMaterial;
    private String medidaProduccionMaterial;
    private String medidaVentaMaterial;

    public Integer getCodMaterial() {
        return codMaterial;
    }

    public void setCodMaterial(Integer codMaterial) {
        this.codMaterial = codMaterial;
    }

    public String getNombreMaterial() {
        return nombreMaterial;
    }

    public void setNombreMaterial(String nombreMaterial) {
        this.nombreMaterial = nombreMaterial;
    }

    public String getMedidaProduccionMaterial() {
        return medidaProduccionMaterial;
    }

    public void setMedidaProduccionMaterial(String medidaProduccionMaterial) {
        this.medidaProduccionMaterial = medidaProduccionMaterial;
    }

    public String getMedidaVentaMaterial() {
        return medidaVentaMaterial;
    }

    public void setMedidaVentaMaterial(String medidaVentaMaterial) {
        this.medidaVentaMaterial = medidaVentaMaterial;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 41 * hash + Objects.hashCode(this.codMaterial);
        hash = 41 * hash + Objects.hashCode(this.nombreMaterial);
        hash = 41 * hash + Objects.hashCode(this.medidaProduccionMaterial);
        hash = 41 * hash + Objects.hashCode(this.medidaVentaMaterial);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Material other = (Material) obj;
        if (!Objects.equals(this.codMaterial, other.codMaterial)) {
            return false;
        }
        if (!Objects.equals(this.nombreMaterial, other.nombreMaterial)) {
            return false;
        }
        if (!Objects.equals(this.medidaProduccionMaterial, other.medidaProduccionMaterial)) {
            return false;
        }
        if (!Objects.equals(this.medidaVentaMaterial, other.medidaVentaMaterial)) {
            return false;
        }
        return true;
    }

}
