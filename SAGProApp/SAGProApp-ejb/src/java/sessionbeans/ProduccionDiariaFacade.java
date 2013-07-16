/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package sessionbeans;

import entities.ProduccionDiaria;
import java.util.Date;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
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
    
}
