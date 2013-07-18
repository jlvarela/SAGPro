/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package managedbeans;

import entities.User;
import javax.inject.Named;
import java.io.Serializable;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.enterprise.context.SessionScoped;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletRequest;
import sessionbeans.UserFacadeLocal;

/**
 *
 * @author Jose
 */
@Named(value = "userManagedBean")
@SessionScoped
public class UserManagedBean implements Serializable {
    @EJB
    private UserFacadeLocal userFacade;
    

    public String getUserid() {
        return userid;
    }

    public void setUserid(String userid) {
        this.userid = userid;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getUserlastname() {
        return userlastname;
    }

    public void setUserlastname(String userlastname) {
        this.userlastname = userlastname;
    }

    public List<User> getListaUsers() {
        return listaUsers;
    }

    public void setListaUsers(List<User> listaUsers) {
        this.listaUsers = listaUsers;
    }

    public List<User> getSelectedUsers() {
        return selectedUsers;
    }

    public void setSelectedUsers(List<User> selectedUsers) {
        this.selectedUsers = selectedUsers;
    }
    
    
    
    private String userid;
    private String username;
    private String userlastname;
    private String usermail;
    private List<User> listaUsers;
    private List<User> selectedUsers;
    
    
        
    /**
     * Creates a new instance of UserManagedBean
     */
    public UserManagedBean() {
    }
    
    
    
    @PostConstruct
    public void init(){
        FacesContext context = FacesContext.getCurrentInstance();
        HttpServletRequest request = (HttpServletRequest) context.getExternalContext().getRequest();
        this.userid = request.getUserPrincipal().getName();
        User usuario = null;
        if (this.userid != null)
            usuario = userFacade.buscarPorRut(userid);
        if (usuario != null){
            this.username = usuario.getNombreUser();
            this.userlastname = usuario.getApellidoUser();
            this.usermail = usuario.getEmailUser();
        } 
        
        listaUsers=userFacade.findAll();
        System.out.println(listaUsers.size());
    }
}
