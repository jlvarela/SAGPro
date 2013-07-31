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
import javax.faces.application.FacesMessage;
import javax.faces.application.ViewHandler;
import javax.faces.bean.ManagedBean;
import javax.inject.Named;
import javax.faces.bean.ViewScoped;
import javax.faces.component.UIViewRoot;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
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
    private String[] tiposUsuarios;

    public String[] getTiposUsuarios() {
        return tiposUsuarios;
    }

    public void setTiposUsuarios(String[] tiposUsuarios) {
        this.tiposUsuarios = tiposUsuarios;
    }
    
    

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
        String[] valuesUsuarios=userFacade.getValuesTiposUsuarios();
        listaUsers=userFacade.findAll();
        
        if (valuesUsuarios != null ){
            tiposUsuarios = valuesUsuarios;
            
        }
        
    }
    
    public void borrarUsuario(){
        //System.out.println(selectedUser.getRutUser());
        String rut=(selectedUser.getRutUser()).toString();
        System.out.println(rut);
        int resp=userFacade.eliminarUsuario(rut);
        FacesContext fcontext = FacesContext.getCurrentInstance();
        String viewId = fcontext.getViewRoot().getViewId();
        ViewHandler handler = fcontext.getApplication().getViewHandler();
        UIViewRoot root = handler.createView(fcontext, viewId);
        root.setViewId(viewId);
        fcontext.setViewRoot(root);
         if (resp == 0) {
            fcontext.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "", "Usuario eliminado con éxito"));
        } else if (resp == -1) {
            fcontext.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "", "Error al eliminar el usuario"));
        }
    }
    
    
    
    public void modificarUsuario(){
        int resp = userFacade.editarUsuario(selectedUser.getRutUser().toString(),selectedUser.getNombreUser(), selectedUser.getApellidoUser(), selectedUser.getEmailUser(), selectedUser.getRoleUser());
        FacesContext fcontext = FacesContext.getCurrentInstance();
        String viewId = fcontext.getViewRoot().getViewId();
        ViewHandler handler = fcontext.getApplication().getViewHandler();
        UIViewRoot root = handler.createView(fcontext, viewId);
        root.setViewId(viewId);
        fcontext.setViewRoot(root);
        if (resp == 0) {
            fcontext.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "", "Usuario editado con éxito"));
        } else if (resp == -1) {
            fcontext.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "", "Error en la edición del usuario"));
        }
    }
    
    
}
