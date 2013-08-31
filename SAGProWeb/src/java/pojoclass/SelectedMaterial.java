/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package pojoclass;

import java.io.Serializable;

/**
 *
 * @author Jose
 */
public class SelectedMaterial implements Serializable{
    private Integer codMaterial;
    private int cantidad;
    private String nombreMaterial;

    public SelectedMaterial () {
    }

    public String getNombreMaterial() {
        return nombreMaterial;
    }

    public void setNombreMaterial(String nombreMaterial) {
        this.nombreMaterial = nombreMaterial;
    }

    public Integer getCodMaterial() {
        return codMaterial;
    }

    public void setCodMaterial(Integer idMaterial) {
        this.codMaterial = idMaterial;
    }

    public Integer getCantidad() {
        return cantidad;
    }

    public void setCantidad(Integer cantidad) {
        this.cantidad = cantidad;
    }

    @Override
    public int hashCode() {
        int hash = 7;
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
        final SelectedMaterial other = (SelectedMaterial) obj;
        if (this.codMaterial != other.codMaterial) {
            return false;
        }
        return true;
    }

}
