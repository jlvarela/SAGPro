/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package managedbeans;

import javax.inject.Named;
import javax.enterprise.context.ApplicationScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;

/**
 *
 * @author Jose
 */
@Named(value = "loginBean")
@ApplicationScoped
public class LoginBean {

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
    
    String username;
    String password;

    /**
     * Creates a new instance of LoginBean
     */
    public LoginBean() {
        username = new String();
        password = new String();
    }
    
    /**
     * Autentificación del usuario ingresado.
     * 
     * @return String Página destino
     */
    public String login(){
        FacesContext context = FacesContext.getCurrentInstance();
        HttpServletRequest request = (HttpServletRequest) context.getExternalContext().getRequest();
        try{
            request.login(username, password);
        }catch (ServletException e){
            context.addMessage(null, new FacesMessage(e.getMessage()));
        }
        
        return "admin/index";
    }
}
