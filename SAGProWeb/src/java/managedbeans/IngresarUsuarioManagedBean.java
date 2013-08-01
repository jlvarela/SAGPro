/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package managedbeans;

import entities.User;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.inject.Named;
import javax.enterprise.context.RequestScoped;
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
@ManagedBean(name = "ingresarUsuarioManagedBean")
@ViewScoped
public class IngresarUsuarioManagedBean {
    
    @EJB
    private UserFacadeLocal userFacade;
    
    private String userid;
    private String username;
    private String userlastname;
    private String usermail;
    private String userrole;
    private String[] tiposUsuarios;

    public String getUserrole() {
        return userrole;
    }

    public void setUserrole(String userrole) {
        this.userrole = userrole;
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

    public String[] getTiposUsuarios() {
        return tiposUsuarios;
    }

    public void setTiposUsuarios(String[] tiposUsuarios) {
        this.tiposUsuarios = tiposUsuarios;
    }
    
    
    /**
     * Creates a new instance of IngresarUsuarioManagedBean
     */
    public IngresarUsuarioManagedBean() {
    }
    
    @PostConstruct
    public void init(){ 
        String[] valuesUsuarios=userFacade.getValuesTiposUsuarios();
                
        if (valuesUsuarios != null ){
            tiposUsuarios = valuesUsuarios;
            
        }
        
    }
    
    public void ingresarUsuario(){
        
        String rutUsuario= userid.substring(0, userid.length()-1);
        
        int resp = userFacade.agregarUsuario(rutUsuario, username, userlastname, usermail,userrole);

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
