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
public class Objetivo implements Serializable{
    
    private String nombre;

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }   
}
