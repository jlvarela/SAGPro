/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package sessionbeans;

import entities.ProduccionDiaria;
import java.util.Date;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;

/**
 *
 * @author Jose
 */
@Stateless
public class ProduccionDiariaFacade extends AbstractFacade<ProduccionDiaria> implements ProduccionDiariaFacadeLocal {
    @PersistenceContext(unitName = "SAGProApp-ejbPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    @Override
    public void create(ProduccionDiaria entity) {
        super.create(entity); //To change body of generated methods, choose Tools | Templates.
    }

    public ProduccionDiariaFacade() {
        super(ProduccionDiaria.class);
    }

    @Override
    public int agregarProduccionDiaria(final int codMaterial, final int cantidadMaterial) {
        if(codMaterial > 0 && cantidadMaterial > 0){
            ProduccionDiaria prod = new ProduccionDiaria(new Date(), codMaterial);
            prod.setProduccionMaterial(cantidadMaterial);
            create(prod);
            return 0;
        }
        else{
            return -1;  // Invalid arguments
        }
        
    }

    @Override
    public ProduccionDiaria find(Object id) {
        return super.find(id); //To change body of generated methods, choose Tools | Templates.
    }
    
    @Override
    public List<ProduccionDiaria> buscarPorFecha(final String produccion_fecha){
        try {
            List<ProduccionDiaria> produccionDiaria = (List<ProduccionDiaria>) em.createNamedQuery("ProduccionDiaria.findByFechaDiariaEstadistica")
                    .setParameter("fechaDiariaEstadistica", produccion_fecha)
                    .getResultList();
            System.out.println("Producción: '" + produccion_fecha + "' se ha encontrado con éxito");
            return produccionDiaria;

        } catch (NoResultException e) {
            System.out.println("Producción: '" + produccion_fecha + "' no se encuentra registrado");
            return null;
        }
    }

    @Override
    public List<ProduccionDiaria> findAll() {
        return super.findAll(); //To change body of generated methods, choose Tools | Templates.
    }
    
    
    
}
