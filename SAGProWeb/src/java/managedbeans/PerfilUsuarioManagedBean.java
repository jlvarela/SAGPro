/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package managedbeans;

import beanvalidations.rutValidator;
import entities.User;
import java.io.Serializable;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletRequest;
import pojoclass.Usuario;
import sessionbeans.UserFacadeLocal;

/**
 *
 * @author Jose
 */
@ManagedBean(name = "perfilUsuarioManagedBean")
@ViewScoped
public class PerfilUsuarioManagedBean implements Serializable{
    @EJB
    private UserFacadeLocal userFacade;

    /**
     * Creates a new instance of PerfilUsuarioManagedBean
     */
    Usuario my_user;
    String password;
    String new_password;
    String format_rut;
    
    public PerfilUsuarioManagedBean() {
    }
    
    @PostConstruct
    public void init(){
        FacesContext context = FacesContext.getCurrentInstance();
        HttpServletRequest request = (HttpServletRequest) context.getExternalContext().getRequest();
        if (request.getUserPrincipal() != null) {
            String name = request.getUserPrincipal().getName();
            User usuario = null;
            if (name != null) {
                usuario = userFacade.buscarPorRut(name);
            }
            if (usuario != null) {
                my_user = util.MappingFromEntitieToPojo.usuarioFromEntityToPojo(usuario);
                format_rut = my_user.getRut().concat("-").concat(String.valueOf(rutValidator.getUltimoDigito(my_user.getRut())));
            }
        }
    }

    public String getFormat_rut() {
        return format_rut;
    }

    public void setFormat_rut(String format_rut) {
        this.format_rut = format_rut;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getNew_password() {
        return new_password;
    }

    public void setNew_password(String new_password) {
        this.new_password = new_password;
    }

    public Usuario getMy_user() {
        return my_user;
    }

    public void setMy_user(Usuario my_user) {
        this.my_user = my_user;
    }
    
    public void editarPerfil()
    {
        FacesContext cont = FacesContext.getCurrentInstance();
        FacesMessage msg;
        
        if ( !password.isEmpty() && !new_password.isEmpty() ){
            // Valdiar contraseña antigua
            String pass_md5 = util.MD5.md5(password);
            if ( pass_md5.compareTo(my_user.getPassword()) == 0 ){      // Ingresó passowrd actual correcta
                userFacade.editarUsuarioPassword(my_user.getRut()
                        , new_password
                        , my_user.getNombre()
                        , my_user.getApellido()
                        , my_user.getCorreo()
                        , my_user.getTipo());
                msg = new FacesMessage(FacesMessage.SEVERITY_INFO, null, "Perfil editado con éxito.");
                cont.addMessage(null, msg);
            }
            else{
                msg = new FacesMessage(FacesMessage.SEVERITY_ERROR, null, "Contraseña actual es incorrecta");
                cont.addMessage(null, msg);
            }
        }
        else if ( !password.isEmpty() && new_password.isEmpty() ){
            msg = new FacesMessage(FacesMessage.SEVERITY_WARN, null, "Debe ingresar la nueva contraseña.");
            cont.addMessage(null, msg);
        }
        else if ( password.isEmpty() && !new_password.isEmpty() ){
            msg = new FacesMessage(FacesMessage.SEVERITY_WARN, null, "Debe ingresar su contraseña antigua.");
            cont.addMessage(null, msg);
        }
        else{
            int resp = userFacade.editarUsuario(my_user.getRut(),my_user.getNombre(),my_user.getApellido(),my_user.getCorreo(),my_user.getTipo());
            if (resp == -1){
                msg = new FacesMessage(FacesMessage.SEVERITY_ERROR, null, "Usuario no encontrado. Lo sentimos.");
                cont.addMessage(null, msg);
            }
            else if (resp == 0){
                msg = new FacesMessage(FacesMessage.SEVERITY_INFO, null, "Perfil editado con éxito.");
                cont.addMessage(null, msg);
            }
        }
    }
    
}
