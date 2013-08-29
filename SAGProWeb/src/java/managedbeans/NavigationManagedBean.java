/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package managedbeans;

import java.io.IOException;
import javax.faces.bean.ApplicationScoped;
import javax.faces.bean.ManagedBean;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;

/**
 *
 * @author Jose
 */
@ManagedBean(name = "navigationManagedBean")
@ApplicationScoped
public class NavigationManagedBean {

    /**
     * Creates a new instance of NavigationManagedBean
     */
    public NavigationManagedBean() {
    }
    
    /**
     * Redirige al usuario a la página web page.
     * Esta debe ser ingresada
     * @param webpage 
     */
    private void goToPage(String webpage){
        ExternalContext extcon = FacesContext.getCurrentInstance().getExternalContext();
        
        try{
            extcon.redirect(extcon.getRequestContextPath() + webpage);
        }
        catch(IOException ex){
            System.out.println("No se ha podido redirigir a la página ".concat(webpage));            
        }
    }
}
