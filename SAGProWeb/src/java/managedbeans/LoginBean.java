/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package managedbeans;

import java.io.IOException;
import java.io.Serializable;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.el.ELContext;
import javax.inject.Named;
import javax.enterprise.context.RequestScoped;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author Jose
 */
@ManagedBean(name = "loginBean")
@ViewScoped
public class LoginBean implements Serializable {

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
        
    private String username;
    private String password;
    /**
     * Creates a new instance of LoginBean
     */
    public LoginBean() {
    }

    /**
     * Autentificación del usuario ingresado.
     *
     * @return String Página destino
     */
    public void login() {
        FacesContext context = FacesContext.getCurrentInstance();
        HttpServletRequest request = (HttpServletRequest) context.getExternalContext().getRequest();
        
        
        if (request.getUserPrincipal() == null) {
            try {
                request.login(getFormattedRut(username), password);
                redirectToMainPage(request);
                
            } catch (ServletException e) {
                context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Usuario y/o contraseña incorrecta inválido", "Login inválido"));
                System.out.println("Login Failed: " + e.getMessage());
            }
        } else {
            redirectToMainPage(request);
            context.addMessage(null, new FacesMessage("Logueado"));
        }
    }

    public String logout() {
        FacesContext context = FacesContext.getCurrentInstance();
        HttpServletRequest request = (HttpServletRequest) context.getExternalContext().getRequest();
        if (request.getUserPrincipal() != null) {
            try {
                request.logout();
                ExternalContext ext = context.getExternalContext();
                context.getExternalContext().redirect(ext.getRequestContextPath());
            } catch (ServletException e) {
                System.out.println("No se pudo desloguear :( " + e.getMessage());
                return "";
            } catch (IOException ex) {
                Logger.getLogger(LoginBean.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return "";
    }
    
    private void redirectToMainPage(HttpServletRequest request){
        ExternalContext extcon = FacesContext.getCurrentInstance().getExternalContext();
        String webpage = "";
        try{
            
            if ( request.isUserInRole("Admin") ){
                webpage = webpage.concat("/faces/admin/consultarUsuario.xhtml");
            }
            else if ( request.isUserInRole("Calidad") ){
                webpage = webpage.concat("/faces/calidad/ingresarProduccion.xhtml");
            }
            else if ( request.isUserInRole("Gerencia") ){
                webpage = webpage.concat("/faces/gerente/evolucionProduccion.xhtml");
            }
            extcon.redirect(extcon.getRequestContextPath() + webpage);
        }
        catch(IOException ex){
            System.out.println("No se ha podido redirigir a la página ".concat(webpage));            
        }
    }
    
    private String getFormattedRut(String rut){
        String total = rut.substring(0,rut.length()-1);
        return total;
    }
}
