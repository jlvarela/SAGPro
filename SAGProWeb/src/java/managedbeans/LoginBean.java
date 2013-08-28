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
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
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
    public String login() {
        FacesContext context = FacesContext.getCurrentInstance();
        HttpServletRequest request = (HttpServletRequest) context.getExternalContext().getRequest();
        if (request.getUserPrincipal() == null) {
            try {
                request.login(username, password);
                return "admin/index?faces-redirect=true";
            } catch (ServletException e) {
                context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Error", "Login inválido"));
                System.out.println("Login Failed: " + e.getMessage());
                return "";
            }
        } else {
            System.out.println(request.getUserPrincipal().getName());
            context.addMessage(null, new FacesMessage("Logueado"));
            return "";
        }
    }

    public String logout() {
        FacesContext context = FacesContext.getCurrentInstance();
        HttpServletRequest request = (HttpServletRequest) context.getExternalContext().getRequest();
        if (request.getUserPrincipal() != null) {
            try {
                request.logout();
                context.getExternalContext().redirect("/SAGProWeb/");
            } catch (ServletException e) {
                System.out.println("No se pudo desloguear :( " + e.getMessage());
                return "";
            } catch (IOException ex) {
                Logger.getLogger(LoginBean.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return "";
    }
}
