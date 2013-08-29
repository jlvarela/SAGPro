/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package pojoclass;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import java.util.Objects;
/**
 *
 * @author Jose
 */
public class Objetivo implements Serializable{
    
    private Integer codObjetivo;
    private String nombre;
    private Short prioridad;
    private String descripcion;
    private Date fechaInicial;
    private Date fechaLimite;
    private List<SelectedMaterial> materialList;
    
    

    public List<SelectedMaterial> getMaterialList() {
        return materialList;
    }

    public void setMaterialList(List<SelectedMaterial> materialList) {
        this.materialList = materialList;
    }

    

    public Integer getCodObjetivo() {
        return codObjetivo;
    }

    public void setCodObjetivo(Integer codObjetivo) {
        this.codObjetivo = codObjetivo;
    }

    public Short getPrioridad() {
        return prioridad;
    }

    public void setPrioridad(Short prioridad) {
        this.prioridad = prioridad;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
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
    
    
    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }   

    public void removeMaterial(SelectedMaterial item) {
        this.materialList.remove(item);
    }
    
    public SelectedMaterial isMaterialInObjetivo(SelectedMaterial sm){
        int index;
        index = materialList.indexOf(sm);
        if ( index > 0 )
            return materialList.get(index);
        return null;
    }
    
    public void addMaterial(SelectedMaterial sm){
        materialList.add(sm);
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 17 * hash + Objects.hashCode(this.codObjetivo);
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
        final Objetivo other = (Objetivo) obj;
        if (this.codObjetivo == other.codObjetivo) {
            return false;
        }
        return true;
    }
    
}
