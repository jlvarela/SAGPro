/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package managedbeans;

import pojoclass.Usuario;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.application.ViewHandler;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.component.UIViewRoot;
import javax.faces.context.FacesContext;
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
    
    private List<Usuario> listaUsers;
    private List<Usuario> selectedUsers;
    private Usuario selectedUser;
    private String[] tiposUsuarios;

    public List<Usuario> getSelectedUsers() {
        return selectedUsers;
    }

    public void setSelectedUsers(List<Usuario> selectedUsers) {
        this.selectedUsers = selectedUsers;
    }

    public String[] getTiposUsuarios() {
        return tiposUsuarios;
    }

    public void setTiposUsuarios(String[] tiposUsuarios) {
        this.tiposUsuarios = tiposUsuarios;
    }

    public List<Usuario> getListaUsers() {
        return listaUsers;
    }

    public void setListaUsers(List<Usuario> listaUsers) {
        this.listaUsers = listaUsers;
    }
    
    public Usuario getSelectedUser() {
        return selectedUser;
    }

    public void setSelectedUser(Usuario selectedUser) {
        this.selectedUser = selectedUser;
    }

    /**
     * Creates a new instance of ConsultarUsuarioManagedBean
     */
    public ConsultarUsuarioManagedBean() {
        
        
    }
    
    @PostConstruct
    public void init(){ 
        String[] valuesUsuarios=userFacade.getValuesTiposUsuarios();
        List<entities.User> listaUsersEntities = userFacade.findAll();
        
        if (valuesUsuarios != null ){
            tiposUsuarios = valuesUsuarios;
        }
        
        ArrayList<Usuario> usuarioList = new ArrayList();
        
        for (entities.User userEntity : listaUsersEntities )
            usuarioList.add(util.MappingFromEntitieToPojo.usuarioFromEntityToPojo(userEntity));
        
        listaUsers = usuarioList;        
    }
    
    public void borrarUsuario(){
        //System.out.println(selectedUser.getRutUser());
        String rut = (selectedUser.getRut()).toString();
        System.out.println(rut);
        int resp = userFacade.eliminarUsuario(rut);
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
        int resp = userFacade.editarUsuario(selectedUser.getRut().toString(),selectedUser.getNombre(), selectedUser.getApellido(), selectedUser.getCorreo(), selectedUser.getTipo());
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
