/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package pojoclass;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import pojoclass.Material;
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
    private List<Material> materialList;

    public List<Material> getMaterialList() {
        return materialList;
    }

    public void setMaterialList(List<Material> materialList) {
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
}
