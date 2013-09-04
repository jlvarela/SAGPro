/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package managedbeans;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.faces.bean.ApplicationScoped;
import javax.faces.bean.ManagedBean;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;

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
    
    public void goToObjetivos(){
        goToConsultarObjetivos();
    }
    
    public void goToConsultarObjetivos(){
        goToPage("/faces/gerente/consultarObjetivo.xhtml");
    }
    
    public void goToCrearObjetivo(){
        goToPage("/faces/gerente/crearObjetivo.xhtml");
    }
    
    public void goToEvolucionProduccion(){
        goToPage("/faces/gerente/evolucionProduccion.xhtml");
    }
    
    public void goToEstadisticas(){
        goToPage("/faces/gerente/estadisticas.xhtml");
    }
        
    public void goToMaterial(){
        goToCrearMaterial();
    }
    
    public void goToCrearMaterial(){
        goToPage("/faces/admin/ingresarMaterial.xhtml");
    }
    
    public void goToConsultarMaterial(){
        goToPage("/faces/admin/consultarMaterial.xhtml");
    }
    
    public void goToUsuarios(){
        goToConsultarUsuario();
    }
    
    public void goToCrearUsuario(){
        goToPage("/faces/admin/ingresarUsuario.xhtml");
    }
    
    public void goToConsultarUsuario(){
        goToPage("/faces/admin/consultarUsuario.xhtml");
   }
    
    public void goToProduccion(){
        goToCrearProduccion();
    }
    
    public void goToCrearProduccion(){
        goToPage("/faces/calidad/ingresarProduccion.xhtml");
    }
    
    public void goToConsultarProduccion(){
        goToPage("/faces/calidad/consultarProduccion.xhtml");
    }
    
    public void goToPerfil(){
        goToPage("/faces/user/perfil.xhtml");
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
    
    public String getTemplate(){
        FacesContext context = FacesContext.getCurrentInstance();
        HttpServletRequest request = (HttpServletRequest) context.getExternalContext().getRequest();
        if (request.getUserPrincipal() != null) {
            if ( request.isUserInRole("Admin") ){
                return "templateAdmin";
            }
            else if ( request.isUserInRole("Calidad") ){
                return "templateGestorCalidad";
            }
            else if ( request.isUserInRole("Gerencia") ){
                return "templateGerente";
            }
        }
        return "";
    }
    
    public void goToMain(){
        FacesContext context = FacesContext.getCurrentInstance();
        HttpServletRequest request = (HttpServletRequest) context.getExternalContext().getRequest();
        if (request.getUserPrincipal() != null) {
            if ( request.isUserInRole("Admin") ){
                goToUsuarios();
            }
            else if ( request.isUserInRole("Calidad") ){
                goToProduccion();
            }
            else if ( request.isUserInRole("Gerencia") ){
                goToEvolucionProduccion();
            }
        }       
    }
}
