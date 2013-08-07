/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package managedbeans;

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
import pojoclass.Usuario;

/**
 *
 * @author Marco
 */
@ManagedBean(name = "ingresarUsuarioManagedBean")
@ViewScoped
public class IngresarUsuarioManagedBean {
    
    @EJB
    private UserFacadeLocal userFacade;
    
    private Usuario newUser;
    private String[] tiposUsuarios;
    private List<Usuario> selectedUsers;
    private Usuario selectUser;

    public String[] getTiposUsuarios() {
        return tiposUsuarios;
    }

    public void setTiposUsuarios(String[] tiposUsuarios) {
        this.tiposUsuarios = tiposUsuarios;
    }

    public List<Usuario> getSelectedUsers() {
        return selectedUsers;
    }

    public void setSelectedUsers(List<Usuario> selectedUsers) {
        this.selectedUsers = selectedUsers;
    }
    
    public Usuario getNewUser() {
        return newUser;
    }

    public void setNewUser(Usuario newUser) {
        this.newUser = newUser;
    }

    public Usuario getSelectUser() {
        return selectUser;
    }

    public void setSelectUser(Usuario selectUser) {
        this.selectUser = selectUser;
    }
    
    
    /**
     * Creates a new instance of IngresarUsuarioManagedBean
     */
    public IngresarUsuarioManagedBean() {
    }
    
    @PostConstruct
    public void init(){ 
        String[] valuesUsuarios = userFacade.getValuesTiposUsuarios();
                
        if (valuesUsuarios != null ){
            tiposUsuarios = valuesUsuarios;
            
        }
        
        newUser = new Usuario();
        
    }
    
    public void ingresarUsuario(){
        
        String rutUsuario = newUser.getRut().toString().substring(0, newUser.getRut().toString().length() - 1);
        
        int resp = userFacade.agregarUsuario(rutUsuario
                , newUser.getNombre()
                , newUser.getApellido()
                , newUser.getCorreo()
                , newUser.getTipo());

        FacesContext fcontext = FacesContext.getCurrentInstance();
        String viewId = fcontext.getViewRoot().getViewId();
        ViewHandler handler = fcontext.getApplication().getViewHandler();
        UIViewRoot root = handler.createView(fcontext, viewId);
        root.setViewId(viewId);
        fcontext.setViewRoot(root);
        
        if (resp == 0) {
            fcontext.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "", "Usuario agregado con éxito"));
        } else if (resp == -1) {
            fcontext.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "", "Usuario ya ingresado"));
        }
    }
}
