/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package pojoclass;

import java.io.Serializable;
import java.util.Date;

/**
 *
 * @author Jose
 */
public class ProduccionDiaria implements Serializable{
    private Date fecha;
    private Integer codMaterial;
    private Integer cantidad;
    private String nombreMaterial;
    private String nombreUnidadMaterial;

    public String getNombreUnidadMaterial() {
        return nombreUnidadMaterial;
    }

    public void setNombreUnidadMaterial(String nombreUnidadMaterial) {
        this.nombreUnidadMaterial = nombreUnidadMaterial;
    }

    public String getNombreMaterial() {
        return nombreMaterial;
    }

    public void setNombreMaterial(String nombreMaterial) {
        this.nombreMaterial = nombreMaterial;
    }   
    
    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    public Integer getCodMaterial() {
        return codMaterial;
    }

    public void setCodMaterial(Integer codMaterial) {
        this.codMaterial = codMaterial;
    }

    public Integer getCantidad() {
        return cantidad;
    }

    public void setCantidad(Integer cantidad) {
        this.cantidad = cantidad;
    }

    @Override
    public boolean equals(Object o) {
        Date otra_fecha = (Date) o;
        return this.fecha.compareTo(otra_fecha) == 0;
    }
    
    public boolean equals(Date otra_fecha){
        return this.fecha.compareTo(otra_fecha) == 0;
    }
    
    
}
