/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package sessionbeans;

import entities.User;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityExistsException;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.NonUniqueResultException;
import javax.persistence.PersistenceContext;

/**
 *
 * @author Jose
 */
@Stateless
public class UserFacade extends AbstractFacade<User> implements UserFacadeLocal {
    @PersistenceContext(unitName = "SAGProApp-ejbPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public UserFacade() {
        super(User.class);
    }

    @Override
    public User buscarPorRut(final String userRut) {
       try{
           User usuario = (User) em.createNamedQuery("User.findByRutUser")
                   .setParameter("rutUser", Long.parseLong(userRut))
                   .getSingleResult();
           System.out.println("Usuario: '"+userRut+"' se ha encontrado con éxito");
           return usuario;
                   
        }
       catch(NoResultException e){
           System.out.println("Usuario: '"+userRut+"' no se encuentra registrado");
           return null;
       }
       catch(NonUniqueResultException e){
           System.out.println("Usuario: '"+userRut+"', por alguna razón inesperada, se encuentra repetido");
           return null;
       }
    }

    @Override
    public List<User> findAll() {
        return super.findAll(); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public User find(Object id) {
        return super.find(id); //To change body of generated methods, choose Tools | Templates.
    }
    
    /**
     *
     * @param usuario_rut
     * @return
     */
    @Override
    public int eliminarUsuario(final String usuario_rut) {

        if (!usuarioExists(usuario_rut)) {
            return -1;
            
        } else {
            try {
                User usuario=buscarPorRut(usuario_rut);
                remove(usuario);
                System.out.println("eliminación del usuario realizada con éxito");
                return 0;
            } catch (EntityExistsException e) {
                System.out.println("Eliminando Usuario: Error -> " + e.getMessage());
                return -1;
            }
        }
    }
    
    private Boolean usuarioExists(String usuario_rut) {
        int resultados;
        resultados = em.createNamedQuery("User.findByRutUser")
                .setParameter("rutUser", Long.parseLong(usuario_rut))
                .getResultList().size();

        return resultados != 0;
    }

    @Override
    public void remove(User entity) {
        super.remove(entity); //To change body of generated methods, choose Tools | Templates.
    }
    
    
    
}
