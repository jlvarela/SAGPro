/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package managedbeans;

import entities.User;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.inject.Named;
import javax.faces.bean.ViewScoped;
import org.primefaces.event.SelectEvent;
import sessionbeans.UserFacadeLocal;


/**
 *
 * @author Marco
 */

@ManagedBean(name = "consultarUsuarioManagedBean")
@ViewScoped

public class ConsultarUsuarioManagedBean implements Serializable{
    
    @EJB
    private UserFacadeLocal userFacade;
    
    private String userid;
    private String username;
    private String userlastname;
    private String usermail;
    private List<User> listaUsers;
    private List<User> selectedUsers;
    private User selectedUser;

    public User getSelectedUser() {
        return selectedUser;
    }

    public void setSelectedUser(User selectedUser) {
        this.selectedUser = selectedUser;
    }
    
    

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

    public String getUsermail() {
        return usermail;
    }

    public void setUsermail(String usermail) {
        this.usermail = usermail;
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
    
    

    /**
     * Creates a new instance of ConsultarUsuarioManagedBean
     */
    public ConsultarUsuarioManagedBean() {
        
        
    }
    
    @PostConstruct
    public void init(){        
        listaUsers=userFacade.findAll();
        
    }
    
    public void hola(SelectEvent arg){
        System.out.println("Holasfksagfask");
        selectedUser = (User)arg.getObject();
    }
    
    public void holi()
    {
        System.out.println(selectedUser.getNombreUser());
    }
}
