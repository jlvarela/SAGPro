/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package managedbeans;

import javax.inject.Named;
import javax.enterprise.context.RequestScoped;

/**
 *
 * @author Jose
 */
@Named(value = "objetivoManagedBean")
@RequestScoped
public class ObjetivoManagedBean {

    /**
     * Creates a new instance of ObjetivoManagedBean
     */
    
    private String nombreObjetivo;

    public String getNombreObjetivo() {
        return nombreObjetivo;
    }

    public void setNombreObjetivo(String nombreObjetivo) {
        this.nombreObjetivo = nombreObjetivo;
    }
    
    
    
    public ObjetivoManagedBean() {
    }
}
