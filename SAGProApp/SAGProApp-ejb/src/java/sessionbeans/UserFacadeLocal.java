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
    
}
