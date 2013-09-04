/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package sessionbeans;

import entities.User;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author Jose
 */
@Local
public interface UserFacadeLocal {

    void create(User user);

    void edit(User user);

    void remove(User user);

    User find(Object id);

    List<User> findAll();

    List<User> findRange(int[] range);

    int count();

    User buscarPorRut(final String userRut);

    public int eliminarUsuario(final String usuario_rut);

    public int editarUsuario(String userid, String username, String userlastname, String usermail, String rol);

    public String[] getTiposUsuariosValues();

    public String[] getValuesTiposUsuarios();

    public int agregarUsuario(String rut, String nombre, String apellido, String correo, String rol); 

    int cambiarPassword(final String usuario_rut, final String new_password);

    int editarUsuarioPassword(final String user_rut, final String new_pass, final String nombre, final String apellido, final String correo, final String rol);

}
