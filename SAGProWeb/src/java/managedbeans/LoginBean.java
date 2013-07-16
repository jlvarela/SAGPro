/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package managedbeans;

import javax.el.ELContext;
import javax.inject.Named;
import javax.enterprise.context.RequestScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;

/**
 *
 * @author Jose
 */
@Named(value = "loginBean")
@RequestScoped
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
                return "/index?faces-redirect=true";
            } catch (ServletException e) {
                System.out.println("No se pudo desloguear :(");
                return "";
            }
        }
        return "/index?faces-redirect=true";
    }
}
