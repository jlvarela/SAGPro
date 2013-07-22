/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package sessionbeans;

import entities.User;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
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
    private final String[] tiposUsuariosValues={"Admin", "Calidad", "Gerencia"};
    @PersistenceContext(unitName = "SAGProApp-ejbPU")
    private EntityManager em;

    @Override
    public String[] getTiposUsuariosValues() {
        return tiposUsuariosValues;
    }
    
    @Override
    public String[] getValuesTiposUsuarios() {
        return getTiposUsuariosValues();
    }
    
    
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

    @Override
    public void create(User entity) {
        super.create(entity); //To change body of generated methods, choose Tools | Templates.
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
    
    @Override
    public int editarUsuario(final String usuario_rut, final String nombre, final String apellido, final String correo, final String rol){
        if (!usuarioExists(usuario_rut)) {
            return -1;
            
        } else {
            try {
                User usuario=buscarPorRut(usuario_rut);
                usuario.setNombreUser(nombre);
                usuario.setApellidoUser(apellido);
                usuario.setEmailUser(correo);
                usuario.setRoleUser(rol);
                edit(usuario);
                System.out.println("edición del usuario realizada con éxito");
                return 0;
            } catch (EntityExistsException e) {
                System.out.println("Editando Usuario: Error -> " + e.getMessage());
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

    @Override
    public void edit(User entity) {
        super.edit(entity); //To change body of generated methods, choose Tools | Templates.
    }
    
    public String md5(String password){
        char[] HEXADECIMAL = { '0', '1', '2', '3','4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f' };
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            byte[] bytes = md.digest(password.getBytes());
            StringBuilder sb = new StringBuilder(2 * bytes.length);
            for (int i = 0; i < bytes.length; i++) {
                int low = (int)(bytes[i] & 0x0f);
                int high = (int)((bytes[i] & 0xf0) >> 4);
                sb.append(HEXADECIMAL[high]);
                sb.append(HEXADECIMAL[low]);
            }
            return sb.toString();
        } catch (NoSuchAlgorithmException e) {
            //exception handling goes here
            return null;
        }
        
    }
    
    

    
    @Override
    public int agregarUsuario(final String rut, final String nombre, final String apellido, final String correo,final String rol){
        if (!usuarioExists(rut)){
            try{
            User usuario= new User();
            usuario.setRutUser(Long.valueOf(rut));
            usuario.setNombreUser(nombre);
            usuario.setApellidoUser(apellido);
            usuario.setEmailUser(correo);
            usuario.setPasswordUser(md5(rut));
            usuario.setRoleUser(rol);
            create(usuario);
            System.out.println("Creación del usuario realizada con éxito");
            return 0;
            }
            catch (EntityExistsException e){
                System.out.println("Ingresando Usuario: Error -> " + e.getMessage());
                return -1;
            }
        }
        else{
            System.out.println("entro aca");
            return -1;
        }
        
        
    }
    
    
    
    
    
}
