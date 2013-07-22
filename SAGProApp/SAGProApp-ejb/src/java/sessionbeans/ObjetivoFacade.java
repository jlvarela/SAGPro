/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package sessionbeans;

import entities.Objetivo;
import java.util.Date;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityExistsException;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author Jose
 */
@Stateless
public class ObjetivoFacade extends AbstractFacade<Objetivo> implements ObjetivoFacadeLocal {
    @PersistenceContext(unitName = "SAGProApp-ejbPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public ObjetivoFacade() {
        super(Objetivo.class);
    }

    @Override
    public void create(Objetivo entity) {
        super.create(entity); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public List<Objetivo> findAll() {
        return super.findAll(); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public int agregarObjetivo(final String nombre
            , final String decripcion
            , final Date fecha_inicial
            , final Date fecha_final
            , final short prioridad) {
        
        Objetivo obj = new Objetivo(0, nombre, new Date(), new Date());
        obj.setDescripcionObjetivo(decripcion);
        obj.setPrioridadObjetivo(prioridad);
        
        if (!objetivoExists(nombre)){
            try{
                create(obj);
                System.out.println("Creación de Objetivo realizada con éxito");
                return 0;
            }catch(EntityExistsException e){
                System.out.println("Ingresando objetivo:" + e.getMessage());
                return -1;
            }
        }
        else{
            return -1;
        }
    }
    
    public Boolean objetivoExists(String obj_nombre){
        int resultados;
        resultados = em.createNamedQuery("Objetivo.findByNombreObjetivo")
                .setParameter("nombreObjetivo", obj_nombre)
                .getResultList().size();
        
        return resultados != 0;
    }
    
}
